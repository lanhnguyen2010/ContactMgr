package vn.kms.launch.contactmgr.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import vn.kms.launch.contactmgr.domain.security.UserInfo;
import vn.kms.launch.contactmgr.domain.user.User;

@Component
public class SecurityUtil {
    public static Integer getCurrentUserId(){
        User user = getCurrentUser();
        if(user != null){
            return user.getId();
        }
        
        return null;
    }
    
    public static String getCurrentUserRole(){
        User user = getCurrentUser();
        if(user != null){
            return user.getRole();
        }
        
        return null;
    }
    
    public static String getCurrentUsername(){
        User user = getCurrentUser();
        if(user != null){
            return user.getUsername();
        }
        
        return null;
    }
    
    private static User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && principal instanceof User){
            return (User)principal;
        }
        
        return null;
    }
}
