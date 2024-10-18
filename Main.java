package AdventureOOP;
import java.io.*;
// import java.nio.*;
import java.util.*;
// import javax.swing.*;

/**Public Class Main<br>
 * Main-Klasse, lässt die Start-Sequenz laufen.<br>
 * Auswahl zwischen: Charakter laden und neuen Spielstand erstellen.<br>
 * Beim ersten Starten des Programms wird automatisch ein neuer Spielstand erstellt.<br>
 * Wiederholt in Dauerschleife das Hauptmenü, bis das Programm vom Nutzer beendet wird.<br>
 * Alle anderen Methoden und Klassen returnen zurück zur Main-Klasse ins Hauptmenü.<br>
 * Relevante Klassen:<br>
 * {@link Aktionen}<br>
 * {@link Character}<br>
 * {@link PlayerInventory}<br>
 * {@link RepeatedTasks}<br>
 */
public class Main {
    public static void main(String[] args) {     
        
        Player mainChar = new Player("test", 1); // Objekterstellung für Load-Funktion
        PlayerInventory mainCharInventory = new PlayerInventory(); // Objekterstellung für Load-Funktion
        boolean load = false;
        String playerName = "";

        File file = new File("Spieler.txt"); // Überprüft ob ein Savegame vorhanden ist
        if (file.exists()) {
            try {
                Scanner myScanner =  new Scanner(file);
                playerName = myScanner.nextLine();
                load = Dialogue.Choice("Möchtest du folgenden Charakter laden: " + playerName, "Spiel laden?", new String[] {"Ja", "Nein"}, true) == 0; // Abfrage, ob das Savegame geladen werden soll
                myScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Fehler");
                e.printStackTrace();
            } // try-catch
        } // if

        if (load) { // Lädt einen alten Spielstand

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerName + ".dat"))) { // Übernimmt die Daten des Spielers
                mainChar = (Player) ois.readObject();
                System.out.println("Spieler geladen: " + mainChar.getPlayerName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } // try-catch
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(playerName + "Inv.dat"))) { // Übernimmt die Daten des Spieler-Inventars
                mainCharInventory = (PlayerInventory) ois.readObject();
                System.out.println("Spieler-Inventar geladen: " + mainChar.getPlayerName());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } // try-catch
            Dialogue.Confirm("Willkommen zurück, " + mainChar.getPlayerName() + ".");

        } else { // Löscht den aktuellen Save und erstellt einen neuen Save

            try { // Erstellt eine Save-Datei
                File mainCharSaveFile = new File("Spieler.txt");
                mainCharSaveFile.delete();
                mainCharSaveFile.createNewFile();
                System.out.println("Speicherdatei wurde angelegt.");                
            } catch (IOException e) {
                System.out.println("Spechern fehlgeschlagen.");
                e.printStackTrace();
            } // try-catch

            // Begrüßungsdialog und Einführung in das Spiel

            Dialogue.Confirm("Willkommen in Aldhaven, Reisender." 
                                            + " Hier in Aldhaven wird dein Abenteuer beginnen."
                                            + "\nDu wirst dein altes Leben zurücklassen und nie wieder einen Blick hinter dich werden."
                                            + "\nHier kannst du sein wer oder was du willst.");
            Dialogue.Confirm("Nun, da du hier ein neues Leben anfängst, verrate mir doch, wie du gerne genannt werden möchtest.");

            playerName = Dialogue.UserInputString("Wie möchtest du genannt werden?", true); // freie User-Eingabe zum Namen. Es gibt atm keine Restrictions.

            Dialogue.Confirm("Also schön! Von nun an sollst du " + playerName + " genannt werden.");
            Dialogue.Confirm("Um hier in Aldhaven zurecht zu kommen, wirst du gegen Monster kämpfen und einige Quests erledigen müssen, " + playerName + ". Dazu kannst du eine von drei Klassen wählen.");

            int confirm = 1;
            int playerKlasse = 0; // 0 = Soldat,  1 =  Waldläufer, 2 = Lehrling

            while(confirm == 1) { // Bestätigungsdialog, wird so lange wiederholt, bis der User die Eingabe mit "Ja" bestätigt.
                playerKlasse = Dialogue.Choice("Wähle eine Klasse (mit Klick auf eine Klasse kommst du in einen Bestätigungsdialog, in dem du mehr über die jeweilige Klasse erfährst.)", "Wähle deine Klasse", new String[] {"Soldat", "Waldlaeufer", "Lehrling"}, false);
                String options2[] = {"Ja", "Nein"};
                switch(playerKlasse) {                
                    case 0:
                        confirm = Dialogue.Choice("SOLDAT, ihr bestes Attribut ist die Stärke, dafür sind sie nicht so flink wie die anderen Klassen. Sie tragen schwere Rüstung und Schwert und Schild.\nMit genug Training kann ein SOLDAT sich zum KRIEGER oder RITTER weiterentwickeln.", "Bist du ein SOLDAT?", options2, false);
                        break;
                    case 1:
                        confirm = Dialogue.Choice("WALDLAEUFER, richten durchschnittlichen Schaden an, bleiben dem gegnerischen Auge aber verborgen und sind nur schwer zu fassen. Ihre Hauptwaffe ist der Bogen, doch sie tragen stets auch einen Dolch bei sich.\nMit genug Trainig kann ein WALDLAEUFER sich zum JAEGER oder DIEB weiterenwickeln.", "Bist du ein WALDLAEUFER?", options2, false);
                        break;
                    case 2:
                        confirm = Dialogue.Choice("LEHRLING, ein angehender Magier. Verursacht unglaublichen Schaden und verbringt die meiste Zeit mit dem Lesen von Büchern, daher fehlt es ihm in allen anderen Attributen.\nMit genug Training kann sich ein LEHRLING zum MAGIER oder ALCHEMISTEN ausbilden lassen.", "Bist du ein LEHRLING?", options2, false);
                        break;
                } // switch
            } // while

            try { // Erstellt den Spieler-Save in einer Text-Datei, damit sie später wieder abgerufen werden können.
                FileWriter playerEditor = new FileWriter("Spieler.txt");
                playerEditor.write(playerName);
                playerEditor.close();
                System.out.println("Spieler hinzugefügt: " + playerName);
            } catch (IOException e) {
                System.out.println("Fehler");
                e.printStackTrace();
            } // try-catch       

            // Erstellt Player- und PlayerInventory-Objects und gibt eine Ausgabe zur Überprüfung aus
            mainChar = new Player(playerName, playerKlasse);
            Dialogue.Confirm(mainChar.getAllPlayer()); 
            mainCharInventory = new PlayerInventory();
            Dialogue.Confirm(mainCharInventory.getAll());

            // Speichert Player-Object und PlayerInventory-Object
            RepeatedTasks.saveGame(mainChar, mainCharInventory); 

        } // else

        // Startet den Timer für die Energie-Regeneration und Lebens-Regeneration

        RepeatedTasks.startTimer(mainChar);

        // Haupt-Menü, wird in Dauerschleife wiederholt, bis der User das Programm beendet. Hier können verschiedene Aktionen ausgewählt werden (aka Klassen und Funktionen aufgerufen werden)
        boolean beenden = false;
        while(!beenden) {
            int aktion = Dialogue.Choice("Was möchtest du als nächstes tun, " + mainChar.getPlayerName() + "?", "Auf Abenteuer gehen", new String[] {"Abenteuer", "Attribute erhöhen", "Ausrüstung upgraden", "Status", "Shop", "Speichern und Beenden", "Test-Button"}, false);
            switch(aktion) {
                case 0:
                if (mainChar.getEnergie() > 0) {
                    if (mainChar.getCurrentLebenspunkte() < (mainChar.getMaxLebenspunkte()/10)) {
                        Dialogue.Confirm("Du bist zu schwer verletzt, um auf Abenteuer zu gehen.");
                    } else {
                        Aktionen.generateAdventure(mainChar, mainCharInventory);
                    } // if-else Lebenspunkte
                } else  {
                    Dialogue.Confirm("Leider hast du nicht genug Energie.");
                } // if-else Energie
                    break;
                case 1:
                if(mainChar.getSP() > 0) {
                    Aktionen.Skills(mainChar);
                } else {
                    Dialogue.Confirm("Leider hast du keine Skillpunkte mehr.");
                } // if-else
                    break;
                case 2:
                Aktionen.Upgrade(mainChar, mainCharInventory);
                    break;
                case 3:
                Dialogue.Confirm(mainChar.getAllPlayer());
                Dialogue.Confirm(mainCharInventory.getAll());
                    break;
                case 4:
                Aktionen.shopChoice(mainChar, mainCharInventory);
                    break;  
                case 5:
                int choice = Dialogue.Choice("Möchest du das Spiel speichern oder beenden?", "Spiel beenden", new String[] {"Speichern", "Speichern und beenden", "Beenden ohne Speichern"}, true);
                if(choice > 0) {
                    beenden = true;
                } // if
                if(choice < 2) {
                    RepeatedTasks.saveGame(mainChar, mainCharInventory);
                } // if
                    break;
                case 6:
                // Platz für Test-Code
                mainChar.addEnergie(10);
                mainChar.setCurrentLebenspunkteToMax(mainChar.getMaxLebenspunkte());
                // mainCharInventory.subEdelsteine(mainCharInventory.getEdelsteine());
                // mainCharInventory.subErz(mainCharInventory.getErz());
                // mainCharInventory.subHolz(mainCharInventory.getHolz());
                // mainChar.addCurrentXP(mainChar.getMaxXP());
                    break;
            } // switch

        } // while

        // Beendet das Programm
        RepeatedTasks.stopTimer();
        Dialogue.Confirm("Programm wird beendet."); 
       
    } // Main-Methode
} // Main-Klasse
