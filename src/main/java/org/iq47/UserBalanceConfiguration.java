package org.iq47;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "org.iq47.model.repo2",
        entityManagerFactoryRef = "userBalanceManagerFactory",
        transactionManagerRef= "userBalanceTransactionManager")
public class UserBalanceConfiguration {

    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource userBalanceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "userBalanceManagerFactory")
    public LocalContainerEntityManagerFactoryBean userBalanceManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(userBalanceDataSource())
                .packages("org.iq47.model.repo2")
                .build();
    }

    @Bean
    public PlatformTransactionManager userBalanceTransactionManager(
            final @Qualifier("userBalanceManagerFactory") LocalContainerEntityManagerFactoryBean userBalanceManagerFactory) {
        return new JpaTransactionManager(userBalanceManagerFactory.getObject());
    }

}
