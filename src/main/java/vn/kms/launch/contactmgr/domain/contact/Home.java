package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
public class Home {
	private Address address;

	@Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", message = "must be a valid phone format: +<1 to 3 digits> <9 to 10 digits>")
	@Column(name = "PHONE")
	private String phone;

	@Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", message = "must be a valid fax format: +<1 to 3 digits> <9 to 10 digits>")
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
