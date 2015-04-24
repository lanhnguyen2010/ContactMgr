package vn.kms.launch.contactmgr.infrastructure;


import java.util.UUID;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;

public class TokenService {
    
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
   
    //public static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
       
    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens(){
        //restApiAuthTokenCache.evictExpiredElements();
    }
    public String generateToken(){
        return UUID.randomUUID().toString();
    }
    
    public void storeToken(String token,Authentication authentication){
       // restApiAuthTokenCache.put(new Element(token,authentication));
    }
    public boolean contains(String token) {
      //  return restApiAuthTokenCache.get(token) != null;
        return true;
    }
    
    public Authentication retrieve(String token) {
        //return (Authentication) restApiAuthTokenCache.get(token).getObjectValue();
        return null;
    }
}
