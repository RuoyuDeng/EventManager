import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventFactory {
    private final HashMap<DatePlace,Event> aEventsMap;

    public EventFactory(){
        aEventsMap = new HashMap<>();
    }

    /**
     * Create unique EventManager.Concert EventManager.Event
     * @param pEventName
     * @param pLocalDate
     * @param pPrice
     * @param pNumTickets
     * @param pLocation
     * @param pArtist
     * @return
     */

    public Event createConcertEvent(String pEventName, LocalDate pLocalDate,double pPrice, int pNumTickets, Location pLocation,String pArtist, List<VIP> pVIPs){
        Concert c = new Concert(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pArtist,pVIPs);
        DatePlace dp = new DatePlace(c.getLocations(),c.getDate());

        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
//            if(e instanceof EventManager.Concert) return e;
//            else return null;
//            return aEventsMap.get(dp);
        }
        else {
            aEventsMap.put(dp,c);
            return aEventsMap.get(dp);
        }
    }

    /**
     * Create unique EventManager.Gala EventManager.Event
     * @param pEventName
     * @param pLocalDate
     * @param pPrice
     * @param pNumTickets
     * @param pLocation
     * @return
     */

    public Event createGalaEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,List<VIP> pVIPs){
        Gala g = new Gala(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pVIPs);
        DatePlace dp = new DatePlace(g.getLocations(),g.getDate());
        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
//            if(e instanceof EventManager.Gala) return e;
//            else return null;
        }
        else {
            aEventsMap.put(dp,g);
            return aEventsMap.get(dp);
        }
    }

    /**
     * Create unique EventManager.Workshop EventManager.Event
     * @param pEventName
     * @param pLocalDate
     * @param pPrice
     * @param pNumTickets
     * @param pLocation
     * @param pExistPres
     * @return
     */

    public Event createWorkshopEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,List<Workshop> pExistPres){
        Workshop w = new Workshop(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pExistPres);
        DatePlace dp = new DatePlace(w.getLocations(),w.getDate());

        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
        }

        else{
            aEventsMap.put(dp,w);
            return aEventsMap.get(dp);
        }

    }

    /**
     * Create unique EventManager.Screening EventManager.Event
     * @param pEventName
     * @param pLocalDate
     * @param pPrice
     * @param pNumTickets
     * @param pLocation
     * @param pRate
     * @return
     */

    public Event createScreeningEvent(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,Rating pRate){
        Screening s = new Screening(pEventName,pLocalDate,pPrice,pNumTickets,pLocation,pRate);
        DatePlace dp = new DatePlace(s.getLocations(),s.getDate());
        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
//            if(e instanceof EventManager.Screening) return e;
//            else return null;
        }
        else{
            aEventsMap.put(dp,s);
            return aEventsMap.get(dp);
        }
    }


    /**
     * Create unique EventManager.Festival EventManager.Event
     * @param pEventName
     * @param pEvents
     * @return
     */
    public Event createFestivalEvent(String pEventName, List<Event> pEvents){
        Festival f = new Festival(pEventName,pEvents);
        DatePlace dp = new DatePlace(f.getLocations(),f.getDate());
        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
//            if(e instanceof EventManager.Festival) return e;
//            else return null;
        }
        else{
            aEventsMap.put(dp,f);
            return aEventsMap.get(dp);
        }
    }

    /**
     * Create Unique EventManager.ComingSoon events
     * @param pEventName
     * @param pLocalDate
     * @return
     */

    public Event createComingSoon(String pEventName, LocalDate pLocalDate){
        ComingSoon c = new ComingSoon(pEventName,pLocalDate);
        DatePlace dp = new DatePlace(c.getLocations(),c.getDate());
        if(aEventsMap.containsKey(dp)){
            Event e = aEventsMap.get(dp);
            return e;
//            if(e instanceof EventManager.ComingSoon) return e;
//            else return null;
        }
        else {
            aEventsMap.put(dp,c);
            return aEventsMap.get(dp);
        }
    }

    /**
     * Used to return a list of workshop given a list of events. It is used for Bob to initialize some prerequsites workshops
     * @param events
     * @return
     */

    public List<Workshop> eventToWorkshops(List<Event> events){
        List<Workshop> result = new ArrayList<>();
        if(events.size() == 0) {
            return result;
        }
        for(Event e : events){
            if (e instanceof Workshop){
                result.add((Workshop) e);
            }
        }
        return result;
    }

}
