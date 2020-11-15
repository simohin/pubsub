package simohin.subscriber.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
import simohin.subscriber.config.properties.DatabaseProperties;

/**
 * @author Timofei Simohin
 * @created 14.11.2020
 */
@Slf4j
@Configuration
@EnableJpaRepositories("simohin.subscriber.repository")
public class DatabaseConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factoryBean.setPackagesToScan("simohin.subscriber");
        factoryBean.setPersistenceUnitName("subscriber");
        factoryBean.setJpaProperties(jpaHibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    public DataSource dataSource(DatabaseProperties properties) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername(properties.getLogin());
        dataSource.setPassword(properties.getPassword());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setAutoCommit(false);
        dataSource.setMaximumPoolSize(properties.getConnectionPoolSize());
        dataSource.setMinimumIdle(properties.getConnectionPoolMinIdle());
        dataSource.setConnectionTimeout(properties.getConnectionTimeout());
        dataSource.setLeakDetectionThreshold(20000);

        log.info("Init dataSource (url={})", dataSource.getDataSourceProperties());

        return dataSource;
    }

    @Primary
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        log.info("Init jpaTransactionManager {}", jpaTransactionManager.toString());

        return jpaTransactionManager;
    }

    private Properties jpaHibernateProperties() {

        final Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.generate_statistics", "false");
        properties.setProperty("hibernate.type", "info");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.use_sql_comments", "false");
        properties.setProperty("hibernate.jdbc.batch_size", "20");
        properties.setProperty("hibernate.order_inserts", "true");
        properties.setProperty("hibernate.order_updates", "true");
        properties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");

        return properties;
    }

}
