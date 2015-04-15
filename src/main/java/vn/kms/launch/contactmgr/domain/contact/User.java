package vn.kms.launch.contactmgr.domain.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User extends vn.kms.launch.contactmgr.domain.Entity {
	private static final long serialVersionUID = 1L;
	
	@Column(name="USERNAME")
	private String userName;
	
	@Column(name="PASSWORD")
	private String passWord;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;
	
	@Column(name="ACTIVE")
	private int active;
	
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Column(name="ASSIGNEDCOMPANIES")
	private String assignedcompanies;
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public String getAssignedcompanies() {
		return assignedcompanies;
	}

	public void setAssignedcompanies(String assignedcompanies) {
		this.assignedcompanies = assignedcompanies;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public void setActive(int active) {
		this.active = active;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Role getRole() {
		return role;
	}
	public int getActive() {
		return active;
	}
	
	
}
