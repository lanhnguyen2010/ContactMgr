package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Transactional
    public User getUser(int id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public int getIdByEmail(String email) throws ValidationException {
        Query query = em.createQuery("select id from User where email = :email");
        query.setParameter("email", email);
        List<Object> results = query.getResultList();

        if (results.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return (int) results.get(0);
    }

    @Transactional
    public User updatePasswordByEmail(String email, String password) {
        int id = getIdByEmail(email);
        User user = getUser(id);

        user.setPassword(password);
        user.setConfirmPassword(password);

        return saveUser(user, id);
    }

    @Transactional
    public List<String> getRoles() {
        Role[] roles = Role.values();
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < roles.length; i++) {
            result.add(roles[i].name());
        }
        return result;
    }

    @Transactional
    public User saveUser(User user, Integer id) throws ValidationException {
        if (user == null) {
            return null;
        }
        if (id != null && !userRepository.exists(id)) {
            throw new EntityNotFoundException();
        }
        user.setId(id);
        validateUser(user);
        if (id != null && !user.getAssignedCompanies().isEmpty()){
        	userRepository.updateUserAssignedCompanies(id);
        }
        return userRepository.save(user);
    }

    @Transactional
    public int deleteUsers(int... ids) {
        return userRepository.deleteUsers(ids);
    }

    @Transactional
    public Integer activeUser(int... ids) {
        return userRepository.activeUser(ids);
    }

    @Transactional
    public Integer deactiveUser(int... ids) {
        return userRepository.deactiveUser(ids);
    }

    @Transactional
    public SearchResult<User> searchUsers(UserSearchCriteria criteria) {
        return userRepository.searchByCriteria(criteria);
    }

    public void validateUser(User user) throws ValidationException {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
        }
    }

}
