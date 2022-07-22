import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Festival extends AbstractEvent{
    private final List<Event> aEvents;


    public Festival(String pEventName, List<Event> pEvents){
        super(pEventName,pEvents);
        aEvents = new ArrayList<>(pEvents);
    }

    @Override
    public void acceptVisitor(EventVisitor eventVisitor) {
        eventVisitor.visitFestival(this);
    }

    public List<Event> getEvents(){
        return Collections.unmodifiableList(aEvents);
    }

}
