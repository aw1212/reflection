package reflection.spring;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class SkylanderAppRunner {

    private static Set<Class> allSkylanders = new HashSet<>();
    private static Class<? extends RunnableApp> runner;
    private static Map<String, Object> skylanders = new HashMap<>();

    public static void main(String[] args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = getReflections();
        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
        setInitialClassStructures(allClasses);

        while (skylanders.keySet().size() < allSkylanders.size()) {
            outer:
            for (Class c : allSkylanders) {
                if (skylanders.get(c.getName()) != null) {
                    continue;
                }

                Set<Class> classesForInjection = new HashSet<>();
                for (Field f : c.getDeclaredFields()) {
                    if (f.isAnnotationPresent(Inject.class)) {
                        classesForInjection.add(f.getType());
                    }
                }

                if (classesForInjection.isEmpty()) {
                    skylanders.put(c.getName(), c.getConstructor().newInstance());
                    continue;
                }

                List<Object> dependencies = new ArrayList<>();

                for (Class ffi : classesForInjection) {
                    if (skylanders.get(ffi.getName()) == null) {
                        continue outer;
                    }
                    dependencies.add(skylanders.get(ffi.getName()));
                }

                Object newClass = c.getConstructor().newInstance();

                for (Object d : dependencies) {
                    String setterName = "set" + d.getClass().getSimpleName();
                    Method m = c.getDeclaredMethod(setterName, d.getClass());
                    m.invoke(newClass, d);
                }
                skylanders.put(c.getName(), newClass);
            }
        }

        Object runObject = skylanders.get(runner.getName());
        Method run = runner.getDeclaredMethod("run");
        run.invoke(runObject);
    }

    private static Reflections getReflections() {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        return new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("reflection.spring"))));
    }

    private static void setInitialClassStructures(Set<Class<?>> allClasses) {
        for (Class clazz : allClasses) {
            if (clazz.isAnnotationPresent(Runner.class)) {
                runner = clazz;
            }
            if (clazz.isAnnotationPresent(Skylander.class)) {
                allSkylanders.add(clazz);
            }
        }
    }

}
