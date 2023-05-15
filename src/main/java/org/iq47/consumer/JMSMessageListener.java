package org.iq47.consumer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component
public class JMSMessageListener implements MessageListener {

    private JMSContext jmsContext;

    private Queue queue;

    @Autowired
    public JMSMessageListener(ConnectionFactory connectionFactory, Queue queue) throws JMSException {
        this.jmsContext = connectionFactory.createContext();
        this.queue = queue;
    }

    @SneakyThrows
    @PostConstruct
    void init() {
        JMSConsumer consumer = jmsContext.createConsumer(queue);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message m) {
        try {
            TextMessage msg = (TextMessage) m;
            System.out.println("following message is received:" + msg.getText());
        } catch(JMSException e) {
            System.out.println(e);
        }
    }
}
