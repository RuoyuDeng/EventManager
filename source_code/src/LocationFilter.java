import java.util.ArrayList;
import java.util.List;

public class LocationFilter implements FilterStrategy{
    private Location aLocation;

    public LocationFilter(Location pLocation){
        assert pLocation != null;
        aLocation = pLocation;
    }

    @Override
    public List<Event> filter(List<Event> pEvents) {
        assert pEvents != null;
        List<Event> result = new ArrayList<>();
        for(Event e: pEvents){
            if(e.getLocations().isPresent()){
                List<Location> locations = e.getLocations().get();
                if(locations.contains(aLocation)) result.add(e);
            }
        }
        return result;
    }
}
