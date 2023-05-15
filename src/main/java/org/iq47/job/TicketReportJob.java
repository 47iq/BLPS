package org.iq47.job;

import org.iq47.message.TicketReportMessage;
import org.iq47.producer.JMSMessageSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketReportJob implements Job {

    JMSMessageSender jmsMessageSender;

    @Autowired
    public TicketReportJob(JMSMessageSender jmsMessageSender) {
        this.jmsMessageSender = jmsMessageSender;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jmsMessageSender.sendTicketReportMessage(new TicketReportMessage("pobeda"));
    }
}
