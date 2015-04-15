package vn.kms.launch.contactmgr.domain.greeting;

import org.springframework.format.annotation.DateTimeFormat;
import vn.kms.launch.contactmgr.util.SearchCriteria;

import java.util.Date;

public class GreetingSearchCriteria extends SearchCriteria {
    private String code;

    private String message;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationFrom;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date creationTo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationFrom() {
        return creationFrom;
    }

    public void setCreationFrom(Date creationFrom) {
        this.creationFrom = creationFrom;
    }

    public Date getCreationTo() {
        return creationTo;
    }

    public void setCreationTo(Date creationTo) {
        this.creationTo = creationTo;
    }
}
