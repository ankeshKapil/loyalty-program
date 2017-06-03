package com.cosmos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cosmos.app.service.SchemeService;
import com.cosmos.app.web.rest.util.HeaderUtil;
import com.cosmos.app.web.rest.util.PaginationUtil;
import com.cosmos.app.service.dto.SchemeDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Scheme.
 */
@RestController
@RequestMapping("/api")
public class SchemeResource {

    private final Logger log = LoggerFactory.getLogger(SchemeResource.class);

    private static final String ENTITY_NAME = "scheme";
        
    private final SchemeService schemeService;

    public SchemeResource(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    /**
     * POST  /schemes : Create a new scheme.
     *
     * @param schemeDTO the schemeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schemeDTO, or with status 400 (Bad Request) if the scheme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schemes")
    @Timed
    public ResponseEntity<SchemeDTO> createScheme(@Valid @RequestBody SchemeDTO schemeDTO) throws URISyntaxException {
        log.debug("REST request to save Scheme : {}", schemeDTO);
        if (schemeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new scheme cannot already have an ID")).body(null);
        }
        SchemeDTO result = schemeService.save(schemeDTO);
        return ResponseEntity.created(new URI("/api/schemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schemes : Updates an existing scheme.
     *
     * @param schemeDTO the schemeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schemeDTO,
     * or with status 400 (Bad Request) if the schemeDTO is not valid,
     * or with status 500 (Internal Server Error) if the schemeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schemes")
    @Timed
    public ResponseEntity<SchemeDTO> updateScheme(@Valid @RequestBody SchemeDTO schemeDTO) throws URISyntaxException {
        log.debug("REST request to update Scheme : {}", schemeDTO);
        if (schemeDTO.getId() == null) {
            return createScheme(schemeDTO);
        }
        SchemeDTO result = schemeService.save(schemeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schemeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schemes : get all the schemes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schemes in body
     */
    @GetMapping("/schemes")
    @Timed
    public ResponseEntity<List<SchemeDTO>> getAllSchemes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Schemes");
        Page<SchemeDTO> page = schemeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schemes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /schemes/:id : get the "id" scheme.
     *
     * @param id the id of the schemeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schemeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/schemes/{id}")
    @Timed
    public ResponseEntity<SchemeDTO> getScheme(@PathVariable Long id) {
        log.debug("REST request to get Scheme : {}", id);
        SchemeDTO schemeDTO = schemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schemeDTO));
    }

    /**
     * DELETE  /schemes/:id : delete the "id" scheme.
     *
     * @param id the id of the schemeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schemes/{id}")
    @Timed
    public ResponseEntity<Void> deleteScheme(@PathVariable Long id) {
        log.debug("REST request to delete Scheme : {}", id);
        schemeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
