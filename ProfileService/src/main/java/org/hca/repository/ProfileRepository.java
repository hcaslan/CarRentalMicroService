package org.hca.repository;

import org.hca.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile,String> {
    Optional<Profile> findByAuthId(String authId);

    Optional<Profile> findByEmail(String email);
}
