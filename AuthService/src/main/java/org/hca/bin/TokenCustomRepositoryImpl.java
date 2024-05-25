package org.hca.bin;

import org.hca.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class TokenCustomRepositoryImpl implements TokenCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        Query query = new Query(Criteria.where("token").is(token));
        Update update = new Update().set("confirmedAt", confirmedAt);
        return mongoTemplate.updateFirst(query, update, Token.class).getMatchedCount();
    }
}
