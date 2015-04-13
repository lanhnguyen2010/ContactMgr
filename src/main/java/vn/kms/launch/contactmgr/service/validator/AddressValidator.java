package vn.kms.launch.contactmgr.service.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import vn.kms.launch.contactmgr.domain.contact.Address;

public class AddressValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
		return Address.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Address address = (Address) obj;
		
		if((address.getPostalCode().toString().length() != 5 )){
			errors.rejectValue("postalCode", "postalCode.invalid", new Object[]{address.getPostalCode()},
					"must be a valid postalCode format: <5 digits>");
		}
	}

}
