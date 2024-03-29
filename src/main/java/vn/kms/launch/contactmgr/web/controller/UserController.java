package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.dto.user.ChangePasswordInfo;
import vn.kms.launch.contactmgr.service.MailService;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(method = POST)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return saveUser(user, null);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        return saveUser(user, id);
    }

    @RequestMapping(value = "/search", method = POST)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public SearchResult<User> searchUser(@RequestBody UserSearchCriteria criteria) {
        return userService.searchUsers(criteria);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        int deleteId = userService.deleteUsers(id);
        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : NO_CONTENT);
    }

    @RequestMapping(method = DELETE)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Integer> deleteUsers(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        int deleteId = userService.deleteUsers(ids);
        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : NO_CONTENT);
    }

    @RequestMapping(value = "/active/", method = PUT)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Integer> activeUser(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        Integer result = userService.activeUser(ids);
        return new ResponseEntity<>((result == 0) ? NOT_FOUND : OK);
    }

    @RequestMapping(value = "/deactive/", method = PUT)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Integer> deactiveUser(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        Integer result = userService.deactiveUser(ids);
        return new ResponseEntity<>((result == 0) ? NOT_FOUND : OK);
    }


    @RequestMapping(value = "/roles", method = GET)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<String>> getRoles() {
        List<String> result = userService.getRoles();
        if (result == null) {
            return new ResponseEntity<List<String>>(result,
                BAD_REQUEST);
        }
        return new ResponseEntity<List<String>>(result, OK);
    }

    @RequestMapping(value = "/validate", method = POST)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Object> validateUser(@RequestBody User user) {
        try {
            userService.validateUser(user);
            return new ResponseEntity<>(OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Object>(e.getErrors(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reset_password", method = PUT)
    public ResponseEntity<?> forgetPassword(@RequestParam String email) {
        String randomPassword = null;
        Map<String, Object> exception = new HashMap<>();
        try {
            randomPassword = mailService.sendRandomPasswordTo(email);
        } catch (MessagingException ex) {
            exception.put("message", "The email is not sent because server lost");
            return new ResponseEntity<>(exception, INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException ex) {
            exception.put("message", "The email is not existed");
            return new ResponseEntity<>(exception, BAD_REQUEST);
        }

        exception.put("message", "The new password was sent to your email");
        return new ResponseEntity<>(exception, OK);
    }

    @RequestMapping(value = "/updateLanguage/{language}", method = PUT)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DESIGNER', 'EDITOR')")
    public ResponseEntity<?> updateLanguage(@PathVariable String language) {
        Integer update = userService.updateLanguage(language);
        return new ResponseEntity<>((update == 0) ? NOT_FOUND : OK);
    }

    @RequestMapping(value = "/updatePassword", method = POST)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'DESIGNER', 'EDITOR')")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordInfo changePasswordInfo) {
        try {
            Integer update = userService.updatePassword(changePasswordInfo);
            return new ResponseEntity<>((update == 0) ? NO_CONTENT : OK);
        } catch (ValidationException ex) {
            Map<String, Object> exception = new HashMap<>();
            exception.put("data", changePasswordInfo);
            exception.put("errors", ex.getErrors());
            return new ResponseEntity<>(exception, BAD_REQUEST);
        }
    }

    private ResponseEntity<?> saveUser(User user, Integer id) {
        try {
            User savedContact = userService.saveUser(user, id);
            return new ResponseEntity<>(savedContact, OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (ValidationException e) {
            Map<String, Object> returnObj = new HashMap<>();
            returnObj.put("data", user);
            returnObj.put("errors", e.getErrors());
            return new ResponseEntity<>(returnObj, BAD_REQUEST);
        }
    }


}
