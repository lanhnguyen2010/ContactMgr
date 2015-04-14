package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Company")
public class Company extends vn.kms.launch.contactmgr.domain.Entity{
	private static final long serialVersionUID = 1L;

	@Column(name = "CODE")
	private String code;

	@Column(name = "NAME")
	private String name;

	@Column(name = "WEBSITE")
	private String website;

	@Column(name = "LOGO")
	private String logo;

	@Column(name = "MESSAGE")
	private String message;
	
	@Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", 
			message = "must be a valid phone format: +<1 to 3 digits> <9 to 10 digits>")
	@Column(name = "PHONE")
	private String phone;

	@Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", 
			message = "must be a valid fax format: +<1 to 3 digits> <9 to 10 digits>")
	@Column(name = "FAX")
	private String fax;

	@Embedded
	private Address address;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
