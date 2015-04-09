package vn.kms.launch.contactmgr.domain.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {
	@Id
	@Column(name = "ID")
	private int id;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	@Column(name = "LAST_NAME")
	private String lastname;
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PHOTO")
	private String photo;
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "BIRTHDAY")
	private Date birthday;

	@Embedded
	private Home home;

	@Embedded
	private Work work;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	@Embeddable
	public class Work {
		@Column(name = "TITLE")
		private String title;

		@Column(name = "DEPARTMENT")
		private String department;

		@ManyToOne
		@JoinColumn(name = "COMPANY_ID")
		private Company company;

		public Work() {

		}

		public Work(String title, String department, Company company) {
			this.title = title;
			this.department = department;
			this.company = company;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}
	}

	@Embeddable
	public class Home {
		private Address address;

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
}
