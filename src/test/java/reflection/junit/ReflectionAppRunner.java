package reflection.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionAppRunner {

    private static Class clazz = ReflectionAppTest.class;

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object object = clazz.newInstance();
        final Set<Method> methodsToRun = new HashSet<>();
        Method initMethod = null;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeTest.class)) {
                initMethod = method;
            } else if (method.isAnnotationPresent(TestApp.class)) {
                methodsToRun.add(method);
            }
        }
        for (Method method : methodsToRun) {
            if (initMethod != null) {
                initMethod.invoke(object);
            }
            method.invoke(object);
        }
    }
}
