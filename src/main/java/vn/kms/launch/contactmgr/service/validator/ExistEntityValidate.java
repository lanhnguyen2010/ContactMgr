package vn.kms.launch.contactmgr.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ExistEntityValidate implements ConstraintValidator<ExistEntity, Integer>{
    
    private Class<?> entity;
    
    public void initialize(ExistEntity annotation) {
        this.entity = annotation.type();
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {

    	if((id == null) || (id < 1)){
            System.out.println("1: " + entity.hashCode());
            return false;
        }
        return false;
    }
}
