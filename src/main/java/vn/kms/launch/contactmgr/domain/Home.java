package vn.kms.launch.contactmgr.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Home {
	private Address address;
	
	@Column(name = "PHONE", columnDefinition="char(15)")
	private String phone;
	
	@Column(name = "FAX", columnDefinition="char(20)")
	private String fax;
	
	public Home(){
		
	}
	
	public Home(Address address, String phone, String tax){
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
