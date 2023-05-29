package org.iq47.service;

import org.iq47.model.entity.AsyncTask;

import java.util.List;
import java.util.Optional;

public interface AsyncTaskService {
    AsyncTask save(AsyncTask asyncTask);
    Optional<AsyncTask> getById(Long id);
    List<AsyncTask> getApplicableTimedOutTasks();
    List<AsyncTask> timeoutAllExpiredTasks();
}
