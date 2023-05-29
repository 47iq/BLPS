package org.iq47.service;

import org.iq47.model.entity.AsyncTask;
import org.iq47.model.entity.AsyncTaskStatus;
import org.iq47.model.repo1.AsyncTaskRepository;
import org.iq47.model.repo1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {

    AsyncTaskRepository repository;

    public AsyncTaskServiceImpl(AsyncTaskRepository repository) {
        this.repository = repository;
    }

    public Optional<AsyncTask> getById(Long id) {
        return repository.findById(id);
    }

    public AsyncTask save(AsyncTask asyncTask) {
        return repository.save(asyncTask);
    }

    public List<AsyncTask> getApplicableTimedOutTasks() {
        return repository.getApplicableTimedOutTasks();
    }

    public List<AsyncTask> timeoutAllExpiredTasks() {
        List<AsyncTask> timedOut =  repository.getAboutToTimeOutTasks();
        for(AsyncTask task: timedOut) {
            task.setStatus(AsyncTaskStatus.TIMED_OUT);
            task.setFinishedAt(LocalDateTime.now());
            repository.save(task);
        }
        return timedOut;
    }
}
