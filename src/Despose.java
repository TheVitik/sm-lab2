public class Despose extends Element{
    public Despose(double delay) {
        super(delay);
    }
    @Override
    public void inAct() {
        super.inAct();
        outAct();
    }
    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
    }
    @Override
    public void printInfo() {
        super.printInfo();
    }
    @Override
    public void doStatistics(double delta) {
        super.doStatistics(delta);
    }
}
