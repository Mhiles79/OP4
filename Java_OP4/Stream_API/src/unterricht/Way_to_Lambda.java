package unterricht;

import java.util.function.BinaryOperator;

public class Way_to_Lambda {
    public static void main(String[] args) {
        //1. Anonyme Klasse
        IOperationen addition = new IOperationen() {
            @Override
            public double berechne(double a, double b) {
                return a + b;
            }
        };
        System.out.println(addition.getClass().isAnonymousClass());
        System.out.println("Summe: " + addition.berechne(1.2, 4));

        //2. Lambda mit Block
        IOperationen subtraktion = (double a, double b) -> {
            return a - b;
        };
        System.out.println("Differenz: " + subtraktion.berechne(3.1, 2));

        //3. Lambda Kurzversion (Typinferenz: Datentypen automatisch erkannt)
        IOperationen multiplikation = (a, b) -> a * b;
        System.out.println("Produkt: " + multiplikation.berechne(5.1, 3));

        //4. Eigene Schnittstelle sparen, JDK Interfaces nehmen
        BinaryOperator<Double> verdoppeln = (a, b) -> 2 * (a + b);
        System.out.println("Umfang: " + verdoppeln.apply(4.0, 3.1));

        //5. Lambda als Funktions-Argument
        double ergebnis = rechne((a, b) -> a * a + b * b, 2, 3);
        System.out.println("Aus Funktion: " + ergebnis);
    }

    private static double rechne(IOperationen was_rechnen, double a, double b) {
        return was_rechnen.berechne(a, b);
    }
}
