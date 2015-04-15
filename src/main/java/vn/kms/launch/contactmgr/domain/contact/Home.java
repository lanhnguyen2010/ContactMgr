package vn.kms.launch.contactmgr.domain.contact;

import vn.kms.launch.contactmgr.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Embeddable
public class Home extends ValueObject<Home> {
    @Embedded
    @Valid
    private Address address;

    @Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", message = "{validation.phone.message}")
    @Column(name = "PHONE")
    private String phone;

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
