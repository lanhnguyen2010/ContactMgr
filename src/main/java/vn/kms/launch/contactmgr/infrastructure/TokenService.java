package vn.kms.launch.contactmgr.infrastructure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;


import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;

public  class TokenService {

     // public static final Cache restApiAuthTokenCache =
    // CacheManager.getInstance().getCache("restApiAuthTokenCache");
    private MessageDigest digester;
    public TokenService(){
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String generateToken(String userName, String password) {
        String userNameAndPassword = userName+password;
        StringBuilder sb = new StringBuilder();
        byte[] hash = digester.digest();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                sb.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                sb.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        sb.append(":");
        sb.append(userName);
        return sb.toString();
    }
    public byte[] encrypt(String str){
        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        return hash;
        
    }
    public void storeToken(String token, Authentication authentication) {
        // restApiAuthTokenCache.put(new Element(token,authentication));
    }

    public boolean contains(String token) {
        // return restApiAuthTokenCache.get(token) != null;
        return true;
    }

    public String getUserName(String token){
        String[] parts = token.split(":");
        String userString = parts[1];
        return userString;
    }
    public static void main(String[] args) {
        
        TokenService token = new TokenService();
        String token1 = token.generateToken("andytran", "2010193");
       // token.retrieve(token1);
   //     System.out.println(token1);
    }
}
