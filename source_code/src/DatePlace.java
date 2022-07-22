import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DatePlace{
    private Optional<List<Location>> aLocations;
    private LocalDate aDate;

    public DatePlace(Optional<List<Location>> pLocations, LocalDate pDate){
        assert pLocations != null && pDate != null;
        aLocations = pLocations;
        aDate = pDate;
    }

    public Optional<List<Location>> getLocations(){
        return aLocations;
    }

    public LocalDate getDate(){
        return aDate;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || this.getClass() != obj.getClass()) return false;
        if(this == obj) return true;
        DatePlace compare = (DatePlace) obj;
        return compare.aDate.equals(aDate) && compare.aLocations.equals(aLocations);
    }

    @Override
    public int hashCode(){
        return aDate.hashCode() + aLocations.hashCode();
    }
}