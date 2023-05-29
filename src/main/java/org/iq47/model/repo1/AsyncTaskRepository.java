package org.iq47.model.repo1;

import org.iq47.model.entity.AsyncTask;
import org.iq47.model.entity.SellerTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsyncTaskRepository extends JpaRepository<AsyncTask, Long> {

    @Query(value = "SELECT * FROM task WHERE status=3 AND finished_at + restart_period * interval '1 second' < NOW()", nativeQuery = true)
    List<AsyncTask> getApplicableTimedOutTasks();

    @Query(value = "SELECT * FROM task WHERE status=1 AND start + timeout * interval '1 second' < NOW()", nativeQuery = true)
    List<AsyncTask> getAboutToTimeOutTasks();
}
