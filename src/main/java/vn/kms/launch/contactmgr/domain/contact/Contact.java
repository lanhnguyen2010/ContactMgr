package vn.kms.launch.contactmgr.domain.contact;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CONTACTS")
public class Contact extends vn.kms.launch.contactmgr.domain.Entity {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "LAST_NAME")
    private String lastName;

    @Email(message = "{validation.email.message}")
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHOTO")
    private String photo;

    @Pattern(regexp = "(\\+[0-9]{1,3}\\s)([0-9]{9,10})", message = "{validation.mobile.message}")
    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BIRTHDAY")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Embedded
    @Valid
    private Home home;

    @Embedded
    @Valid
    private Work work;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
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
}
