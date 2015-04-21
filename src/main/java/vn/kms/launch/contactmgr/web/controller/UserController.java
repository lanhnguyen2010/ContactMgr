package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
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

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return saveUser(user, null);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        return saveUser(user, id);
    }


    @RequestMapping(value = "/search", method = POST)
    public SearchResult<User> searchUser( @RequestBody UserSearchCriteria criteria) {
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
        int result = userService.activeUser(ids);
        if (result == ids.length) {
            return new ResponseEntity<Integer>(result,
                HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/deactive/", method = PUT)
    public ResponseEntity<Integer> deactiveUser(@RequestParam int... ids) {
        int result = userService.deactiveUser(ids);
        if (result == ids.length) {
            return new ResponseEntity<Integer>(result,
                HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/roles", method = GET)
    public ResponseEntity<List<String>> getRoles() {
        List<String> result = userService.getRoles();
        if (result == null) {
            return new ResponseEntity<List<String>>(result,
                HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<String>>(result, HttpStatus.OK);
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
