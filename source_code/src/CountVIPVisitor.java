public class CountVIPVisitor implements EventVisitor {
    private int aVIPcount = 0;

    public int getVIPcount(){
        return aVIPcount;
    }

    @Override
    public void visitConcert(Concert c) {
        assert c != null;
        aVIPcount += c.getaVIPs().size();
    }

    @Override
    public void visitGala(Gala g) {
        assert g != null;
        aVIPcount += g.getaVIPs().size();
    }

    @Override
    public void visitWorkshop(Workshop w) {
        // do nothing
    }

    @Override
    public void visitScreening(Screening s) {
        // do nothing
    }

    @Override
    public void visitFestival(Festival f) {
        // do nothing
    }

    @Override
    public void visitComingSoon(ComingSoon c) {
        // do nothing
    }
}
