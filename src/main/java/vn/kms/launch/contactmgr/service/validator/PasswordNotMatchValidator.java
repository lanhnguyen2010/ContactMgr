package vn.kms.launch.contactmgr.service.validator;

import vn.kms.launch.contactmgr.util.HashString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Created by thanhtuong on 5/4/2015.
 */
public class PasswordNotMatchValidator implements ConstraintValidator<PasswordNotMatch, Object> {
    private String passwordFieldNameOld;
    private String username;

    @Override
    public void initialize(PasswordNotMatch constraintAnnotation) {
        this.passwordFieldNameOld = constraintAnnotation.passwordFieldNameOld();
        this.username = constraintAnnotation.username();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            String passwordOld = (String) ValidatorUtil.getFieldValue(value, passwordFieldNameOld);
            this.username = (String) ValidatorUtil.getFieldValue(value, username);
            if (passwordsAreNotMatch(passwordOld)) {
                ValidatorUtil.addValidationError(passwordFieldNameOld, context);
                return false;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred during validation", ex);
        }
        return true;
    }

    private boolean passwordsAreNotMatch(String password) {
        password = HashString.MD5(password);
        String PasswordByUsername = "";

    }

}
