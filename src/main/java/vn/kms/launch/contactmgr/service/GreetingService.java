package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import vn.kms.launch.contactmgr.Constants;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.domain.greeting.GreetingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static vn.kms.launch.contactmgr.Constants.SYSTEM_USER;

/**
 * Created by trungnguyen on 4/8/15.
 */
@Service
@Transactional(readOnly = true)
public class GreetingService {
    @Autowired
    private GreetingRepository greetingRepo;

    @Transactional
    public void saveGreeting(String code, String message) {
        Greeting greeting = greetingRepo.findByCode(code);
        if (greeting == null) {
            greeting = new Greeting(code, message);
            greeting.setCreatedAt(new Date());
            greeting.setCreatedBy(SYSTEM_USER);
        } else {
            greeting.setMessage(message);
            greeting.setUpdatedAt(new Date());
            greeting.setUpdatedBy(SYSTEM_USER);
        }

        greetingRepo.save(greeting);
    }

    @Transactional
    public boolean deleteGreeting(String code) {
        int effected = greetingRepo.deleteByCode(code);

        return (effected > 0)? true : false;
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

    private Locale getDisplayLocale(String displayCode) {
        if (StringUtils.isEmpty(displayCode)) {
            displayCode = "en";
        }

        return new Locale(displayCode);
    }
}
