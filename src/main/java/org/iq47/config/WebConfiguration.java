package org.iq47.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("web")
@ComponentScan(basePackages = "org.iq47", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="org.iq47.consumer.*"),
})
public class WebConfiguration {
}
