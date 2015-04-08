package vn.kms.launch.contactmgr.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class Company {
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "CODE", columnDefinition = "char(10)")
	private String code;
	@Column(name = "NAME")
	private String name;
	@Lob
	@Column(name = "WEBSITE", columnDefinition = "TEXT")
	private String website;
	@Lob
	@Column(name = "LOGO")
	private String logo;
	@Lob
	@Column(name = "MESSAGE")
	private String message;

	@Column(name = "PHONE", columnDefinition = "char(10)")
	private String phone;
	@Column(name = "FAX",columnDefinition="char(20)")
	private String fax;
	
	@Embedded private Address address;

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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
