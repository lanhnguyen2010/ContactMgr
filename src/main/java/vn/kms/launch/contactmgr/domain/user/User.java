package vn.kms.launch.contactmgr.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "USERS")
public class User extends vn.kms.launch.contactmgr.domain.Entity {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{validation.not-empty.message}")
    @Size(max = 16, message = "{validation.maxUserName.message}")
    @Pattern(regexp = "^([A-Za-z0-9]+)$", message = "{validation.UserName.message}")
    @Column(name = "USERNAME")
    private String username;

    @NotBlank(message = "{validation.not-empty.message}")
    @Size(min = 6, message = "{validation.minPassWord.message}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$!%&]).{1,50}$", message = "{validation.PassWord.message}")
    @Column(name="PASSWORD")
    private String password;

    @Size(max = 20, message = "{validation.maxName.message}")
    @Column(name = "FIRST_NAME")
    private String firstname;

    @Size(max = 20, message = "{validation.maxName.message}")
    @Column(name = "LAST_NAME")
    private String lastname;

    @Size(max = 255, message = "{validation.size-255.message}")
//    @Email(message = "{validation.email.message}")
    @Pattern(regexp ="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{validation.email.message}")
    private String email;

    @Column(name = "ROLE")
    private String role;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    //"(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"
//    @Pattern(regexp = "^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])$", message = "{validation.expiredDate.message}")
    @Column(name = "EXPIRED_DATE")
    private Date expiredDate;

    @Column(name = "ACTIVE", columnDefinition = "INT(1)")
    private boolean active;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "ASSIGNED_COMPANIES")
    private String assignedCompanies;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAssignedCompanies() {
        return assignedCompanies;
    }

    public void setAssignedCompanies(String assignedCompanies) {
        this.assignedCompanies = assignedCompanies;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
