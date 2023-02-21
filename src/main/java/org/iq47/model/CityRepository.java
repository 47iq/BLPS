package org.iq47.model;

import org.iq47.model.entity.City;
import org.iq47.model.entity.SellerTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
