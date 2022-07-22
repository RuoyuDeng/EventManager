import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Concert extends AbstractEvent {
    private String aArtist;
    private final List<VIP> aVIPs = new ArrayList<>();

    public Concert(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation, String pArtist,List<VIP> pVIPs){
        super(pEventName,pLocalDate,pPrice,pNumTickets,pLocation);
        assert pArtist != null;
        aArtist = pArtist;
        for (VIP v: pVIPs){
            aVIPs.add(new VIP(v.getaName()));
        }
    }

    public String getArtist(){
        return aArtist;
    }

    public void setArtist(String pArtist){
        assert pArtist != null;
        aArtist = pArtist;
    }

    public void addVIP(VIP pVIP){
        assert pVIP != null;
        aVIPs.add(pVIP);
    }

    public List<VIP> getaVIPs(){
        return Collections.unmodifiableList(aVIPs);
    }

    public void acceptVisitor(EventVisitor eventVisitor){
        assert eventVisitor != null;
        eventVisitor.visitConcert(this);
    }

}
