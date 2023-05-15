package org.iq47.job;

import lombok.extern.slf4j.Slf4j;
import org.iq47.message.TicketReportMessage;
import org.iq47.producer.JMSMessageSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
@Slf4j
public class TicketReportJob implements Job {

    JMSMessageSender jmsMessageSender;

    @Autowired
    public TicketReportJob(JMSMessageSender jmsMessageSender) {
        this.jmsMessageSender = jmsMessageSender;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("Job ** {} ** starting @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
            jmsMessageSender.sendTicketReportMessage(new TicketReportMessage("pobeda"));
            log.info("Job ** {} ** completed.  Next job scheduled @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getNextFireTime());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
