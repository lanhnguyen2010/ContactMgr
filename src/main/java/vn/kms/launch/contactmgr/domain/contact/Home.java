package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import vn.kms.launch.contactmgr.domain.ValueObject;

@Embeddable
public class Home extends ValueObject<Home> {
    private static final long serialVersionUID = 1L;

    @Embedded
    @Valid
    private Address address;

    @Pattern(regexp = "^(\\+[0-9]{1,3}\\s)([0-9]{9,10})$", message = "{validation.phone.message}")
    @Column(name = "PHONE")
    private String phone;

    @Pattern(regexp = "^(\\+[0-9]{1,3}\\s)([0-9]{9,10})$", message = "{validation.fax.message}")
    @Column(name = "FAX")
    private String fax;

    public Home() {

    }

    public Home(Address address, String phone, String tax) {
        this.address = address;
        this.phone = phone;
        this.fax = tax;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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
}
