package org.iq47.model.repo1;

import org.iq47.model.entity.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}
