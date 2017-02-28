package reflection.spring;

@Runner
@Skylander
public class SkylanderApp implements RunnableApp {

    @Inject
    private PortalOfPower portalOfPower;

    @Override
    public void run() {
        portalOfPower.summonSkylander();
    }

    public void setPortalOfPower(PortalOfPower portalOfPower) {
        System.out.println("setting portalOfPower dependency");
        this.portalOfPower = portalOfPower;
    }
}
