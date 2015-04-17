package vn.kms.launch.contactmgr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.domain.greeting.GreetingSearchCriteria;
import vn.kms.launch.contactmgr.service.GreetingService;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/api/greetings")
public class GreetingController {
    @Autowired
    private GreetingService greetingService;

    @RequestMapping(value = "/search", method = POST)
    public SearchResult<Greeting> searchGreetings(@RequestBody GreetingSearchCriteria criteria) {
        return greetingService.searchGreetings(criteria);
    }

    @RequestMapping(value = "/validate", method = POST)
    public ResponseEntity<Object> validateGreeting(@RequestBody Greeting greeting) {
        try {
            greetingService.validateGreeting(greeting);
            return new ResponseEntity<>(OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Object>(e.getErrors(), BAD_REQUEST);
        }
    }

    @RequestMapping(method = GET)
    public List<Greeting> getGreetings(@RequestParam(value = "displayCode", required = false) String displayCode) {
        return greetingService.getGreetings(displayCode);
    }

    @RequestMapping(value = "/{code}", method = GET)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("code") String code,
                                                @RequestParam(value = "displayCode", required = false) String displayCode) {
        Greeting greeting = greetingService.getGreeting(code, displayCode);

        if (greeting == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(greeting, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> createGreeting(@RequestBody Greeting greeting) {
        return saveGreeting(greeting, null);
    }

    @RequestMapping(value = "/{code}", method = PUT)
    public ResponseEntity<?> updateGreeting(@PathVariable("code") String code,
                               @RequestBody Greeting greeting) {
        return saveGreeting(greeting, code);
    }

    @RequestMapping(value = "/{code}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGreeting(@PathVariable("code") String code) {
        greetingService.deleteGreeting(code);
    }

    private ResponseEntity<?> saveGreeting(Greeting greeting, String code) {
        try {
            Greeting savedGreeting = greetingService.saveGreeting(code, greeting.getMessage());
            return new ResponseEntity<>(savedGreeting, OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, NOT_FOUND);
        } catch (ValidationException e) {
            Map<String, Object> returnObj = new HashMap<>();
            returnObj.put("data", greeting);
            returnObj.put("errors", e.getErrors());
            return new ResponseEntity<>(returnObj, BAD_REQUEST);
        }
    }
}
