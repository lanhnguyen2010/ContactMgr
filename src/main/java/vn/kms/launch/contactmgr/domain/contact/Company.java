package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "COMPANIES")
public class Company extends vn.kms.launch.contactmgr.domain.Entity {
    private static final long serialVersionUID = 1L;

    @Size(max = 100, message = "{validation.size-100.message}")
    @NotBlank(message = "{validation.not-empty.message}")
    @Column(name = "NAME")
    private String name;

    @Size(max = 255, message = "{validation.size-255.message}")
    @Column(name = "WEBSITE")
    private String website;

    @Size(max = 255, message = "{validation.size-255.message}")
    @Column(name = "LOGO")
    private String logo;

    @Size(max = 255, message = "{validation.size-255.message}")
    @Column(name = "MESSAGE")
    private String message;

    @Pattern(regexp = "^(\\+[0-9]{1,3}\\s)([0-9]{9,10})$", message = "{validation.phone.message}")
    @Column(name = "PHONE")
    private String phone;

    @Pattern(regexp = "^(\\+[0-9]{1,3}\\s)([0-9]{9,10})$", message = "{validation.fax.message}")
    @Column(name = "FAX")
    private String fax;

    @Embedded
    @Valid
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

    public void setName(String name) {
        this.name = name;
    }
}
