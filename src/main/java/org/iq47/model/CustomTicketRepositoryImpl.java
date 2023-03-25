package org.iq47.model;

import org.iq47.model.entity.City;
import org.iq47.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class CustomTicketRepositoryImpl implements CustomTicketRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Ticket> getTicketsByDepartureCityAndArrivalCity(Pageable pageable, City departure, City arrival, LocalDateTime depDate, LocalDateTime arrDate) {
        Query query = em.createQuery("select a from Ticket a where a.departureCity= :departure and a.arrivalCity= :arrival and a.departureDate> :depDateMin and a.arrivalDate> :arrDateMin and a.departureDate< :depDateMax and a.arrivalDate< :arrDateMax");
        query.setParameter("departure", departure);
        query.setParameter("arrival", arrival);
        query.setParameter("depDateMin", depDate);
        LocalDateTime depDateMax =  depDate.plusHours(24);
        query.setParameter("depDateMax", depDateMax);
        query.setParameter("arrDateMin", arrDate);
        LocalDateTime arrDateMax =  arrDate.plusHours(24);
        query.setParameter("arrDateMax", arrDateMax);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult((pageNumber) * pageSize);
        query.setMaxResults(pageSize);
        List<Ticket> list = query.getResultList();
        Query queryCount = em.createQuery("Select count(a.id) From Ticket a where a.departureCity= :departure and a.arrivalCity= :arrival and a.departureDate> :depDateMin and a.arrivalDate> :arrDateMin and a.departureDate< :depDateMax and a.arrivalDate< :arrDateMax");
        queryCount.setParameter("departure", departure);
        queryCount.setParameter("arrival", arrival);
        queryCount.setParameter("depDateMin", depDate);
        queryCount.setParameter("depDateMax", depDate.plusHours(24));
        queryCount.setParameter("arrDateMin", arrDate);
        queryCount.setParameter("arrDateMax", arrDate.plusHours(24));
        long count = (long) queryCount.getSingleResult();
        return new PageImpl<Ticket>(list, pageable, count);
    }
}
