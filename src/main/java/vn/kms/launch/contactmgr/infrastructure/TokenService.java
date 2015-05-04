package vn.kms.launch.contactmgr.infrastructure;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.util.HashString;

public  class TokenService {

    private static final long EXPIRED_TIME = 60*60*1000;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    
    @Autowired
    UserService userService;
    private MessageDigest digester;
    
    
    public TokenService(){
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("TokenService: " + e.getMessage());
        }
    }
    
    public String extractUsername(String token){
        String[] parts = token.split(":");
        String userString = parts[0];
        return userString;
    }
    
    public User getUser(String userName){
        User user = userService.findByUsername(userName);
        return user;
    }
    
    public String getHashUserNameAndPassWord(String token) {
        String[] parts = token.split(":");
        String hash = parts[2];
        return hash;
    }
    
    public boolean isExpries(String token) {
        long deltaTime = System.currentTimeMillis() - getExpiredTimeToken(token);
        return deltaTime <= EXPIRED_TIME;
    }

    private long getExpiredTimeToken(String token){
        String[] parts = token.split(":");
        long expires = Long.parseLong(parts[1]);
        return expires;
    }
    
    public String generateToken(String username, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append(username);
        sb.append(":");
        sb.append(System.currentTimeMillis());
        sb.append(":");
        sb.append(HashString.MD5(username+password));
        return sb.toString();
    }
}
