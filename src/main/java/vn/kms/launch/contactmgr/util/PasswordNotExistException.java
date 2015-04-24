package vn.kms.launch.contactmgr.util;

/**
 * Created by thanhtuong on 4/24/2015.
 */
public class PasswordNotExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PasswordNotExistException(String message) {
        super(message);
    }
}
