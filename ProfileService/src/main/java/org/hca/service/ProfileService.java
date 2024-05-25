package org.hca.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.hca.dto.request.ProfileUpdateRequestDto;
import org.hca.entity.EStatus;
import org.hca.entity.Profile;
import org.hca.exception.ErrorType;
import org.hca.exception.ProfileServiceException;
import org.hca.mapper.ProfileMapper;
import org.hca.model.ProfileSaveModel;
import org.hca.model.ProfileUpdateModel;
import org.hca.model.StatusUpdateModel;
import org.hca.repository.ProfileRepository;
import org.hca.utility.JwtUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final JwtUtil jwtUtil;
    private final ProfileMapper profileMapper;
    private final RabbitTemplate rabbitTemplate;
    private final RedisTemplate<String,Profile> profileRedisTemplate;
    private static final String KEY="profiles";
    @PostConstruct
    private void init(){
        if (Boolean.FALSE.equals(profileRedisTemplate.hasKey(KEY)))
        {
            List<Profile> allProducts = profileRepository.findAll();
            allProducts.forEach(profile -> {
                profileRedisTemplate.opsForList().rightPush(KEY,profile);
            });
        }
    }

    @Transactional
    @RabbitListener(queues = "q.profile.create")
    public void saveProfile(ProfileSaveModel dto) {
        save(ProfileMapper.INSTANCE.dtoToProfile(dto));
        Profile save = findByAuthId(dto.getAuthId());
        if (save == null)
            throw new ProfileServiceException(ErrorType.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public String updateProfile(ProfileUpdateRequestDto dto) {
        if (jwtUtil.isTokenExpired(dto.getToken())) throw new ProfileServiceException(ErrorType.ACCESS_DENIED);
        String email = jwtUtil.extractUsername(dto.getToken());
        Profile profile = findByEmail(email);
        ProfileUpdateModel profileUpdateModel = ProfileUpdateModel.builder()
                .authId(profile.getAuthId())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .build();
        if (isNullOrEmptyOrWhitespace(dto.getPhone())) profile.setPhone(dto.getPhone());
        if (isNullOrEmptyOrWhitespace(dto.getFirstName())) {
            profile.setFirstName(dto.getFirstName());
            profileUpdateModel.setFirstName(profile.getFirstName());
        }
        if (isNullOrEmptyOrWhitespace(dto.getLastName())) {
            profile.setLastName(dto.getLastName());
            profileUpdateModel.setLastName(profile.getLastName());
        }
        if (isNullOrEmptyOrWhitespace(dto.getEmail())) {
            profile.setEmail(dto.getEmail());
            profileUpdateModel.setEmail(profile.getEmail());
        }
        rabbitTemplate.convertAndSend("exchange.direct.profileUpdate","Routing.ProfileUpdate",profileUpdateModel);
        update(profile);
        return "Update Success!";
    }
    @Transactional
    @RabbitListener(queues = "q.status.update")
    public void updateStatus(StatusUpdateModel updateModel) {
        Profile profile = findByAuthId(updateModel.getAuthId());
        profile.setStatus(EStatus.valueOf(updateModel.getStatus()));
        update(profile);
    }
    private static boolean isNullOrEmptyOrWhitespace(String str) {
        if (str == null) {
            return false;
        }
        return !str.trim().isEmpty();
    }

    public void update(Profile profile) {
        Long index = profileRedisTemplate.opsForList().indexOf(KEY, findById(profile.getId()));
        profileRepository.save(profile);
        profileRedisTemplate.opsForList().set(KEY,index,profile);
    }

    public void save(Profile profile) {
        profileRepository.save(profile);
        profileRedisTemplate.opsForList().rightPush(KEY,profile);
    }
    public List<Profile> getAllCache(){
        return profileRedisTemplate.opsForList().range(KEY, 0, -1);
    }
    public Profile findById(String id){
        return getAllCache().stream().filter(profile -> profile.getId().equals(id)).findFirst().orElseThrow(()-> new ProfileServiceException(ErrorType.USER_NOT_FOUND));
    }
    public Profile findByAuthId(String authId){
        return getAllCache().stream().filter(profile -> profile.getAuthId().equals(authId)).findFirst().orElseThrow(()-> new ProfileServiceException(ErrorType.USER_NOT_FOUND));
    }
    public Profile findByEmail(String email){
        return getAllCache().stream().filter(profile -> profile.getEmail().equals(email)).findFirst().orElseThrow(()-> new ProfileServiceException(ErrorType.USER_NOT_FOUND));
    }
}
