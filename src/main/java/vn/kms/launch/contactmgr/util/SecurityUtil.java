package vn.kms.launch.contactmgr.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vn.kms.launch.contactmgr.domain.security.SecurityUser;
import vn.kms.launch.contactmgr.domain.security.UserInfo;
import vn.kms.launch.contactmgr.domain.user.User;

@Component
public class SecurityUtil {
    public static Integer getCurrentUserId(){
        SecurityUser user = getCurrentUser();
        if(user != null){
            return user.getId();
        }
        
        return null;
    }
    
    public static String getCurrentUserRole(){
        SecurityUser user = getCurrentUser();
        if(user != null){
            return user.getRole();
        }
        
        return null;
    }
    
    public static String getCurrentUsername(){
        SecurityUser user = getCurrentUser();
        if(user != null){
            return user.getUsername();
        }
        
        return null;
    }
    
    public static UserInfo getUserInfo(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof SecurityUser){
            new UserInfo((SecurityUser) principal);
        }
        
        return null;
    }
    
    private static SecurityUser getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof User){
            return (SecurityUser)principal;
        }
        
        return null;
    }
}
