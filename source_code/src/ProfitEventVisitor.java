public class ProfitEventVisitor implements EventVisitor {
    private double concertProfitRate;
    private double galaProfitRate;
    private double workshopProfitRate;
    private double screeningProfitRate;
    private double totalProfit;

    public ProfitEventVisitor(double pConcertProfitRate,double pGalaProfitRate, double pWorkshopProfitRate, double pScreeningProfitRate){
        assert pConcertProfitRate > 0;
        assert pGalaProfitRate > 0;
        assert pWorkshopProfitRate > 0;
        assert pScreeningProfitRate > 0;
        concertProfitRate = pConcertProfitRate;
        galaProfitRate = pGalaProfitRate;
        workshopProfitRate = pWorkshopProfitRate;
        screeningProfitRate = pScreeningProfitRate;
        totalProfit = 0;
    }

    public double getTotalProfit(){
        return totalProfit;
    }

    @Override
    public void visitConcert(Concert c) {
        if(c.getNumTickets().isPresent() && c.getPrice().isPresent()){
            totalProfit += concertProfitRate * (c.getNumTickets().get() * c.getPrice().get());
        }
    }

    @Override
    public void visitGala(Gala g) {
        if(g.getNumTickets().isPresent() && g.getPrice().isPresent()){
            totalProfit += galaProfitRate * (g.getNumTickets().get() * g.getPrice().get());
        }
    }

    @Override
    public void visitWorkshop(Workshop w) {
        if(w.getNumTickets().isPresent() && w.getPrice().isPresent()){
            totalProfit += workshopProfitRate * (w.getNumTickets().get() * w.getPrice().get());
        }
    }

    @Override
    public void visitScreening(Screening s) {
        if(s.getNumTickets().isPresent() && s.getPrice().isPresent()){
            totalProfit += screeningProfitRate * (s.getNumTickets().get() * s.getPrice().get());
        }
    }

    @Override
    public void visitFestival(Festival f) {
        for (Event e : f.getEvents()){
            e.acceptVisitor(this);
        }
    }

    @Override
    public void visitComingSoon(ComingSoon c) {
        // do nothing
    }

}
