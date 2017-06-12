package com.cosmos.app.service.impl;

import com.cosmos.app.service.DriverService;
import com.cosmos.app.domain.Driver;
import com.cosmos.app.repository.DriverRepository;
import com.cosmos.app.service.dto.DriverDTO;
import com.cosmos.app.service.mapper.DriverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing Driver.
 */
@Service
@Transactional
public class DriverServiceImpl implements DriverService{

    private final Logger log = LoggerFactory.getLogger(DriverServiceImpl.class);
    
    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    /**
     * Save a driver.
     * Before converting to Driver Entity 
     * Setting createdOn date and updatedOn date
     * to Current Date
     * @param driverDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DriverDTO save(DriverDTO driverDTO) {
        log.debug("Request to save Driver : {}", driverDTO);
        if(driverDTO.getId()==null){
            driverDTO.setCreatedOn(ZonedDateTime.now());
        }
        driverDTO.setUpdatedOn(ZonedDateTime.now());
        Driver driver = driverMapper.toEntity(driverDTO);
        driver = driverRepository.save(driver);
        DriverDTO result = driverMapper.toDto(driver);
        return result;
    }

    /**
     *  Get all the drivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DriverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drivers");
        Page<Driver> result = driverRepository.findAll(pageable);
        return result.map(driver -> driverMapper.toDto(driver));
    }

    /**
     *  Get one driver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DriverDTO findOne(Long id) {
        log.debug("Request to get Driver : {}", id);
        Driver driver = driverRepository.findOne(id);
        DriverDTO driverDTO = driverMapper.toDto(driver);
        return driverDTO;
    }
    

    /**
     *  Delete the  driver by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Driver : {}", id);
        driverRepository.delete(id);
    }
    
    
    
    /**
     * find driver by the cardNumber.
     * 
     */
	@Override
	@Transactional(readOnly = true)
	public DriverDTO findByCardNumber(Long cardNumber) {
		 log.debug("Request to get Driver : {}", cardNumber);
	        Driver driver = driverRepository.findByCardNumber(cardNumber);
	        DriverDTO driverDTO = driverMapper.toDto(driver);
	        return driverDTO;
	}
   
    
}
