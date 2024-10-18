package AdventureOOP;
// import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;


/**
 * Beinhaltet unzugeordnete Aufgaben die immer wieder abgerufen werden können.<br>
 * Beinhaltet Aufgaben, die im Hintergrund laufen und in zeitlichen Abständen stattfinden.<br>
 * Relevante Klassen:<br>
 * {@link Main}
 */
public class RepeatedTasks {
    
    private static ScheduledExecutorService executor;
    // private static int energie = 0;

    /**
     * Startet einen Timer, der alle 5 Minuten Leben und Energie regeneriert
     * @param p Player-Objekt, der von der Regeneration betroffen ist
     */
    public static void startTimer(Player p) {
        executor = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            public void run() {
                p.addEnergie(1);
                p.addCurrentLebenspunkte((int) (Math.round(0.1 * p.getMaxLebenspunkte())));
            } // run
        }; // task

        executor.scheduleAtFixedRate(task, 5, 5, TimeUnit.MINUTES);

    } // startTimer-Methode

    /**
     * Beendet den Timer.
     */
    public static void stopTimer() {
        executor.shutdownNow();
        System.out.println("Energieregeneration gestoppt.");
    } // stopTimer

    /**
     * Speichert das Spiel. Exportiert Player-Object und PlayerInventory-Daten als .dat-Dateien
     * @param p Player-Object
     * @param pInv PlayerInventory-Object
     */
    public static void saveGame(Player p, PlayerInventory pInv) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(p.getPlayerName() + ".dat"))) { // Speichert Spieler-Daten in Dat-Datei
            oos.writeObject(p);
            System.out.println("Player gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        } // try
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(p.getPlayerName() + "Inv.dat"))) { // Speichert Spieler Inventar-Daten in Dat-Datei
            oos.writeObject(pInv);
            System.out.println("Inventar gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        } // try
    } // saveGame

} // RepeatedTask-Klasse
