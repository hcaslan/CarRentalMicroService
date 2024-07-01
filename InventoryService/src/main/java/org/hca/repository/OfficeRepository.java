package org.hca.repository;

import org.hca.entity.Office;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeRepository extends MongoRepository<Office, String> {
    Optional<Office> findByName(String name);
}
