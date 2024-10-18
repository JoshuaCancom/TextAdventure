package AdventureOOP;
import java.util.*;

/**
 * Enthält die meisten wichtigen Formeln zur Berechnung von bestimmten Werten.<br>
 * Entählt Zufallsgeneratoren und Varianzgeneratoren.<br>
 * Wichtige Klassen:<br>
 * {@link Main}<br>
 * {@link Adventure}<br>
 */
public class Mathematik {

    private static Random randomizer = new Random(System.currentTimeMillis());

    /**
     * Funktion zum ermitteln einer zufälligen ganzen Zahl
     * @param upLimit 0 - upLimit (inklusive)
     * @param downLimit erhöht die kleinstmögliche Zufallszahl (wirkt sich nicht auf das upLimit aus)
     * @return zufällige ganze Zahl
     */
    public static int Randomizer(int upLimit) {
        return randomizer.nextInt(upLimit + 1);
    } // allgemeiner Randomizer

    public static int Randomizer(int upLimit, int downLimit) {
        return (randomizer.nextInt(upLimit - downLimit + 1) + downLimit);      
    } // allgemeiner Randomizer

    /**
     * Erstellt einen dynamisch veränderbaren und mit einer zufälligen Varianz versehenen Faktor. 
     * @param downVariance die prozentuale maximale Veränderung nach unten in Prozent (positive ganze Zahl)
     * @param upVariance die prozentuale maximale Veränderun nach oben in Prozent (positive ganze Zahl)
     * @return einen Faktor, der weiterverarbeitet werden kann
     */
    public static double DynamicVarianceGenerator(int downVariance, int upVariance) {

        double i = 1.0;

        int varianceDifferenz = (downVariance + upVariance);
        int randomValue = randomizer.nextInt(varianceDifferenz + 1);

        i += ((randomValue - downVariance) / 100.0);

        return i;
    } // DynamicVarianceGenerator

    /**
     * Formel um einen exponentiell wachsenden Wert zu erhalten
     * @param level das verarbeitete Level
     * @param base die Base zur Bestimmung des Wachstumsverhältnis zum Level. Standardwert 100 -> je größer desto kleiner das Ergebnis, vice versa
     * @return das Endergebnis, ein gerundeter Wert der weiter verarbeitet werden kann
     */
    public static int getAbsoluteValue(int level, int base) {
        double k = GrowthFactor(level, base);
        int absoluteValue = (int) (Math.round(BaseFormular(k)));
        return absoluteValue;
    } // getAbsoluteValue

    /**
     * Berechnung des Wachstumsfaktor k
     * @param level das verarbeitete Level
     * @param base die Base zur Bestimmung des Wachstumsverhältnisses zum Level. Standardwert: 100 -> Ein größerer Wert bedeutet ein kleineres Wachstum und vice versa
     * @return Wachstumsfaktor für die Formel
     */
    private static double GrowthFactor(double level, double base) {
        double factorK = (10.0 * (level / base));
        return  factorK;
    } // GrowthFactor
    
    /**
     * Berechnung eines absoluten Werts, der weiterverarbeitet werden kann.
     * @param k ist der Wachstumsfaktor und muss über Mathematik.GrowthFactor() bestimmt werden
     * @return einen ungerundeten Double-Wert
     */
    private static double BaseFormular(double k) {
        return (Math.pow(Math.E, k));
    } // BaseFormular
} // Mathe-Klasse
