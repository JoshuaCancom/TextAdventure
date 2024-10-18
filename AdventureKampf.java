package AdventureOOP;

/**
 * Kampf-Klasse, hier sind alle wichtigen Funtkionen für den Kampf im Spiel hinterlegt.<br>
 * Diese Funktionen unmfassen den Kampf selbst, die Hit-Validation, die Schadensberechnung (inklusive Crit-Validation) sowie den Ablauf bei Sieg oder Niederlage gegen einen Gegner.<br>
 * Jeder Kampf dauert maxmal 10 Runden und wird beendet wenn einer der drei Bedinungen erfüllt sind:<br>
 * - Spieler auf 0 Leben<br>
 * - Gegner auf 0 Leben<br>
 * - nach 10 Runden<br>
 * Falls nach 10 Runden keine Partei besiegt ist (0 Leben hat), gewinnt die Partei, die den meisten Schaden angerichtet hat.<br>
 * Relevante Klassen:<br>
 * {@link Adventure}<br>
 * {@link Mathematik}<br>
 */
public class AdventureKampf {

    /**
     * Die Hauptmethode zum Kampf. Hier werden alle relevanten Daten gesammelt und verarbeitet um daraus einen zufallsgeneriertem Kampf zu gestalten.
     * Das Ergebnis des Kampfes wird an eine andere Methode derselben Klasse weitergegeben.
     * @param p ist das Spieler-Objekt
     * @param pInv  ist das Inventar von p (Spieler)
     * @param enemy ist das Gegner-Objekt
     */    
    public static void Kampf(Player p, PlayerInventory pInv, Enemy enemy) {

        int rundenCounter; // maximale Rundenanzahl = 10
        int dmgTemp = 0;
        int dmgTotalOut = 0;
        int dmgTotalIn = 0;
        boolean isHit =  false;
        boolean isDefeated =  false;
        String message = "";

        for(rundenCounter = 1; rundenCounter < 11; rundenCounter++) { // startet bei 1, weil ein Rundenzähler nicht bei 0 startet
           
            // - - - - - Kampfphase 1-A: Spieler greift Gegner an, Trefferabfrage (isHit?) - - - - -
            message += "\n- - - - - R U N D E   " + rundenCounter + " - - - - -\n\n";

            isHit = isHitCheck(p, pInv.getSchuhe(), enemy, 1);

            // - - - - - Kampfphase 1-B: Schadensberechnung, falls ein Treffer gelandet wurde  - - - - -
            if(isHit) { 
                // Je nach Klasse sind unterschiedliche Attribute relevant.
                switch(p.getPlayerKlasse()) { // 0 = Soldat, 1 = Waldlaeufer, 2 = Lehrling, 3 = Krieger, 4 = Ritter, 5 = Jaeger, 6 = Dieb, 7 = Magier, 8 = Alchemist, 9 = Feldherr, 10 = Tempelritter, 11 = Scharfschuetze, 12 = Assassine, 13 = Zaubermeister, 14 = Weiser
                    case 0:
                    case 3:
                    case 4:
                    case 9:
                    case 10:
                        dmgTemp = (int) Math.round((p.getStaerke() * Mathematik.DynamicVarianceGenerator(10, 10)) * 0.9); 
                        dmgTemp += (int) Math.round((p.getGeschick() * Mathematik.DynamicVarianceGenerator(10, 10)) * 0.3);
                        break;
                    case 1:
                    case 5:
                    case 6:
                    case 11:
                    case 12:
                        dmgTemp = (int) Math.round((p.getGeschick() * Mathematik.DynamicVarianceGenerator(10, 10)) * 0.5);
                        dmgTemp += (int) Math.round((p.getStaerke() * Mathematik.DynamicVarianceGenerator(10, 10)) * 0.7);
                        break;
                    case 2:
                    case 7:
                    case 8:
                    case 13:
                    case 14:
                        dmgTemp = (int) Math.round((p.getWeisheit() * Mathematik.DynamicVarianceGenerator(10, 10)) * 1.2);
                        break;
                    } // switch
                
                dmgTemp = AdventureKampf.dmgCalc(p, dmgTemp, enemy, pInv.getWaffe(), 1);

                if(AdventureKampf.isCritCheck(pInv.getZweitwaffe(), 1)) {
                    dmgTemp *= 1.5;
                    message += "Ein kritischer Treffer! ";
                };

                // - - - - - Kampfphase 1-C: Schaden wird angewendet
                dmgTotalOut += dmgTemp; // sammelt insgesamten verursachten Schaden, falls keine Partei auf 0 Leben fällt
                enemy.subCurrentLebenspunkte(dmgTemp);
                isDefeated = enemy.getCurrentLebenspunkte() == 0;  

                // - - - - - Kampfphase 1-D: Auswertung des Angriffs bzw. der Runde - - - - -
                if (isDefeated) { 
                    message += "Du hast " + dmgTemp + " Schaden verursacht und den Gegner besiegt.\n";
                    Dialogue.Confirm(message);
                    AdventureKampf.Victory(p, pInv, enemy);
                    return;
                } else { // isDefeated
                    message += "Du hast " + dmgTemp + " Schaden verursacht.\n";
                } // ifDefeated      

            }  else { // isHit
                message += "Leider hast du den Gegner verfehlt, " + p.getPlayerName() + ".\n";

            } // if isHit
            
            // - - - - - SEITENWECHSEL - - - - - 
            
            // - - - - - Kampfphase 2-A: Gegner greift Spieler an (Trefferabfrage) - - - - -
            isHit = isHitCheck(enemy, 1, p, pInv.getSchuhe());

            // - - - - - Kampfphase 2-B: Schadensberechnung, falls ein Treffer gelandet wurde - - - - -
            if(isHit) {

                dmgTemp = (int) Math.round((enemy.getAngriff() * Mathematik.DynamicVarianceGenerator(10, 10))); // = equivalänt zum Player-Switch-Case für die verschiedenen Klassen
                dmgTemp = AdventureKampf.dmgCalc(enemy, dmgTemp, p, 1, pInv.getRuestung());

                if(AdventureKampf.isCritCheck(1, pInv.getZweitwaffe())) {
                    dmgTemp *= 1.5;
                    message += "Ein kritischer Treffer! ";
                };

                // - - - - - Kamnpfphase 2-C: Schaden wird angewandt - - - - -
                dmgTotalIn += dmgTemp; // sammelt insgesamt erhaltenen Schaden, falls keine Partei auf 0 Leben fällt
                p.subCurrentLebenspunkte(dmgTemp);
                isDefeated = p.getCurrentLebenspunkte() == 0;  

                // - - - - - Kampfphase 2-D: Auswertung des Angriffs bzw. der Runde - - - - -
                if(isDefeated) { 
                    message += enemy.getEnemyName() + " hat dir " + dmgTemp + " Schaden verursacht.\nDu hast zu großen Schaden erlitten und musst dich als Verlierer aus dem Kampf zurückziehen.";
                    Dialogue.Confirm(message);
                    AdventureKampf.Defeat(p, pInv, enemy);
                    return;
                } else { // isDefeated
                    message += enemy.getEnemyName() + " hat dir " + dmgTemp + " Schaden verursacht.\n";
                } // ifDefeated      

            } else { // isHit
                message += "Du konntest dem Gegner ausweichen, " + p.getPlayerName() + ".\n";

            } // isHit
        } // for Rundencounter

        // - - - - - Kampfphase 3: finale Auswertung, falls keine Partei besiegt wurde: - - - - -
        isDefeated = dmgTotalOut > dmgTotalIn; // true, wenn der Spieler mehr Schaden ausgeteilt als eingesteckt hat

        if(isDefeated) {
            message += "Du konntest dem Gegner mehr Schaden verursachen (" + dmgTotalOut + ") und hast den Kampf gewonnen!";
            Dialogue.Confirm(message);
            Victory(p, pInv, enemy);
        } else {
            message += "Leider hat der Gegner dir mehr Schaden angerichtet (" + dmgTotalOut + ") und hast den Kampf verloren...";
            Dialogue.Confirm(message);
            Defeat(p, pInv, enemy);
        } // if isDefeated

    } // Kampf-Methode

