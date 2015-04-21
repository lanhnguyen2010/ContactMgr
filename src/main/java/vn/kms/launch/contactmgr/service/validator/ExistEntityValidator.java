package vn.kms.launch.contactmgr.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistEntityValidator implements ConstraintValidator<ExistEntity, String> {

    public void initialize(ExistEntity annotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

        //Implement a querry to check companyid (integer or Array<Integer>)

        return false;
    }
}
