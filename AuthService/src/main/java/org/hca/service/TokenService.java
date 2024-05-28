package org.hca.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.hca.entity.AppUser;
import org.hca.entity.Token;
import org.hca.exception.AuthServiceException;
import org.hca.exception.ErrorType;
import org.hca.model.StatusUpdateModel;
import org.hca.bin.TokenCustomRepositoryImpl;
import org.hca.repository.TokenRepository;
import org.hca.util.MailUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenCustomRepositoryImpl tokenCustomRepository;
    private final AppUserService appUserService;
    private final RabbitTemplate rabbitTemplate;
    private MailUtil mailUtil;
    private final RedisTemplate<String, Token> tokenRedisTemplate;
    private static final String KEY="tokens";

    @PostConstruct
    private void init(){
        if (Boolean.FALSE.equals(tokenRedisTemplate.hasKey(KEY)))
        {
            List<Token> allProducts = tokenRepository.findAll();
            if (!allProducts.isEmpty()) {
                allProducts.forEach(token -> {
                    tokenRedisTemplate.opsForList().rightPush(KEY, token);
                });
            }
        }
    }
    @Transactional
    public String confirmEmailToken(String token) {
        System.out.println("OKEY1-1");
        Token confirmationToken = findByToken(token);
        System.out.println("OKEY1-2");
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException(mailUtil.buildConfirmedPage("Email already confirmed."));
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        setConfirmedAt(token);
        AppUser appUser = appUserService.findById(confirmationToken.getAppUserId());
        appUserService.enableAppUser(appUser.getEmail());
        StatusUpdateModel updateModel = StatusUpdateModel.builder().status("ACTIVE").authId(appUser.getId()).build();
        rabbitTemplate.convertAndSend("exchange.direct.updateStatus","Routing.updateStatus",updateModel);
        return mailUtil.buildConfirmedPage("Email Confirmed");
    }
    @Transactional
    public AppUser confirmToken(String token){
        Token confirmationToken = findByToken(token);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new AuthServiceException(ErrorType.TOKEN_EXPIRED);
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new AuthServiceException(ErrorType.TOKEN_EXPIRED);
        }
        setConfirmedAt(token);
        return appUserService.findById(confirmationToken.getAppUserId());
    }
    public void setConfirmedAt(String token) {
        Token oToken = findByToken(token);
        oToken.setConfirmedAt(LocalDateTime.now());
        update(oToken);
    }
    public void update(Token token) {
        Long index = tokenRedisTemplate.opsForList().indexOf(KEY, findById(token.getId()));
        tokenRepository.save(token);
        tokenRedisTemplate.opsForList().set(KEY,index,token);
    }

    public void save(Token token) {
        tokenRepository.save(token);
        tokenRedisTemplate.opsForList().rightPush(KEY,token);
    }
    public List<Token> getAllCache(){
        return tokenRedisTemplate.opsForList().range(KEY, 0, -1);
    }

    public Token findById(String id) {
        Token token = getAllCache().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (token == null) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
        return token;
    }
    public Token findByToken(String token) {
        System.out.println("OKEY2-1");
        Token oToken = getAllCache().stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst()
                .orElse(null);
        System.out.println("OKEY2-2");
        if (oToken == null) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
        return oToken;
    }
}
