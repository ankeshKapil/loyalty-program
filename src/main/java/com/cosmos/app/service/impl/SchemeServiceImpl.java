package com.cosmos.app.service.impl;

import com.cosmos.app.service.SchemeService;
import com.cosmos.app.domain.Scheme;
import com.cosmos.app.repository.SchemeRepository;
import com.cosmos.app.service.dto.SchemeDTO;
import com.cosmos.app.service.mapper.SchemeMapper;
import com.cosmos.app.web.rest.errors.CustomParameterizedException;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Service Implementation for managing Scheme.
 */
@Service
@Transactional
public class SchemeServiceImpl implements SchemeService{

    private final Logger log = LoggerFactory.getLogger(SchemeServiceImpl.class);

    private final SchemeRepository schemeRepository;

    private final SchemeMapper schemeMapper;

    public SchemeServiceImpl(SchemeRepository schemeRepository, SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
    }

    /**
     * Save a scheme.
     *
     * @param schemeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchemeDTO save(SchemeDTO schemeDTO){
        log.debug("Request to save Scheme : {}", schemeDTO);
        Scheme scheme = schemeMapper.toEntity(schemeDTO);
        alreadySchemeExists(scheme);
        scheme = schemeRepository.save(scheme);
        SchemeDTO result = schemeMapper.toDto(scheme);
        return result;
    }

    /**
     *  Get all the schemes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchemeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Schemes");
        Page<Scheme> result = schemeRepository.findAll(pageable);
        return result.map(scheme -> schemeMapper.toDto(scheme));
    }

    /**
     *  Get one scheme by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SchemeDTO findOne(Long id) {
        log.debug("Request to get Scheme : {}", id);
        Scheme scheme = schemeRepository.findOne(id);
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
        return schemeDTO;
    }

    /**
     *  Delete the  scheme by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scheme : {}", id);
        schemeRepository.delete(id);
    }

    private void alreadySchemeExists(Scheme scheme){

        List<Scheme> schemesOnCard=schemeRepository.findSchemeByDriver(scheme.getDriver());
        LocalDate startdate=scheme.getStartDate();
        LocalDate enddate=scheme.getEndDate();
        for (Scheme s:schemesOnCard) {
            LocalDate startDateDB=s.getStartDate();
            LocalDate endDateDB=s.getEndDate();

            if(startdate.compareTo(endDateDB)<=0 && startDateDB.compareTo(enddate)<=0){
                throw new CustomParameterizedException("A Scheme Already Exist on this card with start date "+s.getStartDate()+" and end date "+s.getEndDate());
            }

        }
        



    }
}
