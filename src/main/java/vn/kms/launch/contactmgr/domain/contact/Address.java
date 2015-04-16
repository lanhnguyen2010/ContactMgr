package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import vn.kms.launch.contactmgr.domain.ValueObject;

@Embeddable
public class Address extends ValueObject<Address> {
	private static final long serialVersionUID = 1L;

	@Column(name = "ADDR_LINE1")
    private String addrLine1;

    @Column(name = "ADDR_LINE2")
    private String addrLine2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Min(value = 10000, message = "{validation.postalCode.message}")
    @Max(value = 99999, message = "{validation.postalCode.message}")
    @Column(name = "POSTAL_CODE")
    private Integer postalCode;

    @Pattern(regexp = "^([A-Z]{2})$", message = "{validation.country.message}")
    @Column(name = "COUNTRY")
    private String country;

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
