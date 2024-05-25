package org.hca.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    USERNAME_ALREADY_TAKEN(1001,
            "Bu username daha önce kullanılmış. Yeniden deneyiniz.",
            HttpStatus.BAD_REQUEST),
    EMAIL_OR_PASSWORD_WRONG(1002,
            "Email adı veya parola yanlış.",
            HttpStatus.BAD_REQUEST),
    PASSWORDS_NOT_MATCHED(1003,
            "Girdiğiniz parolalar uyuşmamaktadır. Lütfen kontrol ediniz.",
            HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(2001,
            "Token geçersizdir.",
            HttpStatus.BAD_REQUEST),
    TOKEN_CREATION_FAILED(2002,
            "Token yaratmada hata meydana geldi.",
            HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_VERIFY_FAILED(2003,
            "Token verify etmede bir hata meydana geldi.",
            HttpStatus.SERVICE_UNAVAILABLE),
    TOKEN_ARGUMENT_NOTVALID(2003,
            "Token argümanı yanlış formatta.",
            HttpStatus.BAD_REQUEST),
    URUN_NOT_FOUND(5003,
            "Böyle bir Ürün bulunamadı.",
            HttpStatus.NOT_FOUND),
    MUSTERI_NOT_FOUND(5004,
            "Böyle bir müşteri bulunamadı.",
            HttpStatus.NOT_FOUND),
    BAD_REQUEST_ERROR(5006,
                              "Bad request error",
    HttpStatus.BAD_REQUEST),
    ACOOUNT_NOT_ACTIVE(5007,
            "Hesap aktif değil.",
            HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(5008,
            "Kullanıcı bulunamadı.",
            HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_MISMATCH(5009,
            "Aktivasyon kodu hatalı.",
            HttpStatus.BAD_REQUEST),
    USER_ALREADY_ACTIVE(5010,
            "Kullanıcı zaten aktif.",
            HttpStatus.BAD_REQUEST),
    ACCESS_DENIED(5011,
            "Işlem reddedildi.",
            HttpStatus.BAD_REQUEST),
    ACTIVATION_ERROR(5012,
            "Aktivasyon hatası",
            HttpStatus.BAD_REQUEST),
    USER_ALREADY_DELETED(5013,
            "Kullanıcı zaten silinmiş.",
            HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(6000,
            "Service is not responding.",
            HttpStatus.INTERNAL_SERVER_ERROR);

    private Integer code;
    private String message;
    private HttpStatus httpStatus;

}