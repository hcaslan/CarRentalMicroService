package org.hca.util;

import org.springframework.stereotype.Service;

@Service
public class MailUtil {
    public String activationEmail(String name, String link) {
        return
                "<p> Hi " + name +
                        ",</p>" +
                        "<p> Thank you for registering. Please click on the below link to activate your account: </p>" +
                        "<blockquote><p> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 10 minutes. <p>See you soon</p>";
    }
    public String passwordChangeEmail(String name, String token) {
        return
                "<p> Hi " + name +
                        ",</p>" +
                        "<p> Here is your password change token. You can change your password with this token: </p>" +
                        "<blockquote><p>" + token + "</p></blockquote>\n Token will expire in 10 minutes. <p>See you soon</p>";
    }
    public String buildConfirmedPage(String message){
        return "<p> " + message + "</p>" +
                "<blockquote><p> You can login now ! </p>";
    }
}
