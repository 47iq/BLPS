package org.iq47.service;

import org.iq47.model.entity.AsyncTask;

import java.util.List;

public interface AsyncTaskService {
    AsyncTask save(AsyncTask asyncTask);
    AsyncTask getById(Long id);
    List<AsyncTask> getApplicableTimedOutTasks();
    List<AsyncTask> timeoutAllExpiredTasks();
}
