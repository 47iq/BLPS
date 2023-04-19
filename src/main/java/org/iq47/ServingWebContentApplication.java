package org.iq47;

//import org.iq47.security.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.iq47.model.repo1",
        entityManagerFactoryRef = "defaultManagerFactory",
        transactionManagerRef= "defaultTransactionManager")
@EntityScan("org.iq47.model.entity.*")
public class ServingWebContentApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Autowired
    private Environment env;

    @Bean
    @Primary
    public DataSource defaultDataSource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean(name = "defaultManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean defaultManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(defaultDataSource())
                .packages("org.iq47.model.entity")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager defaultTransactionManager(
            final @Qualifier("defaultManagerFactory") LocalContainerEntityManagerFactoryBean managerFactory) {
        return new JpaTransactionManager(managerFactory.getObject());
    }
}
