package vn.kms.launch.contactmgr.dto.user;

import org.hibernate.validator.constraints.NotBlank;
import vn.kms.launch.contactmgr.service.validator.PasswordsNotEqual;

import javax.validation.constraints.Pattern;

/**
 * Created by thanhtuong on 4/23/2015.
 */
@PasswordsNotEqual(
    passwordFieldName = "password",
    passwordVerificationFieldName = "passwordConfirm",
    message = "{validation.ConfirmPassWord.message}"
)
public class ChangePasswordInfo {

    private String oldPassword;

    @NotBlank(message = "{validation.PassWord.message}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*]).{6,20}$",
        message = "{validation.PassWord.message}")
    private String password;

    private String passwordConfirm;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
