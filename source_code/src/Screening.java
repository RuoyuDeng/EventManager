import java.time.LocalDate;

public class Screening extends AbstractEvent{
    private Rating aRate;

    public Screening(String pEventName, LocalDate pLocalDate, double pPrice, int pNumTickets, Location pLocation,Rating pRate){
        super(pEventName,pLocalDate,pPrice,pNumTickets,pLocation);
        assert pRate != null;
        aRate = pRate;
    }


    public void setRate(Rating pRate){
        assert pRate != null;
        aRate = pRate;
    }

    public Rating getRate(){
        return aRate;
    }

    @Override
    public void acceptVisitor(EventVisitor eventVisitor) {
        eventVisitor.visitScreening(this);
    }
}
