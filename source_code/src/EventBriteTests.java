import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventBriteTests {
    EventManagement app;
    EventFactory factory;
    List<VIP> VIPs = new ArrayList<>();
    List<Event> hostEvents;
    List<LocalDate> dates = new ArrayList<>();
    @BeforeEach
    void setUp(){
        app = new EventManagement();
        factory = app.getEventFactory();
        VIP v1 = new VIP("v1");
        VIP v2 = new VIP("v2");
        VIPs.add(v1);
        VIPs.add(v2);
        for(int i = 10; i<31; i+=2){
            dates.add(LocalDate.of(2022,3,i));
        }
    }
    @Test
    void testNoDuplicateDateAndLocation(){

        Event c1 = factory.createConcertEvent("c1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        Event c2 = factory.createConcertEvent("c2",dates.get(0),245,330,Location.BellCentre,"a2",VIPs);
        Event c3 = factory.createConcertEvent("c1",dates.get(1),200,200,Location.BellCentre,"a1",VIPs);
        assertNotSame(c1, c3);
        assertSame(c1, c2);
    }

    @Test
    void testAddEventToApp1(){
        Event c1 = factory.createConcertEvent("c1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        app.addEvent(c1);
        assertEquals(c1,app.getHostedEvents().get(0));
    }

    @Test
    void testAddEventToApp2(){
        app.addConcertEvent("c1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        app.addGalaEvent("g2",dates.get(1),200,200,Location.BellCentre,VIPs);
        List<Event> allEvents = app.getHostedEvents();
        assertEquals(dates.get(0),allEvents.get(0).getDate());
        assertEquals(Location.BellCentre,allEvents.get(0).getLocations().get().get(0));

        assertEquals(dates.get(1),allEvents.get(1).getDate());
        assertEquals(Location.BellCentre,allEvents.get(1).getLocations().get().get(0));
    }

    @Test
    void testConcertSetGet(){
        Event c1 = factory.createConcertEvent("c1",dates.get(2),200,200,Location.BellCentre,"a1",VIPs);
        Concert c = (Concert) c1;
        assertEquals("a1",c.getArtist());
        assertEquals(VIPs,c.getaVIPs());
        VIP newV = new VIP("v123");
        c.setArtist("newArtist");
        c.addVIP(newV);
        VIPs.add(newV);
        assertEquals(VIPs,c.getaVIPs());
        assertEquals("newArtist",c.getArtist());

        assertEquals("c1",c.getName());
        assertEquals(dates.get(2),c.getDate());
        assertEquals(Location.BellCentre,c.getLocations().get().get(0));
        assertEquals(200,c.getPrice().get());
        assertEquals(200,c.getNumTickets().get());
    }

    @Test
    void testGalaSetGet(){
        Gala g = (Gala) factory.createGalaEvent("g",dates.get(2),200,200,Location.BellCentre,VIPs);

        assertEquals(VIPs,g.getaVIPs());
        VIP newV = new VIP("v123");
        g.addVIP(newV);
        VIPs.add(newV);
        assertEquals(VIPs,g.getaVIPs());

        assertEquals("g",g.getName());
        assertEquals(dates.get(2),g.getDate());
        assertEquals(Location.BellCentre,g.getLocations().get().get(0));
        assertEquals(200,g.getPrice().get());
        assertEquals(200,g.getNumTickets().get());
    }

    @Test
    void testWorkShopSetGet(){
        List<Workshop> pre = new ArrayList<>();
        Workshop preReq = (Workshop) factory.createWorkshopEvent("w",dates.get(2),200,200,Location.BellCentre,pre);
        pre.add(preReq);
        Workshop w = (Workshop) factory.createWorkshopEvent("w",dates.get(2),200,200,Location.Multiple,pre);

        for(int i = 0; i<w.getPrerequisites().size();i++){
            // check location and date to see if they are same events
            assertEquals(pre.get(i).getDate(),w.getPrerequisites().get(i).getDate());
            assertEquals(pre.get(i).getLocations(),w.getPrerequisites().get(i).getLocations());
        }


        assertEquals("w",w.getName());
        assertEquals(dates.get(2),w.getDate());
        assertEquals(Location.Multiple,w.getLocations().get().get(0));
        assertEquals(200,w.getPrice().get());
        assertEquals(200,w.getNumTickets().get());

    }

    @Test
    void testScreeningSetGet(){
        Screening s = (Screening) factory.createScreeningEvent("s",dates.get(2),200,200,Location.BellCentre,Rating.R);
        assertEquals(Rating.R,s.getRate());
        s.setRate(Rating.G);
        assertEquals(Rating.G,s.getRate());

        assertEquals("s",s.getName());
        assertEquals(dates.get(2),s.getDate());
        assertEquals(Location.BellCentre,s.getLocations().get().get(0));
        assertEquals(200,s.getPrice().get());
        assertEquals(200,s.getNumTickets().get());
    }


    @Test
    void testFestivalSetGet(){
        List<Workshop> pre = new ArrayList<>();
        Event e1 = factory.createConcertEvent("c1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        Event e2 = factory.createGalaEvent("g",dates.get(1),200,200,Location.BellCentre,VIPs);
        Event e3 = factory.createWorkshopEvent("w",dates.get(2),200,200,Location.OlympicStadium,pre);
        Event e4 = factory.createScreeningEvent("s",dates.get(3),200,200,Location.Multiple,Rating.R);
        hostEvents = new ArrayList<>();
        hostEvents.add(e1);
        hostEvents.add(e2);
        hostEvents.add(e3);
        hostEvents.add(e4);

        Festival f = (Festival) factory.createFestivalEvent("f",hostEvents);
        for(int i = 0; i<hostEvents.size(); i++){
            Event compare = hostEvents.get(i);
            Event getEvent = f.getEvents().get(i);
            assertEquals(compare.getDate(),getEvent.getDate());
            assertEquals(compare.getLocations(),getEvent.getLocations());
        }

        assertEquals("f",f.getName());
        assertEquals(dates.get(0),f.getDate());
        assertEquals(Location.BellCentre,f.getLocations().get().get(0));
        assertEquals(Location.OlympicStadium,f.getLocations().get().get(1));
        assertEquals(Location.Multiple,f.getLocations().get().get(2));
        assertEquals(160,f.getPrice().get());
        assertEquals(200,f.getNumTickets().get());
    }

    @Test
    void testComingSoonSetGet(){
        ComingSoon cs = (ComingSoon) factory.createComingSoon("cs",dates.get(0));

        assertEquals("cs",cs.getName());
        assertEquals(dates.get(0),cs.getDate());
        assertEquals(Optional.empty(),cs.getLocations());
        assertEquals(Optional.empty(),cs.getPrice());
        assertEquals(Optional.empty(),cs.getNumTickets());
    }

    static class FilterStrategyStub implements FilterStrategy{
        // filter the events with name as event1
        List<Event> result = new ArrayList<>();
        @Override
        public List<Event> filter(List<Event> pEvents) {
            for(Event e: pEvents){
                if(e.getName().equals("event1")){
                    result.add(e);
                }
            }
            return result;
        }
    }

    @Test
    void testEventFilter(){
        List<Workshop> pre = new ArrayList<>();
        app.addConcertEvent("event1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        app.addGalaEvent("g",dates.get(1),200,200,Location.BellCentre,VIPs);
        app.addWorkshopEvent("event1",dates.get(2),200,200,Location.OlympicStadium,pre);
        app.addScreeningEvent("s",dates.get(3),200,200,Location.Multiple,Rating.R);
        app.addComingSoonEvent("event2",dates.get(3));

        FilterStrategy filterStrategy = new FilterStrategyStub();
//        app.addStrategy(filterStrategy);
        List<FilterStrategy> fs = new ArrayList<>();
        fs.add(filterStrategy);

        FilterResult result = app.applyFilter(fs);
        List<Event> resultEvents = result.getaFilteredEvents();

        assertEquals(2,resultEvents.size());

        assertEquals("event1",resultEvents.get(0).getName());
        assertEquals("event1",resultEvents.get(1).getName());

        assertEquals(dates.get(0),resultEvents.get(0).getDate());
        assertEquals(dates.get(2),resultEvents.get(1).getDate());

        assertEquals(Location.BellCentre,resultEvents.get(0).getLocations().get().get(0));
        assertEquals(Location.OlympicStadium,resultEvents.get(1).getLocations().get().get(0));


    }


    @Test
    void testCalculationOnFilterResult(){
        List<Workshop> pre = new ArrayList<>();
        List<Event> fesEvents = new ArrayList<>();
        Event e1 = factory.createConcertEvent("event1",dates.get(0),200,200,Location.BellCentre,"a1",VIPs);
        Event e2 = factory.createWorkshopEvent("event1",dates.get(2),200,200,Location.OlympicStadium,pre);
        Event e3 = factory.createComingSoon("event2",dates.get(3));

        app.addGalaEvent("g",dates.get(1),200,200,Location.BellCentre,VIPs);
        app.addScreeningEvent("s",dates.get(3),210,200,Location.Multiple,Rating.R);

        fesEvents.add(e1);
        fesEvents.add(e2);
        fesEvents.add(e3);
        app.addFestivalEvent("festival1",fesEvents);

        List<FilterStrategy> strategies = new ArrayList<>();
        FilterStrategy fs1 = new LocationFilter(Location.BellCentre);
        FilterStrategy fs2 = new PriceRangeFilter(100,200);
        strategies.add(fs1);
        strategies.add(fs2);

        try{
            Method privateMethod = EventManagement.class.getDeclaredMethod("addStrategy",FilterStrategy.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(app,fs1);
            privateMethod.invoke(app,fs2);
        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception){
            exception.printStackTrace();
        }

        FilterResult result = app.applyFilter(strategies);
        double profit = app.computeProfit(result,0.8,0.8,0.8,0.8);
        int vipCount = app.countVIP(result);

        assertEquals(32000,profit);
        assertEquals(2,vipCount);
    }

}
