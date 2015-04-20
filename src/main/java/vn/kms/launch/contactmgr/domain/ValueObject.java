package vn.kms.launch.contactmgr.domain;

import java.io.Serializable;

public class ValueObject<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean sameValueAs(T other) {
        return this.equals(other);
    }
}
