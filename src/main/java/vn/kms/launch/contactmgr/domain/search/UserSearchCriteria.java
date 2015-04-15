package vn.kms.launch.contactmgr.domain.search;

public class UserSearchCriteria {

	private String userName;
	private String firstlastName;
	private String email;
	private String role;
	private String assignedCompanies;
	private String createdFrom;
	private String createdTo;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAssignedCompanies() {
		return assignedCompanies;
	}

	public void setAssignedCompanies(String assignedCompanies) {
		this.assignedCompanies = assignedCompanies;
	}

	public String getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	public String getCreatedTo() {
		return createdTo;
	}

	public void setCreatedTo(String createdTo) {
		this.createdTo = createdTo;
	}
	
}
