import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workshop extends AbstractEvent {
    private final List<Workshop> aPrerequisites;

    public Workshop(String pEventName, LocalDate pLocalDate,
                    double pPrice, int pNumTickets, Location pLocation, List<Workshop> pExistPres){
        super(pEventName,pLocalDate,pPrice,pNumTickets,pLocation);
        aPrerequisites = new ArrayList<>(pExistPres);
    }

    public List<Workshop> getPrerequisites(){
        return Collections.unmodifiableList(aPrerequisites);
    }

    @Override
    public void acceptVisitor(EventVisitor eventVisitor) {
        eventVisitor.visitWorkshop(this);
    }
}
