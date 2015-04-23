package vn.kms.launch.contactmgr.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;

@Component
public class SecurityUtil {
    public static Integer getCurrentUserId(){
        return 10; // only for test
    }
    
    public static String getCurrentUserRole(){
        return Role.ADMINISTRATOR.name(); // only for test
    }
    
    public static String getCurrentUsername(){
        return null;
    }
}
