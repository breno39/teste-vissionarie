package com.vissionarie.codetest.repository;

import com.vissionarie.codetest.domain.Apolice;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Apolice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApoliceRepository extends MongoRepository<Apolice, String> {
    Optional<Apolice> findByNumero(Long numero);
}
