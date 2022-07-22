import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterResult {
    private final List<Event> aFilteredEvents;

    public FilterResult(List<Event> pEvents){
        assert pEvents.size() > 0;
        aFilteredEvents = new ArrayList<>(pEvents);
    }

    public List<Event> getaFilteredEvents(){
        return Collections.unmodifiableList(aFilteredEvents);
    }
}
