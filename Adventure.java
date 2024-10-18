package AdventureOOP;
import java.util.*;

/**
 * Wichtigste Methoden für das eigentliche Adventure || Bei Bedarf nochmal unterteilen in weitere Unterklassen<br>
 * Hier werden die ausgewählten Aktionen aus der Aktionen-Klasse abgespielt<br>
 * Relevante Klassen:<br>
 * {@link Aktionen}<br>
 * {@link AdventureKampf}<br>
 * {@link AdventureQuest}<br>
 * {@link Mathematik}<br>
 */
public class Adventure {

    // Trainings-Methode
    /** Trainings-Methode: reduziert die Energie um 1 und gibt dafür XP
     * @param p Player-Objekt des nutzenden Spielers
     */
    public static void Training(Player p) {
        Dialogue.Confirm("Du entscheidest dich ein wenig zu trainieren. ");
        int xp = ((p.getLevel() * 2) + Mathematik.getAbsoluteValue(p.getLevel(), 100));
        xp *= Mathematik.DynamicVarianceGenerator(20, 20);
        Dialogue.Confirm("Nach einem anstrengenden Training erhälst du " + xp + " Erfahrungspunkte, " + p.getPlayerName() + ".");
        p.addCurrentXP(xp); // Erhöht die aktuellen XP
        if(p.getEnergie() > 0) { // Falls noch Energie übrig ist
            boolean shouldRepeat = 0 == Dialogue.Choice("Möchtest du weiter trainieren, " + p.getPlayerName() + "? Du hast noch " + p.getEnergie() + " Energie", "Weiter trainieren?", new String[] {"Ja", "Nein"}, false);
            if(shouldRepeat) { // Abfrage zur Wiederholung der Trainings-Methode
                p.subEnergie(1);
                Training(p);
            } else {
                Dialogue.Confirm("Du kehrst zurück.");
            } // if-else
        } else {
            Dialogue.Confirm("Du hast keine Energie mehr und kehrst zurück.");
        } // if-else
    } // Training-Methode

    /** Kampf-Methode
     * Erzeugt einen zufällig generierten NPC-Gegner und erstellt ein entsprechendes Player-Objekt
     * @param p Player-Objekt des ausführenden Spielers
     * @param pInv PlayerInventory-Objekt von p
     */
    public static void Fighting(Player p, PlayerInventory pInv) {
        int enemyLevel = p.getLevel();
        Random enemyRandomizer = new Random(); // Randomizer
        int enemyDifficulty = (enemyRandomizer.nextInt(100) + 1); // Wahrscheinlichkeitsrechner für verschiedene Schwierigkeiten
        /** Difficulties:
         * Sehr leicht: Gegner Level - 2; Wahrscheinlichkeit: 10%           | 1-10
         * Leicht: Gegner Level - 1; Wahrscheinlichkeit: 20%                | 11-30
         * Normal: Gegner Level = Spieler Level; Wahrscheinlichkeit: 40%    | 31-70
         * Schwer: Gegner Level + 1; Wahrscheinlichkeit: 20%                | 71-90
         * Elite: Gegner Level + 2; Wahrscheinlichkeit: 10%                 | 91-100
         */
        if (enemyDifficulty < 11) {
            enemyLevel -= 2; 
        } else if (enemyDifficulty > 10 && enemyDifficulty < 31) {
            enemyLevel -= 1;
        } else if (enemyDifficulty > 70 && enemyDifficulty < 91) {
            enemyLevel += 1;
        } else if (enemyDifficulty > 90) {
            enemyLevel += 2;
        } // if-else

        if (enemyLevel < 1) { // Enemy-Level kann nicht kleiner als 1 sein.
            enemyLevel = 1;
        } // if 
    
        Enemy enemy = new Enemy(enemyLevel); // Erstellt einen Gegner mit zufälligen Statuswerten

        Random checkRandom = new Random();
        int failProbability = (checkRandom.nextInt(100) + 1);
        failProbability -= (checkRandom.nextInt(p.getGeschick()) + 1);
        failProbability *= (int) Math.round(0.9 + (pInv.getSchuhe() * 0.1));
        if(failProbability < 0) { // Wahrscheinlichkeit kann nicht negativ sein
            failProbability = 0;
        }

        boolean shouldFight = 0 == Dialogue.Choice("Möchtest du gegen folgenden Gegner kämpfen:\n\n" + enemy.getAllEnemy(),  "Gegen Level" + enemy.getLevel() + " " + enemy.getEnemyName() + " kämpfen?", new String[] {"Ja", "Nein (Erfolgschance: " + (100 - failProbability) + "%)"}, false);

        if(shouldFight) {
            Dialogue.Confirm("Der Kampf beginnt...");
            AdventureKampf.Kampf(p, pInv, enemy); // Startet den Kampf
            return;
        } else {
            boolean ifPass = (checkRandom.nextInt(100) + 1) > failProbability;
            if(ifPass) {
                Dialogue.Confirm("Du vermeidest den Kampf und suchst einen geeigneteren Gegner...");
                Fighting(p, pInv);
                return;
            } else {
                boolean fleeOrFight = checkRandom.nextInt(100) > (checkRandom.nextInt(100) * (0.95 + (pInv.getSchuhe() * 0.05)));
                if(fleeOrFight || p.getEnergie() < 1) {
                    Dialogue.Confirm("Leider wurdest du entdeckt und konntest nicht mehr rechzeitig fliehen, " + p.getPlayerName() +". Es kommt zum Kampf.");
                    AdventureKampf.Kampf(p, pInv, enemy);
                    return;
                } else {
                    Dialogue.Confirm("Du wurdest entdeckt, doch du konntest wegrennen, " + p.getPlayerName() + "!\nWÄhrend deiner Flucht hast du 1 Energie verbraucht.");
                    p.subEnergie(1);
                    return;
                } // if-else (fleeOrFight)
            } // if-else (ifPass)
        } // if-else (shouldFight)
    } // Fighting-Methode

