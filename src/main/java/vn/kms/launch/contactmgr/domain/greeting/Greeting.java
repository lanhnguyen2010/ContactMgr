package vn.kms.launch.contactmgr.domain.greeting;

import org.hibernate.validator.constraints.NotEmpty;
import vn.kms.launch.contactmgr.domain.Entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by trungnguyen on 4/8/15.
 */
@javax.persistence.Entity
@Table(name = "GREETINGS")
public class Greeting extends Entity {
    @NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "CODE", unique = true)
    private String code;

    @Transient
    private String language;

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
