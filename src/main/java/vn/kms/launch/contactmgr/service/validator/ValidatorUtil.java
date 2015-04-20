package vn.kms.launch.contactmgr.service.validator;

import java.lang.reflect.Field;

public class ValidatorUtil {
    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = object.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(object);
    }
}
