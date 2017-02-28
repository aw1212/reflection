package reflection.spring;

@Skylander
public class SkylanderFigure {

    private final String name;
    private final int level;

    public SkylanderFigure() {
        name = "Trigger Happy";
        level = 20;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

}

