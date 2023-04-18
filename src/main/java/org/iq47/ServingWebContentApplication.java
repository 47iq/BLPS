package org.iq47;

//import org.iq47.security.ApiKeyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.iq47.model")
@EntityScan("org.iq47.model.entity")
public class ServingWebContentApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    /*@Bean
    public FilterRegistrationBean<ApiKeyFilter> loggingFilter(){
        FilterRegistrationBean<ApiKeyFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ApiKeyFilter());
        registrationBean.addUrlPatterns("/api/tickets/create/*");
        registrationBean.addUrlPatterns("/api/tickets/edit/*");
        registrationBean.addUrlPatterns("/api/tickets/delete/*");
        registrationBean.addUrlPatterns("/api/seller_tickets/create/*");
        registrationBean.addUrlPatterns("/api/seller_tickets/edit/*");
        registrationBean.addUrlPatterns("/api/seller_tickets/delete/*");
        registrationBean.addUrlPatterns("/api/cities/create/*");
        registrationBean.addUrlPatterns("/api/cities/delete/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }*/
}
