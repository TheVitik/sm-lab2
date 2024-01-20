import java.util.ArrayList;
import java.util.List;

public class SimModel {
    public static void main(String[] args) {
        Create c = new Create(1.0);
        c.setName("CREATOR");
        c.setDistribution("exp");

        Process p1 = new Process(1.0, 2);
        p1.setName("PROCESS 1");
        p1.setMaxqueue(5);
        p1.setDistribution("exp");

        Process p2 = new Process(1.0, 2);
        p2.setName("PROCESS 2");
        p2.setMaxqueue(5);
        p2.setDistribution("exp");

        Process p3 = new Process(1.0);
        p3.setName("PROCESS 3");
        p3.setMaxqueue(5);
        p3.setDistribution("exp");

        Despose d = new Despose(1.0);
        d.setName("DESPOSE");

        c.setNextElement(p1);
        p1.setNextElement(p2, 0.9);
        p1.setNextElement(d, 0.1);
        p2.setNextElement(p3);
        p3.setNextElement(d, 0.6);
        p3.setNextElement(p1, 0.4);

        ArrayList<Element> list = new ArrayList<>(List.of(new Element[]{c, p1, p2, p3, d}));
        System.out.println("list = " + list);

        Model model = new Model(list);
        model.simulate(100.0);
    }
}