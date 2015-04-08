package vn.kms.launch.contactmgr.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Work {
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DEPARTMENT")
	private String department;
	
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;
	
	public Work(){
		
	}
	
	public Work(String title, String department, Company company){
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
