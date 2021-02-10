package demo.rt.util;

import java.lang.reflect.Field;

public class ReflectUtil {

    /**
     * 从指定object获取私有属性(使用泛型)
     *
     * @param source
     * @param targetFieldName
     * @param <T>
     */
    public static <T> T getFieldValue(Object source, String targetFieldName) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = source.getClass().getDeclaredField(targetFieldName);
        declaredField.setAccessible(true);
        return (T) declaredField.get(source);
    }

    /**
     * 从指定object获取私有属性(使用泛型)
     *
     * @param source
     * @param targetFieldName
     * @param <T>
     */
    public static <T> T getSuperFieldValue(Object source, String targetFieldName) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = source.getClass().getSuperclass().getDeclaredField(targetFieldName);
        declaredField.setAccessible(true);
        return (T) declaredField.get(source);
    }
}
