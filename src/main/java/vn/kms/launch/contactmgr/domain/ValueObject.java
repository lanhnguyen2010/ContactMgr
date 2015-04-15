package vn.kms.launch.contactmgr.domain;

import java.io.Serializable;

public class ValueObject<T> implements Serializable {
    public boolean sameValueAs(T other) {
        return this.equals(other);
    }
}
