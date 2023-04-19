package org.iq47.model.repo2;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.iq47.model.repo2",
        entityManagerFactoryRef = "userBalanceManagerFactory",
        transactionManagerRef= "userBalanceTransactionManager")
public class UserBalanceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public DataSource userBalanceDataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource2.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource2.url"));
        dataSource.setUsername(env.getProperty("spring.datasource2.username"));
        dataSource.setPassword(env.getProperty("spring.datasource2.password"));

        return dataSource;
    }

    @Bean(name = "userBalanceManagerFactory")
    public LocalContainerEntityManagerFactoryBean userBalanceManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userBalanceDataSource())
                .packages("org.iq47.model.entity")
                .build();
    }

    @Bean
    public PlatformTransactionManager userBalanceTransactionManager(
            final @Qualifier("userBalanceManagerFactory") LocalContainerEntityManagerFactoryBean userBalanceManagerFactory) {
        return new JpaTransactionManager(userBalanceManagerFactory.getObject());
    }

}
