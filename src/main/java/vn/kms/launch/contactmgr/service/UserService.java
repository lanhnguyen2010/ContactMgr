package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.dto.user.ChangePasswordInfo;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.HashString;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.SecurityUtil;
import vn.kms.launch.contactmgr.util.ValidationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public void updatePasswordByEmail(String email, String resetPassword) {

        int tmp = userRepository.updatePasswordByEmail(email, HashString.MD5(resetPassword), true);

        if (tmp == 0) {
            throw new EntityNotFoundException();
        }
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
    public Integer updateLanguage(String language) {
        if (!(language.equalsIgnoreCase("VI") || language.equalsIgnoreCase("EN"))) {
            return null;
        }
        String username = SecurityUtil.getCurrentUsername();
        if (username.equals("") || username.isEmpty()) {
            return null;
        }

        return userRepository.updateLanguage(username, language);
    }

    @Transactional
    public Integer updatePassword(ChangePasswordInfo changePasswordInfo) {
        String email = SecurityUtil.getCurrentEmail();
        if (email.equals("") || email.isEmpty()) {
            return null;
        }
        validateUser(changePasswordInfo);
        return userRepository.updatePasswordByEmail(email, HashString.MD5(changePasswordInfo.getPassword()),false);
    }


    public <T> void validateUser(T t) throws ValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
        }
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User findByUsernameOrEmail(String userName) {
        return userRepository.findByUsernameOrEmail(userName);
    }
}
