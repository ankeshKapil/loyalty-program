package com.cosmos.app.service;

import com.cosmos.app.service.dto.SchemeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Scheme.
 */
public interface SchemeService {

    /**
     * Save a scheme.
     *
     * @param schemeDTO the entity to save
     * @return the persisted entity
     */
    SchemeDTO save(SchemeDTO schemeDTO);

    /**
     *  Get all the schemes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SchemeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" scheme.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SchemeDTO findOne(Long id);

    /**
     *  Delete the "id" scheme.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
