package vn.kms.launch.contactmgr.domain.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "USER")
public class User extends vn.kms.launch.contactmgr.domain.Entity {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "{validation.not-empty.message}")
	@Size(max = 16, message = "{validation.maxUserName.message}")
	@Pattern(regexp = "^([0-9]+[A-Z])$)", message = "{validation.UserName.message}")
	@Column(name="USERNAME")
	private String userName;
	
	@NotEmpty(message = "{validation.not-empty.message}")
	@Size(min = 6, message = "{validation.minPassWord.message}")
	@Pattern(regexp = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]))$", message = "{validation.PassWord.message}")
	@Column(name="PASSWORD")
	private String passWord;
	
	@Size(max = 20, message = "{validation.maxName.message}")
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Size(max = 20, message = "{validation.maxName.message}")
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
