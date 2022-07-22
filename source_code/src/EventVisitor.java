public interface EventVisitor {
    void visitConcert(Concert c);
    void visitGala(Gala g);
    void visitWorkshop(Workshop w);
    void visitScreening(Screening s);
    void visitFestival(Festival f);
    void visitComingSoon(ComingSoon c);
}
