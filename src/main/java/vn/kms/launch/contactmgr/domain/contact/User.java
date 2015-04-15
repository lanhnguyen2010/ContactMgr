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
	
	@Column(name="EMAIL")
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name="EXPIRED_DATE")
	private Date expiredDate;
	
	@Column(name="ACTIVE")
	private int active;
	
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Column(name="ASSIGNED_COMPANIES")
	private String assigned_companies;
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public String getAssigned_companies() {
		return assigned_companies;
	}

	public void setAssigned_companies(String assigned_companies) {
		this.assigned_companies = assigned_companies;
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
	
<<<<<<< HEAD
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
=======
	
>>>>>>> e47a6455e5eab652629676d6ef6e63a96fcac109
}
