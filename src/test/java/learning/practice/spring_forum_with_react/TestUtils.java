package learning.practice.spring_forum_with_react;

import java.lang.reflect.Field;

public class TestUtils {
    public static Object setFieldValue(Object target, Object... values) throws IllegalAccessException{
        Field[] fields = target.getClass().getDeclaredFields();

        for (int fieldIdx = 0; fieldIdx < values.length; fieldIdx++) {
            Field field = fields[fieldIdx];
            field.setAccessible(true);
            field.set(target, values[fieldIdx]);
        }

        return target;
    }
}