    /** Teffer-Validation: 
     * Überprüft und berechnet, wie wahrscheinlich ein Treffer ist. Standardtrefferwahrscheinlichkeit ist 80%, variiert durch Geschicklichkeitswert. <br>
     * HINWEIS: ein NPC hat immer den Ausrüstungswert 1
     * @param attacker das Player-Objekt, welches gerade aktiv ist, also die Angreifende Partei
     * @param attackerSchuhe das Level der Schuh-Ausrüstung vom PlayerInventory-Objekt des angreifenden Player-Objektes
     * @param defender das Player-Objekt, welches nicht aktiv ist, also die Verteidigende Partei
     * @param defenderSchuhe das Level der Schuh-Ausrüstung vom PlayerInventory-Objekt des verteidigen Player-Objektes     
     * @return Boolean-Wert, der einen Treffer validiert oder eben nicht
     */
    private static boolean isHitCheck(Character attacker, int attackerSchuhe, Character defender, int defenderSchuhe) {
        boolean isHit = false;
        int gesTemp;
        int ausTemp;

        gesTemp = (int) Math.round((attacker.getBeweglichkeit() * Mathematik.DynamicVarianceGenerator(10, 10))); // zufälliger Geschicklichkeitswert (Spieler)
        gesTemp *= (int) Math.round(0.9 + (attackerSchuhe * 0.1));

        ausTemp = (int) Math.round((defender.getBeweglichkeit() * Mathematik.DynamicVarianceGenerator(10, 10))); // zufälliger Ausweichwert (Gegner)
        ausTemp *= (int) Math.round(0.9 + (defenderSchuhe * 0.1));

        double k = 1.2; // Konstante, welcher die Steilheit der Kurze beeinflusst (schnelles Wachstum -> größer 0 & kleiner 1, langsames Wachstum -> größer 1)
        double avg = ((gesTemp + ausTemp) / 2); // für den relativen Wert
        double hitChance = (1 / (1 + Math.exp((ausTemp - gesTemp) / (avg * k))));
        hitChance += 0.3; // Grundwahrscheinlichkeit 50% + 30% (0.3)
        isHit = Math.random() <= hitChance;

        return isHit;
    } // isHitCheck
    
