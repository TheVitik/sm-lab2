import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

public class Process extends Element {
    private int queue, maxqueue, failure;
    private double meanQueue;
    private final PriorityQueue<Double> nextT = new PriorityQueue<>();
    private final HashMap<Element, Double> nextRandomElements = new HashMap<>();

    private int workerCount = 1;

    public Process(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
    }

    public Process(double delay, int count) {
        super(delay);
        setTnext(Double.MAX_VALUE);
        queue = 0; 
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        this.workerCount = count;
    }

    @Override
    public void setNextElement(Element nextElement) {
        nextRandomElements.put(nextElement, 1.0);
    }

    public void setNextElement(Element nextElement, Double probability) {
        nextRandomElements.put(nextElement, probability);
    }

    @Override
    public void inAct() {
        if (super.getState() < workerCount) {
            setState(getState() + 1);
            nextT.add(super.getTcurr() + super.getDelay());
            super.setTnext(nextT.peek());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        nextT.poll();

        super.setTnext(Double.MAX_VALUE);
        if (nextT.peek() != null) {
            super.setTnext(nextT.peek());
        }

        super.setState(getState() - 1);

        if (getNextElement() != null) {
            getNextElement().inAct();
        }

        if (!nextRandomElements.isEmpty()) {
            final double chance = new Random().nextDouble();
            double sum = 0;
            for (Element key : nextRandomElements.keySet()) {
                sum += nextRandomElements.get(key);
                if (chance < sum) {
                    key.inAct();
                    break;
                }
            }
        }

        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            super.setState(getState() + 1);
            nextT.add(super.getTcurr() + super.getDelay());
            super.setTnext(nextT.peek());
        }
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }
}