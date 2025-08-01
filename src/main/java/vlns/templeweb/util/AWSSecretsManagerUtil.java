package vlns.templeweb.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import java.util.Map;

@Service
public class AWSSecretsManagerUtil {

    private static final Logger logger = LoggerFactory.getLogger(AWSSecretsManagerUtil.class);

    @Value("${aws.secretName}")
    private String secretName;

    private Map<String, String> secretMap;

    @PostConstruct
    public void init() {
        secretMap = fetchSecret(secretName);
        logger.info("Secrets loaded from AWS Secrets Manager for secret: {}", secretName);
    }

    public Map<String, String> getSecret() {
        return secretMap;
    }

    private Map<String, String> fetchSecret(String secretName) {
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey,secretKey);
        try (SecretsManagerClient client = SecretsManagerClient.builder()
                .region(Region.of("ap-south-1"))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build()) {
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName)
                    .build();
            String secretString = client.getSecretValue(request).secretString();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(secretString, Map.class);
        } catch (Exception e) {
            logger.error("Failed to retrieve secret: {}", secretName, e);
            throw new RuntimeException("Failed to retrieve secret: " + secretName, e);
        }
    }
}
