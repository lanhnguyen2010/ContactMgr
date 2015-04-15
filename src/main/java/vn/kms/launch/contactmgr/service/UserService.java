package vn.kms.launch.contactmgr.service;

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
	

}
