package org.iq47.producer;

import lombok.SneakyThrows;
import org.iq47.message.MessageConverter;
import org.iq47.message.TicketReportMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component
public class JMSMessageSender implements MessageSender {

    private Queue queue;
    private ConnectionFactory connectionFactory;
    private MessageConverter messageConverter;
    private JMSContext jmsContext;

    private MessageProducer publisher;
    private Session session;

    @SneakyThrows
    @Autowired
    public JMSMessageSender(Queue queue, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        this.queue = queue;
        this.connectionFactory = connectionFactory;
        this.messageConverter = messageConverter;
    }

    @PostConstruct
    void init() throws JMSException {
        Connection connection = connectionFactory.createConnection();
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.publisher = session.createProducer(queue);
        connection.start();
    }


    @Override
    public void sendTicketReportMessage(TicketReportMessage reportMessage) throws JMSException {
        publisher.send(queue, session.createTextMessage(messageConverter.convertTicketReportMessage(reportMessage)));
    }
}
