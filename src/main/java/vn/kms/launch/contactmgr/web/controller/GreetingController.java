package vn.kms.launch.contactmgr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.service.GreetingService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by trungnguyen on 4/8/15.
 */
@RestController
@RequestMapping(value = "/api/greetings")
public class GreetingController {
    @Autowired
    private GreetingService greetingService;

    @RequestMapping(method = GET)
    @ResponseBody
    public List<Greeting> getGreetings(@RequestParam(value = "displayCode", required = false) String displayCode) {
        return greetingService.getGreetings(displayCode);
    }

    @RequestMapping(value = "/{code}", method = GET)
    public ResponseEntity<Greeting> getGreeting(@PathVariable("code") String lang,
                                                @RequestParam(value = "displayCode", required = false) String displayCode) {
        Greeting greeting = greetingService.getGreeting(lang, displayCode);

        if (greeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createGreeting(@RequestBody Greeting greeting) throws Exception {
        greetingService.saveGreeting(greeting.getCode(), greeting.getMessage());
    }

    @RequestMapping(value = "/{code}", method = PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateGreeting(@PathVariable("code") String code, @RequestBody Greeting greeting) {
        greetingService.saveGreeting(code, greeting.getMessage());
    }

    @RequestMapping(value = "/{code}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGreeting(@PathVariable("code") String code) {
        greetingService.deleteGreeting(code);
    }
}
