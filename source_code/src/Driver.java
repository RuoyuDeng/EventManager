import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        VIP v1 = new VIP("v1");
        VIP v2 = new VIP("v2");
        VIP v3 = new VIP("v3");
        ArrayList<VIP> vipList1 = new ArrayList<>();
        vipList1.add(v1);
        vipList1.add(v2);
        vipList1.add(v3);

        LocalDate d1 = LocalDate.of(2022,3,10);
        LocalDate d2 = LocalDate.of(2022,3,12);
        LocalDate d3 = LocalDate.of(2022,3,14);
        LocalDate d4 = LocalDate.of(2022,3,16);
        LocalDate d5= LocalDate.of(2022,4,22);

        EventManagement system = new EventManagement();
        EventFactory factory = system.getEventFactory();
        FilterStrategy priceFilter = new PriceRangeFilter(30,180);
        FilterStrategy locationFilter = new LocationFilter(Location.BellCentre);

        List<FilterStrategy> filters = new ArrayList<>();
        filters.add(priceFilter);
        filters.add(locationFilter);
        List<FilterStrategy> emptyFilter = new ArrayList<>(); // Empty filter uses no filter, so the filtered result will be identical to the input

        List<Event> preReqs = new ArrayList<>();
        List<Event> initialPres = new ArrayList<>();
        List<Event> festivalEvents = new ArrayList<>();

        Event c1 = factory.createConcertEvent("concert1", d1,80,10,Location.BellCentre,"artist1",vipList1);
        Event g1 = factory.createGalaEvent("gala1",d3,100,100,Location.PlaceDesArts,vipList1);
        Event s1 = factory.createScreeningEvent("screening1",d4,200, 100,Location.Multiple,Rating.G);
        Event cs1 = factory.createComingSoon("comingsoon1",d4);

        Event w1 = factory.createWorkshopEvent("workshop1",d2,90,30,Location.OlympicStadium,factory.eventToWorkshops(initialPres));
        preReqs.add(w1);
        Event w2 = factory.createWorkshopEvent("workshop2",d2,120,200,Location.BellCentre,factory.eventToWorkshops(preReqs)); // w2 has preRequisite
        // w3 and g1 are the same events due to same date and location constrain, it succeeds to create but it is not a NEW event
        Event w3 = factory.createWorkshopEvent("workshop1",d3,90,30,Location.PlaceDesArts,factory.eventToWorkshops(preReqs));
        System.out.println(w3 == g1);

        // prepare the events for a festival, Bob can add events to system through system.addEvent()
        festivalEvents.add(s1);
        festivalEvents.add(cs1);
        system.addFestivalEvent("festival1",festivalEvents); // has s1 and cs1
        festivalEvents.add(c1);
        festivalEvents.add(g1);
        system.addFestivalEvent("festival2",festivalEvents); // has s1,cs1,c1 and g1
        system.addEvent(w2);
        system.addEvent(c1);
        system.addEvent(g1);
        system.addEvent(s1);
        system.addEvent(cs1);

        // OR add to system without creating any new event instance
        system.addGalaEvent("gala2",d5,200,40,Location.Multiple,vipList1);

        FilterResult filterResult1 = system.applyFilter(filters);
        double totalProfit = system.computeProfit(filterResult1,0.6,0.5,0.3,0.1);
        int vipCount = system.countVIP(filterResult1);
        System.out.println("Using filter 1");
        System.out.printf("The filtered events have %d of VIPs and the expected profit is: %f%n",vipCount,totalProfit);

        FilterResult filterResult2 = system.applyFilter(emptyFilter);
        totalProfit = system.computeProfit(filterResult2,0.6,0.5,0.3,0.1);
        vipCount = system.countVIP(filterResult2);
        System.out.println("Using filter 2");
        System.out.printf("The filtered events have %d of VIPs and the expected profit is: %f%n",vipCount,totalProfit);
        System.out.println(system.getHostedEvents());
    }
}
