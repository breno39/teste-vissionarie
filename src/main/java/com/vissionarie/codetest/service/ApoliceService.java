package com.vissionarie.codetest.service;

import com.vissionarie.codetest.domain.Apolice;
import com.vissionarie.codetest.repository.ApoliceRepository;
import com.vissionarie.codetest.service.errors.BadRequestAlertException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApoliceService {

    private final Logger log = LoggerFactory.getLogger(ApoliceService.class);

    private static final String ENTITY_NAME = "apolice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApoliceRepository apoliceRepository;

    public ApoliceService(ApoliceRepository apoliceRepository) {
        this.apoliceRepository = apoliceRepository;
    }

    public Apolice save(Apolice apolice) {
        log.info("saving apolice {}", apolice.hashCode());
        checkIfApoliceIdIsNull(apolice);
        generateUniqueApoliceNumero(apolice);
        Apolice result = apoliceRepository.save(apolice);
        return result;
    }

    public Apolice update(Apolice apolice, String id) {
        log.info("updating apolice {} with {}", id, apolice.hashCode());

        if (!Objects.equals(id, apolice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apoliceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Apolice result = apoliceRepository.save(apolice);
        return result;
    }

    public Optional<Apolice> patch(Apolice apolice, String id) {
        log.info("patching apolice {} with {}", id, apolice.hashCode());

        if (!Objects.equals(id, apolice.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apoliceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apolice> result = apoliceRepository
            .findById(apolice.getId())
            .map(existingApolice -> {
                if (apolice.getNumero() != null) {
                    existingApolice.setNumero(apolice.getNumero());
                }
                if (apolice.getInicio() != null) {
                    existingApolice.setInicio(apolice.getInicio());
                }
                if (apolice.getFim() != null) {
                    existingApolice.setFim(apolice.getFim());
                }
                if (apolice.getPlacaVeiculo() != null) {
                    existingApolice.setPlacaVeiculo(apolice.getPlacaVeiculo());
                }
                if (apolice.getValor() != null) {
                    existingApolice.setValor(apolice.getValor());
                }

                return existingApolice;
            })
            .map(apoliceRepository::save);
        return result;
    }

    public List<Apolice> findAll() {
        log.info("finding all apolices");
        var result = apoliceRepository.findAll();
        return result;
    }

    public Optional<Apolice> findById(String id) {
        log.info("finding apolice {} ", id);
        var result = apoliceRepository.findById(id);
        return result;
    }

    public void deleteById(String id) {
        log.info("deleting apolice {}", id);
        apoliceRepository.deleteById(id);
    }

    public Optional<Apolice> findByNumero(Long numero) {
        log.info("searching apolice with number {}", numero);
        var result = apoliceRepository.findByNumero(numero);
        return result;
    }

    private void checkIfApoliceIdIsNull(Apolice apolice) {
        if (apolice.getId() != null) {
            throw new BadRequestAlertException("A new apolice cannot already have an ID", ENTITY_NAME, "idexists");
        }
    }

    private void generateUniqueApoliceNumero(Apolice apolice) {
        Optional<Apolice> result = apoliceRepository.findByNumero(apolice.generateNumero());
        result.ifPresent(a -> {
            generateUniqueApoliceNumero(apolice);
        });
    }
}
