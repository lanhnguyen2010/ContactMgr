package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Work {

    @Column(name = "JOB_TITLE")
    private String title;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "COMPANY_ID", insertable = false, updatable = false)
    private Integer companyId;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
