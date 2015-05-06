package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.security.UserInfo;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.util.SecurityUtil;

@RestController
@RequestMapping(value = "/api/security")
public class SecurityController {
    @Autowired
    UserRepository userRepos;

    @RequestMapping(value = "/current-user", method = GET)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DESIGNER', 'EDITOR')")
    public ResponseEntity<UserInfo> getCurrentUser() {
        UserInfo userInfo = SecurityUtil.getUserInfo();
        return new ResponseEntity<>(userInfo, (userInfo == null) ? NOT_FOUND : OK);
    }
}
