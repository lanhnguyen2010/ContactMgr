package vn.kms.launch.contactmgr.dto.user;

import org.hibernate.validator.constraints.NotBlank;
import vn.kms.launch.contactmgr.service.validation.PasswordNotMatch;
import vn.kms.launch.contactmgr.service.validation.PasswordsNotEqual;

import javax.validation.constraints.Pattern;

import vn.kms.launch.contactmgr.util.SecurityUtil;
import  vn.kms.launch.contactmgr.util.SecurityUtil.*;

/**
 * Created by thanhtuong on 4/23/2015.
 */
@PasswordsNotEqual(
    passwordFieldName = "password",
    passwordVerificationFieldName = "passwordConfirm",
    message = "{validation.ConfirmPassWord.message}"
)
@PasswordNotMatch(
    passwordFieldNameOld = "oldPassword",
    message = "{validation.PasswordMatch.message}"
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

    public ChangePasswordInfo(String oldPassword, String password, String passwordConfirm) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
