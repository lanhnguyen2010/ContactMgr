package vn.kms.launch.contactmgr.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.contact.User;
import vn.kms.launch.contactmgr.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Transactional
	public User getUser(int id){
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User saveUser(@Valid User user){
		if(null == user) {
			return null;
		}
		return userRepository.save(user);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRoles(){
		Query query = em.createQuery("select role from User");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getLanguages(){
		Query query =em.createQuery("select language from User");
		return query.getResultList();
	}

}
