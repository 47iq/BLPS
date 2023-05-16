package org.iq47.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"worker", "!web"})
@ComponentScan(basePackages = "org.iq47", includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="org.iq47.consumer.*")
}, useDefaultFilters = false)
public class WorkerConfiguration {
}
