package vn.kms.launch.contactmgr.service.validator;

import javax.persistence.Entity;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ExistEntityValidate implements ConstraintValidator<ExistEntity, Entity>{

     public void initialize(ExistEntity annotation) {
     }
     
     public boolean isValid(Entity entity, ConstraintValidatorContext context) {
         // Implements a querry to check enity existed or not
        return true;
     }
}
