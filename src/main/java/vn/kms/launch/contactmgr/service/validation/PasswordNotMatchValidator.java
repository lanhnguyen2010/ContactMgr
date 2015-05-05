package vn.kms.launch.contactmgr.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.util.HashString;
import vn.kms.launch.contactmgr.util.SecurityUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Created by thanhtuong on 5/4/2015.
 */
public class PasswordNotMatchValidator implements ConstraintValidator<PasswordNotMatch, Object> {
    private String passwordFieldNameOld;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(PasswordNotMatch constraintAnnotation) {
        this.passwordFieldNameOld = constraintAnnotation.passwordFieldNameOld();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        try {
            String passwordOld = (String) ValidatorUtil.getFieldValue(value, passwordFieldNameOld);
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
        String username= SecurityUtil.getCurrentUsername();
        String PasswordByUsername = userRepository.getPasswordByUsername(username);
        return (password.equals(PasswordByUsername));

    }

}
