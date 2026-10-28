package unterricht;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

//In C# heißt das Konzept LinQ
public class Funktionale_Schnittstellen {
    private List<Mitarbeiter> employees;

    public Funktionale_Schnittstellen() {
        this.employees = new ArrayList<>();
        employees.add(new Mitarbeiter("Anna Müller", "IT", 65000, 28, true));
        employees.add(new Mitarbeiter("Max Schmidt", "HR", 45000, 35, true));
        employees.add(new Mitarbeiter("Lisa Weber", "IT", 72000, 31, true));
        employees.add(new Mitarbeiter("Tom Klein", "Marketing", 38000, 26, false));
        employees.add(new Mitarbeiter("Sara Fischer", "Finance", 55000, 42, true));
        employees.add(new Mitarbeiter("Paul Wichter", "IT", 68000, 29, true));
    }

    //Predicate: v.a. für Filter: 1 Parameter -> gibt boolean zurück
    private void predicates() {
        //Gehalt > 50000
        Predicate<Mitarbeiter> hohesGehalt = m -> m.getGehalt() > 50000;
        System.out.println(hohesGehalt.test(employees.get(1)));
        //IT Abteilung
        Predicate<Mitarbeiter> it_abteilung = m -> m.getAbteilung().equals("IT");
        System.out.println(it_abteilung.test(employees.get(0)));

        Predicate<Mitarbeiter> it_topverdiener = hohesGehalt.and(it_abteilung);
        System.out.println(it_topverdiener.test(employees.get(2)));

        employees.stream()
            .filter(hohesGehalt)
            .filter(m -> m.getAlter() > 30)
            .forEach(System.out::println);
    }

    //Function<Input, Output>: nimmt Input, gibt Output zurück
    private void functions() {
        //Wenn Parameter- und Rückgabe-Typen kompatibel zu Functional Interface,
        //kann ich Methodenreferenz nehmen
        Function<Mitarbeiter, String> ma_name = Mitarbeiter::getName; //m -> m.getName();
        System.out.println(ma_name.apply(employees.getFirst()));

        //Jede Funktionsreferenz kann als Lambda dargestellt werden, aber nicht umgekehrt
        Function<Mitarbeiter, Double> bonus = m -> m.getGehalt() * 0.1;
        System.out.println(bonus.apply(employees.getLast()));

        employees.stream()
            .filter(Mitarbeiter::isAktiv)
            .map(m -> m.getName().toLowerCase().replace(" ", ".")+"@firma.de")
            .forEach(System.out::println);
    }

    //Consumer: nimmt Argument und gibt nix zurück
    private void consumer() {
        Consumer<Mitarbeiter> ma_gehalt = m -> System.out.println(m.getName() + ": " + m.getGehalt());
        ma_gehalt.accept(employees.get(1));

        //Filtere nach Nachnamen mit W beginnend, danach sortiere nach Alter, erstelle neue Liste
        Comparator<Mitarbeiter> aufsteigend = (a, b) -> Integer.compare(b.getAlter(), a.getAlter());
        List<Mitarbeiter> gefiltert = employees.stream()
            .filter(m -> m.getName().split(" ")[1].startsWith("W"))
            .sorted(aufsteigend)
            .toList();

        gefiltert.forEach(System.out::println);
    }

    public static void main(String[] args) {
        Funktionale_Schnittstellen system = new Funktionale_Schnittstellen();
        system.predicates();
        System.out.println("+".repeat(20));
        system.functions();
        System.out.println("+".repeat(20));
        system.consumer();
    }
}
