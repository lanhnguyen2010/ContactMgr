package vn.kms.launch.contactmgr.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;

@Component
public class UserUtil {
    @Autowired
    UserRepository userRepo;

    public User getCurrentLoggedUser() {
        User user = userRepo.findOne(10); // I only test
        if (user == null || !user.isActive()
                || user.getExpiredDate().before(new Date())) {
            return null;
        }

        return user;
    }
}