    /** Damage-Calculator:
     * Errechnet den finalen Angriffs- und Verteidigungswert und erzeugt dadurch einen netto Schadenswert.
     * HINWEIS: ein NPC hat immer den Ausrüstungswert 1
     * @param attacker angreifendes Player-Objekt
     * @param dmgTemp temporärer Schadenswert in Abhänigkeit von der Klasse
     * @param defender verteidigendes Player-Objekt
     * @param waffe Waffenlevel des angreifendes Player-Objekts
     * @param ruestung Waffenlevel des verteidigenden Player-Objekts
     * @return den netto Schadenswert für die Weiterberechnung
     */
    private static int dmgCalc(Character attacker, int dmgTemp, Character defender, int waffe, int ruestung) {
        double verTempPer = 0;

        // Schadensberechnung: raw damage
        dmgTemp *= (int) (Math.round((0.9 + ((waffe) * 0.1))));

        // Schadensberechnung: verteidigung/Schadensreduktion
        verTempPer = Math.round((defender.getVerteidigung() * Mathematik.DynamicVarianceGenerator(10, 10)));
        verTempPer *= Math.round((0.9 + ((ruestung) * 0.1)));

        int k = 155; // Steilheit der Kurve
        verTempPer = (0.7 * ( 1 - Math.exp(-verTempPer / k)));

        // Schadensberechnung: net damage
        dmgTemp -= (dmgTemp * verTempPer);

        return dmgTemp;
    } // DamageCalculator

    /**
     * Überprüft, ob ein kritischer Treffer vorliegt
     * @param atkZweitwaffe Zweitwaffenlevel des Angreifers
     * @param defZweitwaffe Zweitwaffenlevel des Verteidiger
     * @return ob ein kririscher Treffer vorliegt
     */
    private static boolean isCritCheck(int atkZweitwaffe, int defZweitwaffe) {
        int changeRate = atkZweitwaffe - defZweitwaffe;
        int upLimit = 100 + (2 * changeRate);
        int downLimit = 0 + (2 * changeRate);
        int randomInt = Mathematik.Randomizer(upLimit, downLimit);

        return (randomInt > 80);
    } // isCritCheck
    
