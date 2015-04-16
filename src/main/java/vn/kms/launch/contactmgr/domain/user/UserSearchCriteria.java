package vn.kms.launch.contactmgr.domain.user;

import java.util.Date;
import java.util.List;

import vn.kms.launch.contactmgr.util.SearchCriteria;

public class UserSearchCriteria extends SearchCriteria {

	private String username;
	private String firstlastName;
	private String email;
	private String role;
	private String assignedCompanies;
	private Date createdFrom;
	private Date createdTo;
	
	public String getFirstlastName() {
		return firstlastName;
	}

	public void setFirstlastName(String firstlastName) {
		this.firstlastName = firstlastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAssignedCompanies() {
		return assignedCompanies;
	}

	public void setAssignedCompanies(String assignedCompanies) {
		this.assignedCompanies = assignedCompanies;
	}

	public Date getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(Date createdFrom) {
		this.createdFrom = createdFrom;
	}

	public Date getCreatedTo() {
		return createdTo;
	}

	public void setCreatedTo(Date createdTo) {
		this.createdTo = createdTo;
	}
}
