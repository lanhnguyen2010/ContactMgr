package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "COMPANIES")
public class Company extends vn.kms.launch.contactmgr.domain.Entity {
    private static final long serialVersionUID = 1L;

<<<<<<< HEAD
	@Column(name = "NAME")
	private String name;
=======
    @Column(name = "NAME")
    private String name;

    @Column(name = "WEBSITE")
    private String website;
>>>>>>> f16a9b4c65466765549031ca657954298986461f

    @Column(name = "LOGO")
    private String logo;

    @Column(name = "MESSAGE")
    private String message;

    @Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", message = "{validation.phone.message}")
    @Column(name = "PHONE")
    private String phone;

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

    public String getName() {
        return name;
    }

<<<<<<< HEAD

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
=======
    public void setName(String name) {
        this.name = name;
    }
>>>>>>> f16a9b4c65466765549031ca657954298986461f
}
