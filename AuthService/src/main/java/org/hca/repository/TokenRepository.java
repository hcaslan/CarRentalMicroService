package org.hca.repository;

import org.hca.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token,String> {
    Optional<Token> findByToken(String token);
}
