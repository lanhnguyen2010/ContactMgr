package vn.kms.launch.contactmgr.infrastructure;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;

public  class TokenService {

    long expriesTime = 60*60*1000;
    @Autowired
    UserService userService;
    private MessageDigest digester;
    public TokenService(){
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String generateTokenFirst(String userName, String password) {
        String userNameAndPassword = userName+password;
        StringBuilder sb = new StringBuilder();
        sb.append(userName);
        sb.append(":");
        sb.append(System.currentTimeMillis());
        sb.append(":");
        byte[] hash = digester.digest(userNameAndPassword.getBytes());
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                sb.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                sb.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return sb.toString();
    }
    public byte[] encrypt(String str){
        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        return hash;
        
    }
    public boolean isExpries(String token) {
        long deltaTime = System.currentTimeMillis() - getExpiresToken(token);
        return deltaTime<=expriesTime;
    }

    public long getExpiresToken(String token){
        String[] parts = token.split(":");
        long expires = Long.parseLong(parts[1]);
        return expires;
    }
    
    public String getUserName(String token){
        String[] parts = token.split(":");
        String userString = parts[0];
        return userString;
    }
    
    public User getUserValid(String userName){
        User user = userService.findByUsername(userName);
        return user;
    }
    public boolean isValidUserName(String userName) {
        User user = userService.findByUsername(userName);
        if(user!=null)
            return true;
        return false;
    }
    
    public String getHashUserNameAndPassWord(String token) {
        String[] parts = token.split(":");
        String hash = parts[2];
        return hash;
        
    }
    
    public static void main(String[] args) {
        
        TokenService token = new TokenService();
        String token1 = "andytran:1430656138090:d41d8cd98f00b204e9800998ecf8427e";
        if(token.isExpries(token1))
            System.out.println(token.getHashUserNameAndPassWord(token1));
    }
    
    public String generateTokenWithUserNameAndPassword(String userName,
            String password) {
        String userNameAndPassword = userName+password;
        StringBuilder sb = new StringBuilder();
        byte[] hash = digester.digest(userNameAndPassword.getBytes());
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                sb.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                sb.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return sb.toString();
    }
}
