package vn.kms.launch.contactmgr.service.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.kms.launch.contactmgr.domain.contact.Home;

public class HomeValidator implements Validator{
	
	private AddressValidator addressValidator;
	
	@Override
	public boolean supports(Class clazz) {
		return Home.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Home home = (Home) obj;
		
		try {
			errors.pushNestedPath("address");
	        ValidationUtils.invokeValidator(this.addressValidator, home.getAddress(), errors);
		} catch (Exception e) {
			errors.popNestedPath();
		}
	}

	/**
     * Set addressValidator
     * @param addressValidator the addressValidator to set
     */
    public void setAddressValidator(AddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }
    
}
