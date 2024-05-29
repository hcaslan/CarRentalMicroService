package org.hca.service;

import lombok.AllArgsConstructor;
import org.hca.constant.EnvironmentProperties;
import org.hca.dto.request.CreatePasswordRequest;
import org.hca.dto.request.RegistrationRequest;
import org.hca.entity.AppUser;
import org.hca.entity.Token;
import org.hca.entity.enums.AppUserRole;
import org.hca.exception.AuthServiceException;
import org.hca.exception.ErrorType;
import org.hca.mapper.AppUserMapper;
import org.hca.model.MailModel;
import org.hca.model.ProfileSaveModel;
import org.hca.util.MailUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hca.constant.Constants.SBJ_ACTIVATION;
import static org.hca.constant.Constants.SBJ_PASSWORD_CHANGE;
import static org.hca.constant.EndPoints.*;

@Service
@AllArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final AppUserService appUserService;
    private final RabbitTemplate rabbitTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private MailUtil mailUtil;
    private final EnvironmentProperties environmentProperties;
    @Transactional
    public String createPassword(CreatePasswordRequest request) {
        AppUser appUser = appUserService.findByEmail(request.email()).orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
        tokenService.confirmToken(request.token());
        if(!request.password().equals(request.passwordConfirm())) throw new AuthServiceException(ErrorType.PASSWORDS_NOT_MATCHING);
        appUser.setPassword(bCryptPasswordEncoder.encode(request.password()));
        appUserService.update(appUser);
        return "Email = " + appUser.getEmail() + " : Password Successfully Created";
    }
    public void resetPassword(String email) {
        AppUser appUser = appUserService.findByEmail(email).orElseThrow(()-> new AuthServiceException(ErrorType.USER_NOT_FOUND));
        MailModel mailModel = MailModel.builder()
                .to(appUser.getEmail())
                .message(mailUtil.passwordChangeEmail(appUser.getFirstname(), createAccessToken(appUser)))
                .subject(SBJ_PASSWORD_CHANGE)
                .build();
        rabbitTemplate.convertAndSend("exchange.direct.mail", "Routing.Mail", mailModel);
    }
    public String register(RegistrationRequest request) {
        AppUser appUser = AppUserMapper.INSTANCE.registrationRequestToAppUser(request);
        appUser.setAppUserRole(AppUserRole.USER);
        String token = signUpUser(appUser);

        String confirmationLink = "http://localhost:"
                + environmentProperties.getPort()
                + CONFIRM
                + "?token="
                + token;

        MailModel mailModel = MailModel.builder()
                .to(request.getEmail())
                .message(mailUtil.activationEmail(request.getFirstname(), confirmationLink))
                .subject(SBJ_ACTIVATION)
                .build();
        ProfileSaveModel saveModel = ProfileSaveModel.builder()
                .firstName(appUser.getFirstname())
                .lastName(appUser.getLastname())
                .email(appUser.getEmail())
                .authId(appUser.getId())
                .build();
        rabbitTemplate.convertAndSend("exchange.direct.mail", "Routing.Mail", mailModel);
        rabbitTemplate.convertAndSend("exchange.direct.createProfile", "Routing.createProfile", saveModel);
        return token;
    }
    private String signUpUser(AppUser appUser) {
        if (appUserService.findByEmail((appUser.getEmail())).isPresent()) {
            throw new AuthServiceException(ErrorType.USER_ALREADY_EXIST);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUserService.save(appUser);

        return createAccessToken(appUser);
    }
    private String createAccessToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(
                token,
                appUser.getId(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );
        System.out.println(confirmationToken);
        tokenService.save(confirmationToken);
        return token;
    }
}
