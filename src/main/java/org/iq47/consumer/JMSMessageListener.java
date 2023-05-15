package org.iq47.consumer;
import lombok.SneakyThrows;
import org.iq47.service.CSVGenerationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.io.IOException;

@Component
public class JMSMessageListener implements MessageListener {

    private ConnectionFactory connectionFactory;

    private Queue queue;

    private CSVGenerationService generationService;

    @Autowired
    public JMSMessageListener(ConnectionFactory connectionFactory, Queue queue, CSVGenerationService generationService) throws JMSException {
        this.queue = queue;
        this.generationService = generationService;
        this.connectionFactory = connectionFactory;
    }

    @SneakyThrows
    @PostConstruct
    void init() {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message m) {
        try {
            TextMessage msg = (TextMessage) m;
            System.out.println("following message is received:" + msg.getText());
            JSONObject jo = new JSONObject(msg.getText());
            String airline = jo.getString("airline_name");
            generationService.generateAirlineReport(airline);
        } catch(JMSException e) {
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
