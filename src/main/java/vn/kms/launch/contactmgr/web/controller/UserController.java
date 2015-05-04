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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.dto.user.ChangeLanguageInfo;
import vn.kms.launch.contactmgr.dto.user.ChangePasswordInfo;
import vn.kms.launch.contactmgr.service.MailService;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.PasswordNotExistException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;
@RestController
@RequestMapping(value = "/api/users")
//@PreAuthorize("hasRole('ADMINISTRATOR')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(method = POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return saveUser(user, null);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        return saveUser(user, id);
    }

    @RequestMapping(value = "/search", method = POST)
    public SearchResult<User> searchUser(@RequestBody UserSearchCriteria criteria) {
        return userService.searchUsers(criteria);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        int deleteId = userService.deleteUsers(id);
        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : NO_CONTENT);
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<Integer> deleteUsers(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        int deleteId = userService.deleteUsers(ids);
        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : NO_CONTENT);
    }

    @RequestMapping(value = "/active/", method = PUT)
    public ResponseEntity<Integer> activeUser(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        Integer result = userService.deactiveUser(ids);
        return new ResponseEntity<>((result == 0) ? NOT_FOUND : OK);
    }

    @RequestMapping(value = "/deactive/", method = PUT)
    public ResponseEntity<Integer> deactiveUser(@RequestParam int... ids) {
        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        Integer result = userService.deactiveUser(ids);
        return new ResponseEntity<>((result == 0) ? NOT_FOUND : OK);
    }


    @RequestMapping(value = "/roles", method = GET)
    public ResponseEntity<List<String>> getRoles() {
        List<String> result = userService.getRoles();
        if (result == null) {
            return new ResponseEntity<List<String>>(result,
                BAD_REQUEST);
        }
        return new ResponseEntity<List<String>>(result, OK);
    }

    @RequestMapping(value = "/validate", method = POST)
    public ResponseEntity<Object> validateUser(@RequestBody User user) {
        try {
            userService.validateUser(user);
            return new ResponseEntity<>(OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Object>(e.getErrors(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/reset_password", method = PUT)
    public ResponseEntity<String> forgetPassword(@RequestParam String email) {
        String randomPassword = null;
        try {
            randomPassword = mailService.sendRandomPasswordTo(email);
        } catch (MessagingException exception) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "/updateLanguage", method = PUT)
    public ResponseEntity<?> updateLanguage(@RequestBody ChangeLanguageInfo changeLanguageInfo) {
        Integer update = userService.updateLanguage(changeLanguageInfo);
        return new ResponseEntity<>((update == 0) ? NOT_FOUND : OK);
    }

    @RequestMapping(value = "/updatePassword", method = PUT)
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordInfo changePasswordInfo) {
        try {
            Integer update = userService.updatePassword(changePasswordInfo);
            return new ResponseEntity<>((update == 0) ? NO_CONTENT : OK);
        } catch (PasswordNotExistException ex) {
            return new ResponseEntity<Object>(new String(ex.getMessage()), BAD_REQUEST);
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
