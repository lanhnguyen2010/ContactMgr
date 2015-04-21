package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.domain.greeting.GreetingRepository;
import vn.kms.launch.contactmgr.domain.greeting.GreetingSearchCriteria;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;


@Service
@Transactional(readOnly = true)
public class GreetingService {
    @Autowired
    private GreetingRepository greetingRepo;

    @Autowired
    private Validator validator;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public Greeting saveGreeting(String code, String message) throws ValidationException {
        Greeting greeting = new Greeting(code, message);
        if (code != null) {
            greeting = greetingRepo.findByCode(code);
            if (greeting == null) {
                throw new EntityNotFoundException();
            }

            greeting.setMessage(message);
        }

        validateGreeting(greeting);
        return greetingRepo.save(greeting);
    }

    @Transactional
    public boolean deleteGreeting(String code) {
        int effected = greetingRepo.deleteByCode(code);

        return (effected > 0) ? true : false;
    }

    public void validateGreeting(Greeting greeting) throws ValidationException {
        Set<ConstraintViolation<Greeting>> violations = validator.validate(greeting);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
        }
    }

    public Greeting getGreeting(String code, String displayCode) {
        Greeting greeting = greetingRepo.findByCode(code);
        if (greeting == null) {
            return null;
        }

        Locale displayLocale = getDisplayLocale(displayCode);
        Locale locale = new Locale(greeting.getCode());
        greeting.setLanguage(locale.getDisplayLanguage(displayLocale));

        return greeting;
    }

    public List<Greeting> getGreetings(String displayCode) {
        List<Greeting> greetings = new ArrayList<>();

        Locale displayLocale = getDisplayLocale(displayCode);
        for (Greeting greeting : greetingRepo.findAll()) {
            Locale locale = new Locale(greeting.getCode());
            greeting.setLanguage(locale.getDisplayLanguage(displayLocale));
            greetings.add(greeting);
        }

        return greetings;
    }

    public SearchResult<Greeting> searchGreetings(GreetingSearchCriteria criteria) {
        return greetingRepo.searchByCriteria(criteria);
    }

    private Locale getDisplayLocale(String displayCode) {
        if (StringUtils.isEmpty(displayCode)) {
            displayCode = "en";
        }

        return new Locale(displayCode);
    }
}
