package org.iq47;

//import org.iq47.security.ApiKeyFilter;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.iq47.model.repo1",
        entityManagerFactoryRef = "defaultManagerFactory",
        transactionManagerRef= "defaultTransactionManager")
@EntityScan("org.iq47.model.entity.*")
@EnableProcessApplication()
public class ServingWebContentApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    @Autowired
    private Environment env;

    @Bean
    @Primary
    @QuartzDataSource
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
