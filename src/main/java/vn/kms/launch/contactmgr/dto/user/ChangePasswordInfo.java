package vn.kms.launch.contactmgr.dto.user;

/**
 * Created by thanhtuong on 4/23/2015.
 */
public class ChangePasswordInfo {

    private Integer id;
    private String password;
    private String passwordconfirm;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordconfirm() {
        return passwordconfirm;
    }

    public void setPasswordconfirm(String passwordconfirm) {
        this.passwordconfirm = passwordconfirm;
    }
}
