package com.cosmos.app.service.mapper;

import com.cosmos.app.domain.*;
import com.cosmos.app.service.dto.SchemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Scheme and its DTO SchemeDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class, })
public interface SchemeMapper extends EntityMapper <SchemeDTO, Scheme> {
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.cardNumber", target = "driverCardNumber")
    SchemeDTO toDto(Scheme scheme); 
    @Mapping(source = "driverId", target = "driver")
    Scheme toEntity(SchemeDTO schemeDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Scheme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scheme scheme = new Scheme();
        scheme.setId(id);
        return scheme;
    }
}
