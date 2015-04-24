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
import vn.kms.launch.contactmgr.util.HashString;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public int updatePasswordByEmail(String email, String resetPassword) {
        int id = getIdByEmail(email);
        return userRepository.updateResetPassword(id, HashString.MD5(resetPassword));
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
        user.setPassword(HashString.MD5(user.getPassword()));
        user.setConfirmPassword(HashString.MD5(user.getConfirmPassword()));
        user.setResetPassword(HashString.MD5(user.getConfirmPassword()));

        if (id != null && !user.getAssignedCompanies().isEmpty()) {
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

    @Transactional
    public int updateLanguage(int id, String language) {
        if (id == 0) {
            return 0;
        }
        if (id != 0 && !userRepository.exists(id)) {
            throw new EntityNotFoundException();
        }
        return userRepository.updateLanguage(id, language);
    }

    @Transactional
    public int updatePassword(int id, String password, String passwordConfirm) {
        if (id == 0 || !password.equals(passwordConfirm)) {
            return 0;
        }
        if (id != 0 && !userRepository.exists(id)) {
            throw new EntityNotFoundException();
        }
        System.out.println("ID::::::::::::::::"+id);
        User user = userRepository.findOne(id);
        user.setPassword(password);
        user.setConfirmPassword(passwordConfirm);
        validateUser(user);
        return userRepository.updatePassword(id, HashString.MD5(password), passwordConfirm);
    }

    public void validateUser(User user) throws ValidationException {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
        }
    }

}
