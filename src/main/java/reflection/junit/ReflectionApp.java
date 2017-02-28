package reflection.junit;

public class ReflectionApp {

    private int value;
    private String name;

    public ReflectionApp(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getDoubleValue() {
        return value * 2;
    }

}
