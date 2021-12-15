package com.vissionarie.codetest.web.rest;

import com.vissionarie.codetest.domain.Apolice;
import com.vissionarie.codetest.service.ApoliceService;
import com.vissionarie.codetest.service.dto.ApoliceDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vissionarie.codetest.domain.Apolice}.
 */
@RestController
@RequestMapping("/api")
public class ApoliceResource {

    private final Logger log = LoggerFactory.getLogger(ApoliceResource.class);

    private static final String ENTITY_NAME = "apolice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApoliceService apoliceService;

    public ApoliceResource(ApoliceService apoliceService) {
        this.apoliceService = apoliceService;
    }

    /**
     * {@code POST  /apolices} : Create a new apolice.
     *
     * @param apolice the apolice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apolice, or with status {@code 400 (Bad Request)} if the apolice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apolices")
    public ResponseEntity<Apolice> createApolice(@Valid @RequestBody ApoliceDTO apolice) throws URISyntaxException {
        log.debug("REST request to save Apolice : {}", apolice.hashCode());
        Apolice result = apoliceService.save(apolice.toEntity());
        return ResponseEntity
            .created(new URI("/api/apolices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /apolices/:id} : Updates an existing apolice.
     *
     * @param id the id of the apolice to save.
     * @param apolice the apolice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apolice,
     * or with status {@code 400 (Bad Request)} if the apolice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apolice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apolices/{id}")
    public ResponseEntity<Apolice> updateApolice(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ApoliceDTO apolice
    ) throws URISyntaxException {
        log.debug("REST request to update Apolice : {}, {}", id, apolice.hashCode());
        var result = apoliceService.update(apolice.toEntity(), id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id)).body(result);
    }

    /**
     * {@code PATCH  /apolices/:id} : Partial updates given fields of an existing apolice, field will ignore if it is null
     *
     * @param id the id of the apolice to save.
     * @param apolice the apolice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apolice,
     * or with status {@code 400 (Bad Request)} if the apolice is not valid,
     * or with status {@code 404 (Not Found)} if the apolice is not found,
     * or with status {@code 500 (Internal Server Error)} if the apolice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/apolices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Apolice> partialUpdateApolice(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ApoliceDTO apolice
    ) throws URISyntaxException {
        log.debug("REST request to partial update Apolice partially : {}, {}", id, apolice.hashCode());
        var result = apoliceService.patch(apolice.toEntity(), id);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id));
    }

    /**
     * {@code GET  /apolices} : get all the apolices.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apolices in body.
     */
    @GetMapping("/apolices")
    public List<Apolice> getAllApolices() {
        log.debug("REST request to get all Apolices");
        return apoliceService.findAll();
    }

    /**
     * {@code GET  /apolices/:id} : get the "id" apolice.
     *
     * @param id the id of the apolice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apolice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apolices/{id}")
    public ResponseEntity<Apolice> getApolice(@PathVariable String id) {
        log.debug("REST request to get Apolice : {}", id);
        Optional<Apolice> apolice = apoliceService.findById(id);
        return ResponseUtil.wrapOrNotFound(apolice);
    }

    /**
     * {@code DELETE  /apolices/:id} : delete the "id" apolice.
     *
     * @param id the id of the apolice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apolices/{id}")
    public ResponseEntity<Void> deleteApolice(@PathVariable String id) {
        log.debug("REST request to delete Apolice : {}", id);
        apoliceService.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    @GetMapping("/apolices/numero/{numero}")
    public ResponseEntity<Apolice> findByNumero(@PathVariable(value = "numero") String numero) {
        log.debug("REST request to get Apolice with numero : {}", numero);
        Optional<Apolice> apolice = apoliceService.findByNumero(Long.valueOf(numero));
        return ResponseUtil.wrapOrNotFound(apolice);
    }
}
