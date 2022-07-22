import java.util.ArrayList;
import java.util.List;

public class PriceRangeFilter implements FilterStrategy{
    private double lowerBound;
    private double upperBound;

    public PriceRangeFilter(double pLowerBound, double pUpperBound){
        assert pLowerBound > 0 && pUpperBound > 0 && pUpperBound > pLowerBound;
        lowerBound = pLowerBound;
        upperBound = pUpperBound;
    }

    @Override
    public List<Event> filter(List<Event> pEvents) {
        List<Event> result = new ArrayList<>();
        for(Event e : pEvents){
            if(e.getPrice().isPresent()){
                double price = e.getPrice().get();
                if(price >= lowerBound && price <= upperBound){
                    result.add(e);
                }
            }
        }
        return result;
    }
}
