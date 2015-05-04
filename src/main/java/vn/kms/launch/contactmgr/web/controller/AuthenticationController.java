package vn.kms.launch.contactmgr.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class AuthenticationController {
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate() {
        return "";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String Logout() {
        return "";
    }
}
