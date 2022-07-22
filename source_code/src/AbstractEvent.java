import java.time.LocalDate;
import java.util.*;

public abstract class AbstractEvent implements Event {
    private final String aEventName;
    private final LocalDate aLocalDate;
    private final Optional<Double> aPrice;
    private final Optional<Integer> aNumTickets;
    private final Optional<List<Location>> aLocations;


    /**
     * Constructor of Normal events besides EventManager.ComingSoon event and EventManager.Festival
     * @param pEventName
     * @param pLocalDate
     * @param pPrice
     * @param pNumTickets
     * @param pLocation
     */
    public AbstractEvent(String pEventName, LocalDate pLocalDate,double pPrice, int pNumTickets, Location pLocation){
        assert pEventName != null && pLocalDate != null && pPrice > 0 && pNumTickets > 0 && pLocation != null;
        aEventName = pEventName;
        aLocalDate = pLocalDate;
        aPrice = Optional.of(pPrice);
        aNumTickets = Optional.of(pNumTickets);
        List<Location> newList = new ArrayList<>();
        newList.add(pLocation);
        aLocations = Optional.of(newList);
    }

    /**
     * Constructor for EventManager.ComingSoon events
     * @param pEventName
     * @param pLocalDate
     */
    public AbstractEvent(String pEventName, LocalDate pLocalDate){
        assert pEventName != null && pLocalDate != null;
        aEventName = pEventName;
        aLocalDate = pLocalDate;
        aPrice = Optional.empty();
        aNumTickets = Optional.empty();
        aLocations = Optional.empty();
    }

    /**
     * Constructor for EventManager.Festival Events
     * @param pEventName
     * @param pEvents
     */
    public AbstractEvent(String pEventName, List<Event> pEvents){
        assert pEventName != null && pEvents.size() > 0;
        List<Event> sortedEvents = new ArrayList<>(pEvents);
        Collections.sort(sortedEvents);
        double totalPrice = 0;
        int minNumTickets = Integer.MAX_VALUE;
        List<Location> locations = new ArrayList<>();
        for (Event e: sortedEvents){
            if(e.getPrice().isPresent()){
                totalPrice += e.getPrice().get();
            }
            if(e.getNumTickets().isPresent()){
                int curTickets = e.getNumTickets().get();
                minNumTickets = Integer.min(curTickets,minNumTickets);
            }
            Optional<List<Location>> optList = e.getLocations();
            if(optList.isPresent()){
                List<Location> tempList = optList.get();
                for(Location loc : tempList){
                    if(!locations.contains(loc)){
                        locations.add(loc); // if the location does not exist in the locations, add it to list
                    }
                }
            }
        }

        aEventName = pEventName;
        aLocalDate = sortedEvents.get(0).getDate();
        aPrice = Optional.of(totalPrice/5);
        aNumTickets = Optional.of(minNumTickets);
        aLocations = Optional.of(locations);
    }


    public String getName(){
        return aEventName;
    }

    public LocalDate getDate(){
        return aLocalDate;
    }

    public Optional<List<Location>> getLocations(){
        return aLocations;
    }

    public Optional<Double>  getPrice(){
        return aPrice;
    }

    public Optional<Integer> getNumTickets(){
        return aNumTickets;
    }

    @Override
    /**
     *
     * @param other
     * @return -1 if the event has smaller date, compare based on date
     */
    public int compareTo(Event other){
        return aLocalDate.compareTo(other.getDate());
    }

    @Override
    public abstract void acceptVisitor(EventVisitor eventVisitor);

}
