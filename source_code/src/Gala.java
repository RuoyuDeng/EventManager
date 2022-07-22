import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gala extends AbstractEvent{
    private final List<VIP> aVIPs = new ArrayList<>();

    public Gala(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation, List<VIP> pVIPs){
        super(pEventName,pLocalDate,pPrice,pNumTickets,pLocation);
        for (VIP v: pVIPs){
            aVIPs.add(new VIP(v.getaName()));
        }
    }

    @Override
    public void acceptVisitor(EventVisitor eventVisitor) {
        eventVisitor.visitGala(this);
    }

    public void addVIP(VIP pVIP){
        assert pVIP != null;
        aVIPs.add(pVIP);
    }

    public List<VIP> getaVIPs(){
        return Collections.unmodifiableList(aVIPs);
    }

}
