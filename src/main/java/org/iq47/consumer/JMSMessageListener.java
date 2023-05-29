package org.iq47.consumer;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVPrinter;
import org.iq47.model.entity.AsyncTask;
import org.iq47.model.entity.AsyncTaskStatus;
import org.iq47.service.AsyncTaskService;
import org.iq47.service.CSVGenerationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JMSMessageListener implements MessageListener {

    private ConnectionFactory connectionFactory;

    private AsyncTaskService asyncTaskService;

    private Queue queue;

    private CSVGenerationService generationService;

    @Autowired
    public JMSMessageListener(ConnectionFactory connectionFactory, AsyncTaskService asyncTaskService, CSVGenerationService generationService, Queue queue) throws JMSException {
        this.asyncTaskService = asyncTaskService;
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
        connection.start();
    }

    @Override
    public void onMessage(Message m) {
        AsyncTask task = null;
        try {
            TextMessage msg = (TextMessage) m;
            System.out.println("following message is received:" + msg.getText());
            JSONObject jo = new JSONObject(msg.getText());
            String airline = jo.getString("airline_name");
            Long taskId = jo.getLong("task_id");
            task = asyncTaskService.getById(taskId);
            task.setStart(LocalDateTime.now());
            task.setStatus(AsyncTaskStatus.EXECUTION);
            asyncTaskService.save(task);
            CSVPrinter printer = generationService.prepareAirlineReport(airline);
            finishReport(task, printer);
        } catch (Exception e) {
            if(task != null)
                task.setStatus(AsyncTaskStatus.ERROR);
            throw new RuntimeException(e);
        } finally {
            if(task != null) {
                task.setFinishedAt(LocalDateTime.now());
                asyncTaskService.save(task);
            }
        }
    }

    @Transactional
    public void finishReport(AsyncTask task, CSVPrinter printer) {
        try {
            if(!task.getStatus().equals(AsyncTaskStatus.TIMED_OUT)) {
                printer.flush();
                printer.close();
                task.setStatus(AsyncTaskStatus.FINISHED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
