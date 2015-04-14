package vn.kms.launch.contactmgr.web.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import vn.kms.launch.contactmgr.domain.contact.User;
import vn.kms.launch.contactmgr.domain.search.UserSearchCriteria;
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
	
    @RequestMapping(value="/search", method = POST)
    public HashMap<String, Object> searchUser(@RequestParam ("page") int page,
                                              @RequestParam (value="pageSize", defaultValue="10") int pageSize,
                                              @RequestBody UserSearchCriteria criteria) throws JSONException {
           return userService.searchUsers(criteria, page, pageSize);
    }
    
	/**
	 * Return 404(Not Found)  code if not contact associated to ID is not found
	 * Return 204(No Content)  code if deleted successfully
	 */
	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		
		int deleteId = userService.deleteUsers(id);
		//receive  id with method deleteContact() from UI

		if (deleteId == 0) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Return 400(Bad Request)  code if ids param is null
	 * Return 404(Not Found)  code if not contact associated to ID is not found
	 * Return 200(OK) code and the actual number of Contact that deleted
	 */
	@RequestMapping(method = DELETE)
	public ResponseEntity<Integer> deleteUsers(@RequestParam int... ids) {

		if(ids.length == 0){
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		
		int deleteId = userService.deleteUsers(ids);
		if (deleteId == 0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Integer>(deleteId,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/setactive/{id}", method = PUT)
	public ResponseEntity<User> activeUser(@PathVariable int id){
		User userUpdate = userService.activeUser(id);
		if(null == userUpdate){
			return new ResponseEntity<User>(userUpdate,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(userUpdate, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/setdeacctive/{id}", method = PUT)
	public ResponseEntity<User> deactiveUser(@PathVariable int id){
		User userUpdate = userService.deactiveUser(id);
		if(null == userUpdate){
			return new ResponseEntity<User>(userUpdate,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(userUpdate, HttpStatus.OK);
	}
	
	
}
