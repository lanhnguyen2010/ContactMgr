package vn.kms.launch.contactmgr.service.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.kms.launch.contactmgr.domain.contact.Contact;

public class ContactValidator implements Validator{
	
	private HomeValidator homeValidator;
	private WorkValidator workValidator;
	
	@Override
	public boolean supports(Class clazz) {
		return Contact.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Email.empty");
		
		Contact contact = (Contact) obj;
//		
//		if((contact.getMobile() != null) && (contact.getMobile().isEmpty())) {
//			errors.rejectValue("mobile", "mobile.invalid", new Object[]{contact.getMobile()},
//					"{validation.mobile.message}");
//		}
		
		try {
			errors.pushNestedPath("home");
	        ValidationUtils.invokeValidator(this.homeValidator, contact.getHome(), errors);
		} catch (Exception e) {
			errors.popNestedPath();
		}
		
		try {
			errors.pushNestedPath("work");
	        ValidationUtils.invokeValidator(this.workValidator, contact.getWork(), errors);
		} catch (Exception e) {
			errors.popNestedPath();
		}
		
	}

	/**
     * Set homeValidator
     * @param HomeValidator the homeValidator to set
     */
    public void setWorkValidator(HomeValidator homeValidator) {
        this.homeValidator = homeValidator;
    }
	
	/**
     * Set workValidator
     * @param WorkValidator the workValidator to set
     */
    public void setWorkValidator(WorkValidator workValidator) {
        this.workValidator = workValidator;
    }
	
}
