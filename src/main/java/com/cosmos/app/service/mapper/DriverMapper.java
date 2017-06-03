package com.cosmos.app.service.mapper;

import com.cosmos.app.domain.*;
import com.cosmos.app.service.dto.DriverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverMapper extends EntityMapper <DriverDTO, Driver> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Driver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
