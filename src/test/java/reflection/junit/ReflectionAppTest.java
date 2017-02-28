package reflection.junit;

import java.util.Objects;

import reflection.junit.BeforeTest;
import reflection.junit.ReflectionApp;
import reflection.junit.TestApp;

public class ReflectionAppTest {

    @BeforeTest
    public void init() {
        System.out.println("preparing test");
    }

    @TestApp
    public void test() {
        System.out.println("running test");
        ReflectionApp reflectionApp = new ReflectionApp(2, "Alex");
        int doubleValue = reflectionApp.getDoubleValue();
        isEqual(4, doubleValue);
    }

    private void isEqual(Object expected, Object actual) {
        if (Objects.equals(expected, actual)) {
            System.out.println("test passed");
        } else {
            throw new AssertionError("test failed");
        }
    }

}