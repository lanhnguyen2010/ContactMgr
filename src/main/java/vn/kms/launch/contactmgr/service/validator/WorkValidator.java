package vn.kms.launch.contactmgr.service.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.kms.launch.contactmgr.domain.contact.Work;

public class WorkValidator implements Validator{
    
    @Override
    public boolean supports(Class clazz) {
        return Work.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
    }

}
