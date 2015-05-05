package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/**
* Created by thoong on 4/22/2015.
*/
@Service("mailService")
@Transactional
public class MailService {

    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARS = "!@#$%^&*";
    private static final String NUMBERS = "0123456789";

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendRandomPasswordTo(String email) throws MessagingException {
        String randomPassword = getRandomPassword();
        userService.updatePasswordByEmail(email, randomPassword);
        sendMail(email, "[CONTACT MANAGER] - Your New Reset Password", randomPassword);
        return randomPassword;
    }

    private String getRandomPassword() {
        Random randomGenerator = new Random();
        int length = randomGenerator.nextInt(5);

        String randomPassword = "";
        randomPassword += UPPERCASE_CHARS.charAt(randomGenerator.nextInt(UPPERCASE_CHARS.length()));
        randomPassword += LOWERCASE_CHARS.charAt(randomGenerator.nextInt(LOWERCASE_CHARS.length()));
        randomPassword += SPECIAL_CHARS.charAt(randomGenerator.nextInt(SPECIAL_CHARS.length()));
        randomPassword += NUMBERS.charAt(randomGenerator.nextInt(NUMBERS.length()));

        for (int i = 0; i <= length + 1; i++) {
            int pos = randomGenerator.nextInt(UPPERCASE_CHARS.length() + LOWERCASE_CHARS.length()
                    + SPECIAL_CHARS.length() + NUMBERS.length());
            char ch;
            if (pos < 26) ch = UPPERCASE_CHARS.charAt(pos);
            else if (pos < 52) ch = LOWERCASE_CHARS.charAt(pos - 26);
            else if (pos < 60) ch = SPECIAL_CHARS.charAt(pos - 52);
            else ch = NUMBERS.charAt(pos - 60);
            randomPassword += ch;
        }

        return randomPassword;
    }

    private void sendMail(String to, String subject, String bodyMessage) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;

        messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(bodyMessage);

        javaMailSender.send(message);
    }
}
