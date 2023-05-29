package org.iq47.producer;

import lombok.SneakyThrows;
import org.iq47.message.MessageConverter;
import org.iq47.message.TicketReportMessage;
import org.iq47.model.entity.AsyncTask;
import org.iq47.model.entity.AsyncTaskStatus;

import org.iq47.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;

@Component
public class JMSMessageSender implements MessageSender {

    private final int TIMEOUT_SECONDS = 1200;

    private final int RESTART_SECONDS = 12000;

    private AsyncTaskService asyncTaskService;

    private Queue queue;
    private ConnectionFactory connectionFactory;
    private MessageConverter messageConverter;

    private MessageProducer publisher;
    private Session session;

    @SneakyThrows
    @Autowired
    public JMSMessageSender(AsyncTaskService asyncTaskService, Queue queue, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        this.asyncTaskService = asyncTaskService;
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
    public AsyncTask sendTicketReportMessage(TicketReportMessage reportMessage) throws JMSException {
        AsyncTask asyncTask = new AsyncTask(null, null, null, TIMEOUT_SECONDS, RESTART_SECONDS, null, AsyncTaskStatus.PENDING);
        asyncTask = this.asyncTaskService.save(asyncTask);
        reportMessage.setTaskId(asyncTask.getId());
        String message = messageConverter.convertTicketReportMessage(reportMessage);
        asyncTask.setMessage(message);
        this.asyncTaskService.save(asyncTask);
        publisher.send(queue, session.createTextMessage(message));
        return asyncTask;
    }

    @Override
    public AsyncTask sendTicketReportMessage(AsyncTask asyncTask) throws JMSException {
        asyncTask.setStatus(AsyncTaskStatus.PENDING);
        asyncTask.setFinishedAt(null);
        asyncTask.setStart(null);
        this.asyncTaskService.save(asyncTask);
        publisher.send(queue, session.createTextMessage(asyncTask.getMessage()));
        return asyncTask;
    }
}
