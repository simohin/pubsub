package simohin.subscriber.config.properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Data
@Component
public class DatabaseProperties {

    @Resource
    private Environment environment;

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private String port;

    @Value("${db.databasename}")
    private String databasename;

    @Value("${db.connection_pool_size}")
    private Integer connectionPoolSize;

    @Value("${db.connection_pool_min_idle}")
    private Integer connectionPoolMinIdle;

    @Value("${db.connection_timeout}")
    private Integer connectionTimeout;

    @Value("${db.login}")
    private String login;

    @Value("${db.password}")
    private String password;

    public String getUrl() {

        if (host == null || host.isEmpty()) {
            host = environment.getProperty("PARENT_HOST");
        }

        return String.format("jdbc:postgresql://%s:%s/%s", host, port, databasename);
    }
}
