package com.cosmos.app.repository;

import com.cosmos.app.domain.Driver;
import com.cosmos.app.service.dto.DriverDTO;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
	
	Driver findByCardNumber(Long cardNumber);

}