    /** Methode, falls der Kampf gewonnen wurde:
     * Erzeugt einen XP- und Goldwert abhängig vom Level des Spielers und dem Gegnertyp und fügt es dem Spieler hinzu.
     * Checkt, ob auch anderer Loot erbeutet wurde, generiert diesen und fügt ihn dem Spieler hinzu. 
     * @param p Player-Objekt des agierenden Spielers
     * @param pInv PlayerInventory-Objekt des Player-Objekts
     * @param e Player-Objekt des besiegten Spielers
     */
    private static void Victory(Player p, PlayerInventory pInv, Enemy e) {
        // genertiert XP, Gold und Loot
        int xp = ((p.getLevel() * 2) + Mathematik.getAbsoluteValue(e.getLevel(), 150));
        xp *= Math.round(e.getLootMultiplier());
        xp *= Mathematik.DynamicVarianceGenerator(20, 20);

        int gold = ((p.getLevel() * 2) + Mathematik.getAbsoluteValue(e.getLevel(), 100));
        gold *= Math.round(e.getLootMultiplier());
        gold *= Mathematik.DynamicVarianceGenerator(30, 30);

        boolean getLoot = ((Math.random()*101) + p.getLevel() > 100);

        int lootHolz = 0;
        int lootErz = 0;
        int lootEdelsteine = 0;

        if(getLoot) {
        lootHolz = (int) (Math.round(p.getLevel() * 1.0) + Mathematik.getAbsoluteValue(p.getLevel(), 400));
        lootEdelsteine *= e.getLootMultiplier();
        lootHolz *= Mathematik.DynamicVarianceGenerator(10, 10);        

        lootErz = (int) (Math.round(p.getLevel() * 0.5) + Mathematik.getAbsoluteValue(p.getLevel(), 600));
        lootErz *= e.getLootMultiplier();
        lootErz *= Mathematik.DynamicVarianceGenerator(20, 10);        

        lootEdelsteine = (int) (Math.round(p.getLevel() * 0.25) + Mathematik.getAbsoluteValue(p.getLevel(), 800));
        lootEdelsteine *= e.getLootMultiplier();
        lootEdelsteine *= Mathematik.DynamicVarianceGenerator(30, 10);   

        } // if getLoot

        // Text-Ausgabe
        String message = "Du erhälst\n" + xp + " XP.\n";
        message += gold + " Gold\n";
        message += lootHolz + " Holz\n";
        message += lootErz + " Erz\n";
        message += lootEdelsteine + " Edelsteine\n";

        Dialogue.Confirm(message);

        // addiert XP, Gold und Loot
        p.addCurrentXP(xp);
        pInv.addGold(gold);
        pInv.addHolz(lootHolz);    
        pInv.addErz(lootErz);
        pInv.addEdelsteine(lootEdelsteine);        
    } // Victory-Methode

    /** Methode, falls der Kampf verloren wurde:
     * Erzeugt einen XP-Wert und reduziert ihn um 90%.
     * @param p Player-Objekt des agierenden Spielers
     * @param pInv PlayerInventory-Objekt des Player-Objekts
     * @param enemy Player-Objekt des NPCs
     */
    private static void Defeat(Player p, PlayerInventory pInv, Enemy e) {
        // genertiert XP, aber nur 10% der ursprünglichen Menge
        int xp = ((p.getLevel() * 2) + Mathematik.getAbsoluteValue(e.getLevel(), 125));
        xp *= 0.1;
        xp *= Mathematik.DynamicVarianceGenerator(20, 20);
        
        // Text-Ausgabe       
        Dialogue.Confirm("Du erhälst\n" + xp + " XP.\n");

        // addiert XP
        p.addCurrentXP(xp);

    } // Victory-Methode

} // Kampf-Klasse