    /** Sammel-Methode, zum Erzeugen von Ressourcen 
     * @param p Player-Object
     * @param pInv zugehöriges PlayerInventory-Object
     */
    public static void Sammeln(Player p, PlayerInventory pInv) {
        boolean repeat = true;
        int choice = 0;
        while(repeat) {
            choice = Dialogue.Choice("Wo möchtest du sammeln?", "Sammeln", new String[] {"Ebene (-1 Energie)", "Wald (-2 Energie)", "Gebirge(-3 Energie)", "Zurück"}, false);
            if(choice == 3) {
                return;
            }
            choice++;
            if(choice > p.getEnergie()) {
                Dialogue.Confirm("Dafür hast du leider nicht genug Energie.");
            } else {
                p.subEnergie(choice);
                Dialogue.Confirm("Du verbrauchst " + choice + " Energie und beginnst Ressourcen zu sammeln.");
                repeat = false;
            } // if-else
        } // while
        int lootHolz = (int) (Math.round(p.getLevel() * 1.0) + Mathematik.getAbsoluteValue(p.getLevel(), 200));
        lootHolz *= Mathematik.DynamicVarianceGenerator(10, 10);
        lootHolz = (int) (Math.round(lootHolz * ((Math.pow(choice, 2)) / 9.0)));

        int lootErz = (int) (Math.round(p.getLevel() * 0.5) + Mathematik.getAbsoluteValue(p.getLevel(), 300));
        lootErz *= Mathematik.DynamicVarianceGenerator(20, 10);
        lootErz = (int) (Math.round(lootErz * ((Math.pow(choice, 2)) / 9.0)));

        int lootEdelsteine = (int) (Math.round(p.getLevel() * 0.25) + Mathematik.getAbsoluteValue(p.getLevel(), 400));
        lootEdelsteine *= Mathematik.DynamicVarianceGenerator(30, 10);   
        lootEdelsteine = (int) (Math.round(lootEdelsteine * ((Math.pow(choice, 2)) / 9.0)));
        pInv.addHolz(lootHolz);
        pInv.addErz(lootErz);
        pInv.addEdelsteine(lootEdelsteine);

        Dialogue.Confirm("Deine Suche war erfolgreich und du konntest folgende Gegenstände finden: \n\nHolz: " + lootHolz + "\nErz: " + lootErz + "\nEdelsteine: " + lootEdelsteine);
    } // Sammel-Methode

    /**
     * Quests sind unabhängig vom Spielerlevel sondern haben feste Schwierigkeiten.
     * Folgende Schwierigkeiten gibt es:
     * - sehr einfach (empfohlen ab Level 10) / ★
     * - einfach (empfohlen ab Level 20) / ★★
     * - normal (empfohlen ab Level 30) / ★★★
     * - schwer (empfohlen ab Level 40) /  ★★★★
     * - sehr schwer (empfohlen ab Level 50) / ★★★★★
     * - heroisch (empfohlen ab Level 70) / ★★★★★★
     * @param p Player-Objekt
     * @param pInv PlayerInventory-Objekt zu p
     */
    public static void Quest(Player p, PlayerInventory pInv) {
        int choice = 0;
        int difficulty = 10;
        choice = Dialogue.Choice("Du hast dich entschieden, eine Quest anzunehmen. Welche Schwierigkeit soll diese Quest haben, " + p.getPlayerName() + "? (Kosten: 2 Energie\n(Sehr Leicht: ★ - Heroisch: ★★★★★★)", "Questauswahl", new String[] {"★", "★★", "★★★", "★★★★", "★★★★★", "★★★★★★"}, true);
        Dialogue.Confirm("Alles klar, " + p.getPlayerName() + ". Deine Quest beginnt in Kürze.");
        switch(choice) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                difficulty *= (choice + 1);
                break;
            case 5:
                difficulty *= (choice + 11);
                break;
        } // switch(choice)
        System.out.println("Difficulty Quest: " + difficulty);
        AdventureQuest.Quest(p, pInv, difficulty);
    } // Quest-Methode
} // Adventure-Class
