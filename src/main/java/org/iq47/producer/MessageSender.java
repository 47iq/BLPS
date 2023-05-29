package org.iq47.producer;

import org.iq47.message.TicketReportMessage;
import org.iq47.model.entity.AsyncTask;

import javax.jms.JMSException;

public interface MessageSender {
    AsyncTask sendTicketReportMessage(TicketReportMessage reportMessage) throws JMSException;
    AsyncTask sendTicketReportMessage(AsyncTask asyncTask) throws JMSException;
}
