package org.hca.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.hca.dto.request.ChangePasswordRequest;
import org.hca.entity.AppUser;
import org.hca.exception.AuthServiceException;
import org.hca.exception.ErrorType;
import org.hca.model.ProfileUpdateModel;
import org.hca.model.StatusUpdateModel;
import org.hca.repository.AppUserRepository;
import org.hca.util.JwtUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RabbitTemplate rabbitTemplate;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String,AppUser> appUserRedisTemplate;
    private static final String KEY="appUsers";
    @PostConstruct
    private void init(){
        if (Boolean.FALSE.equals(appUserRedisTemplate.hasKey(KEY)))
        {
            List<AppUser> allProducts = appUserRepository.findAll();
            allProducts.forEach(appUser -> {
                appUserRedisTemplate.opsForList().rightPush(KEY,appUser);
            });
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email).orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
    }

    public String softDeleteAppUser(String appUserId) {
        AppUser appUser = findById(appUserId);
        if (appUser.getDeleted()) throw new AuthServiceException(ErrorType.USER_ALREADY_DELETED);
        appUser.setDeleted(true);
        save(appUser);
        StatusUpdateModel updateModel = StatusUpdateModel.builder().status("DELETED").authId(appUser.getId()).build();
        rabbitTemplate.convertAndSend("exchange.direct.updateStatus","Routing.updateStatus",updateModel);
        return "Id = " + appUserId + " : Successfully Deleted";
    }

    @RabbitListener(queues = "q.profile.update")
    private void updateAppUser(ProfileUpdateModel updateModel){
        AppUser appUser = findById(updateModel.getAuthId());
        appUser.setFirstname(updateModel.getFirstName());
        appUser.setLastname(updateModel.getLastName());
        appUser.setEmail(updateModel.getEmail());
        update(appUser);
    }
    @Transactional
    public String changePassword(ChangePasswordRequest request) {
        AppUser appUser = findByEmail(request.email()).orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
        jwtUtil.validateToken(request.token(), appUser);
        if(!request.newPassword().equals(request.newPasswordConfirm())) throw new AuthServiceException(ErrorType.PASSWORDS_NOT_MATCHING);
        if (!bCryptPasswordEncoder.matches(request.password(), appUser.getPassword())) {
            throw new AuthServiceException(ErrorType.INCORRECT_EMAIL_OR_PASSWORD);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(request.newPassword());
        appUser.setPassword(encodedPassword);
        save(appUser);
        return "Email = " + appUser.getEmail() + " : Password Successfully Changed";
    }

    public void enableAppUser(String email) {
        AppUser appUser = findByEmail(email).orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
        appUser.setEnabled(true);
        update(appUser);
    }

    public void update(AppUser appUser) {
        Long index = appUserRedisTemplate.opsForList().indexOf(KEY, findById(appUser.getId()));
        appUserRepository.save(appUser);
        appUserRedisTemplate.opsForList().set(KEY,index,appUser);
    }

    public void save(AppUser appUser) {
        appUserRepository.save(appUser);
        appUserRedisTemplate.opsForList().rightPush(KEY,appUser);
    }
    public List<AppUser> getAllCache(){
        return appUserRedisTemplate.opsForList().range(KEY, 0, -1);
    }
    public AppUser findById(String id){
        return getAllCache().stream().filter(appUser -> appUser.getId().equals(id)).findFirst().orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
    }
    public Optional<AppUser> findByEmail(String email){
        return getAllCache().stream().filter(appUser -> appUser.getEmail().equals(email)).findFirst();
    }
}
