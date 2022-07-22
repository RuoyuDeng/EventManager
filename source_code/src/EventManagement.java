import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
Controller to manage events hosted on EventBrite.
 */
public class EventManagement {
    private final List<Event> aHostedEvents;
    private final List<FilterStrategy> aFilterStrategies;
    private final EventFactory aEventFactory;


    public EventManagement(){
        aEventFactory = new EventFactory();
        aFilterStrategies = new ArrayList<>();
        aHostedEvents = new ArrayList<>();
    }

    /*
    Method to host a new concert event
     */
    public void addConcertEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation, String pArtist, List<VIP> pVIPs){
        Event e = aEventFactory.createConcertEvent(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pArtist,pVIPs);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);
    }


    /*
    Method to host a new EventManager.Gala event
     */
    public void addGalaEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,List<VIP> pVIPs){
        Event e = aEventFactory.createGalaEvent(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pVIPs);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);

    }

    /*
    Method to host a new EventManager.Workshop event
     */
    public void addWorkshopEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,List<Workshop> pExistPres){
        Event e = aEventFactory.createWorkshopEvent(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pExistPres);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);
    }


    /*
    Method to host a new EventManager.Screening event
     */
    public void addScreeningEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,Rating pRate){
        Event e = aEventFactory.createScreeningEvent(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pRate);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);
    }

    /*
    Method to host a new EventManager.Festival event
     */
    public void addFestivalEvent(String pEventName, List<Event> pEvents){
        Event e = aEventFactory.createFestivalEvent(pEventName,pEvents);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);
    }

    /*
    Method to host a new EventManager.ComingSoon event
     */
    public void addComingSoonEvent(String pEventName, LocalDate pLocalDate){
        Event e = aEventFactory.createComingSoon(pEventName,pLocalDate);
        if(e != null && !aHostedEvents.contains(e)) aHostedEvents.add(e);
    }

    /*
    Add existing event to system
     */
    public void addEvent(Event event){
        assert event != null;
        if(!aHostedEvents.contains(event)) aHostedEvents.add(event);
    }


    /*
    Return the list of hosted events on EventBrite.
    This method assumes that Events are immutable.
     */
    public ArrayList<Event> getHostedEvents(){
        return new ArrayList<Event>(aHostedEvents);
    }

    /**
     * Input a list of strategies to filter all the events stored in EventManager.Event Management
     * @param pStrategies
     * @return events that satisfy the filter conditions
     */
    public FilterResult applyFilter(List<FilterStrategy> pStrategies){
        assert pStrategies != null;
        List<Event> allEvents = new ArrayList<>(aHostedEvents);
        // did not apply any filter, return all events to filter result
        if(pStrategies.size() == 0) {
            return new FilterResult(allEvents);
        }
        for (FilterStrategy pStrategy : pStrategies) {
            if(!aFilterStrategies.contains(pStrategy)){
                this.addStrategy(pStrategy);
                System.out.println("New EventManager.FilterStrategy recorded in system");
            }
            allEvents = pStrategy.filter(allEvents);
        }
        return new FilterResult(allEvents);
    }

    private void addStrategy(FilterStrategy pStrategy){
        assert pStrategy != null;
        aFilterStrategies.add(pStrategy);
    }

    public double computeProfit(FilterResult targetEvents, double concertRatio, double galaRatio, double workshopRatio, double screeningRatio){
        assert targetEvents != null;
        List<Event> events = targetEvents.getaFilteredEvents();
        ProfitEventVisitor profitVisitor = new ProfitEventVisitor(concertRatio,galaRatio,workshopRatio,screeningRatio);
        for(Event e: events){
            e.acceptVisitor(profitVisitor);
        }
        return profitVisitor.getTotalProfit();
    }

    public int countVIP(FilterResult targetEvents){
        assert targetEvents != null;
        List<Event> events = targetEvents.getaFilteredEvents();
        CountVIPVisitor vipVisitor = new CountVIPVisitor();
        for(Event e: events){
            e.acceptVisitor(vipVisitor);
        }
        return vipVisitor.getVIPcount();
    }

    public EventFactory getEventFactory(){
        return aEventFactory;
    }

}
