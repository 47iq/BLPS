package org.iq47.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"web", "!worker"})
@ComponentScan(basePackages = "org.iq47", includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="org.iq47.controller.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="org.iq47.job.*")
}, useDefaultFilters = false)
public class WebConfiguration {
}
