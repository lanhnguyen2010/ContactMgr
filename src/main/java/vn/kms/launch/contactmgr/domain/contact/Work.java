package vn.kms.launch.contactmgr.domain.contact;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import vn.kms.launch.contactmgr.domain.ValueObject;
import vn.kms.launch.contactmgr.service.validator.ExistEntity;

@Embeddable
public class Work extends ValueObject<Work> {
    private static final long serialVersionUID = 1L;

    @Size(max = 50, message = "{validation.size-50.message}")
    @Column(name = "JOB_TITLE")
    private String title;

    @Size(max = 100, message = "{validation.size-100.message}")
    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "COMPANY_ID")
    @ExistEntity(type=Company.class)
    private Integer companyId;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID", insertable = false, updatable = false)
    @Valid
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
