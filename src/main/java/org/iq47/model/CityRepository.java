package org.iq47.model;

import org.iq47.model.entity.City;
import org.iq47.model.entity.SellerTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    List<City> getTop5ItemsByNameContainsIgnoreCase(String query);
    Optional<City> getCityByName(String name);
}
