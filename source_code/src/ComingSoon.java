import java.time.LocalDate;

public class ComingSoon extends AbstractEvent{

    public ComingSoon(String pEventName, LocalDate pLocalDate){
        super(pEventName,pLocalDate);
    }

    @Override
    public void acceptVisitor(EventVisitor eventVisitor) {
        eventVisitor.visitComingSoon(this);
    }

}
