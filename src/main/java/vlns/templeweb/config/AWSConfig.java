package vlns.templeweb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vlns.templeweb.util.AWSSecretsManagerUtil;

import java.util.Map;

@Configuration
public class AWSConfig {
    @Autowired
    private AWSSecretsManagerUtil secretsManagerUtil;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client() {
        Map<String, String> secrets = secretsManagerUtil.getSecret();
        String accessKeyId = secrets.get("AWS_ACCESS_KEY_ID");
        String secretAccessKey = secrets.get("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
