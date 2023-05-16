package org.iq47.job;

import org.iq47.config.QuartzConfig;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzJobsConfig {
    private static final String CRON_EVERY_DAY_12PM = "0 0 12 * * ?";

    @Bean(name = "ticketsReport")
    public JobDetailFactoryBean jobTicketReport() {
        return QuartzConfig.createJobDetail(TicketReportJob.class, "Tickets Report Job");
    }

    @Bean(name = "ticketsReportTrigger")
    public CronTriggerFactoryBean triggerTicketReport(@Qualifier("ticketsReport") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_DAY_12PM, "Member Statistics Trigger");
    }
}
