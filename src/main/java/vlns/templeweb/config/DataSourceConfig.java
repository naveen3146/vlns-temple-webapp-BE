package vlns.templeweb.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vlns.templeweb.util.AWSSecretsManagerUtil;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Autowired
    private AWSSecretsManagerUtil secretsManagerUtil;

    @Bean
    public DataSource dataSource() {
        Map<String, String> secrets = secretsManagerUtil.getSecret();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(secrets.get("JDBC_URL"));
        config.setUsername(secrets.get("DB_USERNAME"));
        config.setPassword(secrets.get("DB_PASSWORD"));
        config.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(config);
    }

}
