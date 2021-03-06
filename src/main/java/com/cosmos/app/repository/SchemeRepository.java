package com.cosmos.app.repository;

import com.cosmos.app.domain.Driver;
import com.cosmos.app.domain.Scheme;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Scheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemeRepository extends JpaRepository<Scheme,Long> {

    List<Scheme> findSchemeByDriver(Driver driver);

}
