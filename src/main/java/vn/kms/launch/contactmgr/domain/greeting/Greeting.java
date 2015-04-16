package vn.kms.launch.contactmgr.domain.greeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "GREETINGS")
public class Greeting extends vn.kms.launch.contactmgr.domain.Entity {
    @NotEmpty(message = "{validation.not-empty.message}")
    @Pattern(regexp = "(\\[A-Za-z]{2})", message = "{validation.iso-3166-code.message}")
    @Column(name = "CODE", unique = true)
    private String code;

    @Transient
    private String language;

    @NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "MESSAGE")
    private String message;

    protected Greeting() {

    }

    public Greeting(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
