package vn.kms.launch.contactmgr.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import vn.kms.launch.contactmgr.domain.contact.User;
import vn.kms.launch.contactmgr.service.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(method = POST)
	public ResponseEntity<User> createUser(@RequestBody @Valid User user){
		User userCreate=userService.saveUser(user);
		if(null == userCreate){
			return new ResponseEntity<User>(userCreate,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(userCreate, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = PUT)
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User user){
		user.setId(id);
		User userUpdate = userService.saveUser(user);
		if(null == userUpdate){
			return new ResponseEntity<User>(userUpdate,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(userUpdate, HttpStatus.OK);
	}
}
