package org.iq47.job;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iq47.model.entity.AsyncTask;
import org.iq47.producer.JMSMessageSender;
import org.iq47.service.AsyncTaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@NoArgsConstructor
public class TaskMonitorJob implements Job {


    @Autowired
    AsyncTaskService asyncTaskService;

    @Autowired
    JMSMessageSender jmsMessageSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Job ** {} ** starting @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getFireTime());
        List<AsyncTask> taskList = this.asyncTaskService.getApplicableTimedOutTasks();
        for(AsyncTask task: taskList) {
            try {
                jmsMessageSender.sendTicketReportMessage(task);
                log.info("Task ** {} ** marked as ready for restart", task.getId());
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Error while restarting Task {}", task.getId());
            }
        }

        List<AsyncTask> timeoutTaskList = this.asyncTaskService.timeoutAllExpiredTasks();
        for(AsyncTask task: timeoutTaskList) {
            log.info("Task ** {} ** timed out", task.getId());
        }
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", jobExecutionContext.getJobDetail().getKey().getName(), jobExecutionContext.getNextFireTime());
    }
}
