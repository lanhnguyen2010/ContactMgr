package vn.kms.launch.contactmgr.service.validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ExistEntityValidate implements ConstraintValidator<ExistEntity, Integer>{
    
    private Class<?> entity;
    
    @PersistenceContext
    private EntityManager em;
    
    public void initialize(ExistEntity annotation) {
        this.entity = annotation.type();
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        String entityName = entity.getSimpleName();
        if(em != null){
            Query q = em.createQuery("select id from " + entityName + " where id=" + id);
            return q.getResultList().size() > 0;
        }
        return false;
    }
}
