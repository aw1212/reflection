package reflection.spring;

@Skylander
public class PortalOfPower {

    @Inject
    private SkylanderFigure skylanderFigure;

    public void summonSkylander() {
        System.out.println("I have activated: " + skylanderFigure.getName());
    }

    public void setSkylanderFigure(SkylanderFigure skylanderFigure) {
        System.out.println("setting skylanderFigure dependency");
        this.skylanderFigure = skylanderFigure;
    }
}
