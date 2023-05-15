package org.iq47.producer;

import lombok.SneakyThrows;
import org.iq47.message.MessageConverter;
import org.iq47.message.TicketReportMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Component
public class JMSMessageSender implements MessageSender {

    private Queue queue;
    private ConnectionFactory connectionFactory;
    private MessageConverter messageConverter;
    private JMSContext jmsContext;

    @SneakyThrows
    @Autowired
    public JMSMessageSender(Queue queue, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.messageConverter = messageConverter;
        this.jmsContext = connectionFactory.createContext();
        connectionFactory.createConnection();
    }

    @PostConstruct
    public void test() {
        sendTicketReportMessage(new TicketReportMessage());
    }


    @Override
    public void sendTicketReportMessage(TicketReportMessage reportMessage) {
        jmsContext.createProducer().send(queue, messageConverter.convertTicketReportMessage(reportMessage));
    }
}
