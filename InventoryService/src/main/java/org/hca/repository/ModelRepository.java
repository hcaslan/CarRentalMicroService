package org.hca.repository;

import org.hca.entity.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends MongoRepository<Model, String> {
}
