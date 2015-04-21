package vn.kms.launch.contactmgr.util;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Map<String, Object> errors;

    public ValidationException(ConstraintViolation<?>... violations) {
        errors = new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {
            String path = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            addError(errors, path, message);
        }

    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    private void addError(Map<String, Object> errors, String path, String message) {
        int pos = path.indexOf('.');
        if (pos < 0) {
            errors.put(path, message);
            return;
        }

        String parent = path.substring(0, pos);
        Map<String, Object> subErrors = (Map<String, Object>) errors.get(parent);
        if (subErrors == null) {
            subErrors = new HashMap<>();
            errors.put(parent, subErrors);
        }

        path = path.substring(pos + 1);
        addError(subErrors, path, message);
    }
}
