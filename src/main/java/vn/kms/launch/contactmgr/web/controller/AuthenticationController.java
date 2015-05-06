package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.parboiled.parserunners.ProfilingParseRunner.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.service.security.TokenService;
import vn.kms.launch.contactmgr.util.HashString;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Object> authenticate(
            @RequestHeader(value = "X-Auth-Username") String username,
            @RequestHeader(value = "X-Auth-Password") String password){

        if (username.length() == 0 || password.length() == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        User user = credentialsValid(username, password);
        if (user == null) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        if (!isUserValid(user)) {
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        String token = tokenService.generateToken(user.getUsername(),password);
        Map<String, String> responseData = new HashMap<String, String>();
        responseData.put("resetPasswordFlag", String.valueOf(user.getResetPasswordFlag()));
        responseData.put("token", token);
        return new ResponseEntity<Object>(responseData, OK);
    }
    
    private boolean isUserValid(User user) {
        Date date = new Date();
        long expired = user.getExpiredDate().getTime();
        if (date.getTime() > expired || !user.isActive())
            return false;
        return true;
    }

    private User credentialsValid(String username, String password) {
        User user = userService.findByUsernameOrEmail(username);
        if (user != null && user.getPassword().equals(HashString.MD5(password))) {
            return user;
        }
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String Logout() {
        return "";
    }
}
