package org.hca.bin;

import lombok.RequiredArgsConstructor;
import org.hca.entity.AppUser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AppUserCustomRepositoryImpl implements AppUserCustomRepository{
    private final MongoTemplate mongoTemplate;

    @Override
    public long enableAppUser(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().set("enabled", true);
        return mongoTemplate.updateFirst(query, update, AppUser.class).getMatchedCount();
    }
}
