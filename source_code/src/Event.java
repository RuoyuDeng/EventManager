import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
Representation of a type of EventManager.Event that can exist
 */
public interface Event extends Comparable<Event>{
    String getName();
    LocalDate getDate();
    Optional<List<Location>> getLocations();
    Optional<Double> getPrice();
    Optional<Integer> getNumTickets();

    int compareTo(Event other);
    void acceptVisitor(EventVisitor eventVisitor);
}
