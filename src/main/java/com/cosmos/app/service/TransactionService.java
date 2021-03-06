package com.cosmos.app.service;

import com.cosmos.app.service.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Transaction.
 */
public interface TransactionService {

    /**
     * Save a transaction.
     *
     * @param transactionDTO the entity to save
     * @return the persisted entity
     */
    TransactionDTO save(TransactionDTO transactionDTO);

    /**
     *  Get all the transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TransactionDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" transaction.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TransactionDTO findOne(Long id);

    /**
     *  Delete the "id" transaction.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
