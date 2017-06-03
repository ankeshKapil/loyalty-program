package com.cosmos.app.service.mapper;

import com.cosmos.app.domain.*;
import com.cosmos.app.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Transaction and its DTO TransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class, })
public interface TransactionMapper extends EntityMapper <TransactionDTO, Transaction> {
    @Mapping(source = "driver.id", target = "driverId")
    @Mapping(source = "driver.cardNumber", target = "driverCardNumber")
    TransactionDTO toDto(Transaction transaction); 
    @Mapping(source = "driverId", target = "driver")
    Transaction toEntity(TransactionDTO transactionDTO); 
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
