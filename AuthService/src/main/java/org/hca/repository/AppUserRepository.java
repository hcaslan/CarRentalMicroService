package org.hca.repository;


import org.hca.entity.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AppUserRepository extends MongoRepository<AppUser,String>{
    Optional<AppUser> findByEmail(String email);
}
