package org.iq47.consumer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component
public class JMSMessageListener implements MessageListener {

    QueueConnection qConnect;

    Queue queue;

    @Autowired
    public JMSMessageListener(QueueConnection qConnect, Queue queue) {
        this.qConnect = qConnect;
        this.queue = queue;
    }

    @SneakyThrows
    @PostConstruct
    void init() {
        QueueSession qSession = (QueueSession) qConnect.createSession(false, Session.AUTO_ACKNOWLEDGE);
        QueueReceiver consumer = (QueueReceiver) qSession.createConsumer(queue);
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
