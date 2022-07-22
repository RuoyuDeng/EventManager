import java.util.List;

public interface FilterStrategy {
    List<Event> filter(List<Event> pEvents);
}
