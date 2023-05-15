package org.iq47.producer;

import org.iq47.message.MessageConverter;
import org.iq47.message.TicketReportMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSContext;
import javax.jms.Queue;

@Component
public class JMSMessageSender implements MessageSender {

    private Queue queue;
    private JMSContext context;
    private MessageConverter messageConverter;

    @Autowired
    public JMSMessageSender(Queue queue, JMSContext context, MessageConverter messageConverter) {
        this.queue = queue;
        this.context = context;
        this.messageConverter = messageConverter;
    }

    @Autowired
    public JMSMessageSender(Queue queue, JMSContext context) {
        this.queue = queue;
        this.context = context;
    }

    @Override
    public void sendTicketReportMessage(TicketReportMessage reportMessage) {
        context.createProducer().send(queue, messageConverter.convertTicketReportMessage(reportMessage));
    }
}
