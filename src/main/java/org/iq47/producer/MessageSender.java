package org.iq47.producer;

import org.iq47.message.TicketReportMessage;

import javax.jms.JMSException;

public interface MessageSender {
    void sendTicketReportMessage(TicketReportMessage reportMessage) throws JMSException;
}
