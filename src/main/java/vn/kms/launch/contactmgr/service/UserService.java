package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

@Service
@Transactional(readOnly=true)
public class UserService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public User getUser(int id){
		return userRepository.findOne(id);
	}
	
	@Transactional
	public List<String> getRoles(){
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
	public SearchResult<User> searchUsers( UserSearchCriteria criteria){
		return userRepository.searchByCriteria(criteria);
	}


}
