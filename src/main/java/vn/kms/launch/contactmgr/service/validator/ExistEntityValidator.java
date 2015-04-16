package vn.kms.launch.contactmgr.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistEntityValidator implements ConstraintValidator<ExistEntity, String> {

    public void initialize(ExistEntity annotation) {
    }

    /**
     * Check special characters in First Name and Last Name of user
     *
     * @param value is first name and last name of user
     * @param context
     * @return false if value has contained special characters
     */
    public boolean isValid(String value, ConstraintValidatorContext context) {
    
        //Implement a querry to check companyid (integer or Array<Integer>)
    
        return false;
    }
}
