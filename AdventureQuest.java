package AdventureOOP;

/**
 * Die Klasse für die Quests. Noch nicht vollständig implementiert.<br>
 * Es gibt verschiedene Schwierigkeiten, die unabhängig vom Spielerlevel Gold und XP generieren.<br>
 * Die Wahrscheinlichkeit eine Quest erfolgreich abzuschließen ist vom Spielerlevel abhängig.<br>
 * Bisher gibt es nur ein einziges Szenario: Erfolg oder Misserfolg ohne Konsequenzen.<br>
 * Wichtige Klassen:<br>
 * {@Adventure}
 */
public class AdventureQuest {

    /**
     * Die Hauptmethode zur Quest, hier werden alle relevanten Informationen abgerufen und verarbeitet.
     * @param p Player-Object
     * @param pInv zugehöriges PlayerInventory-Object
     * @param difficulty schwierigkeit der Quest (beeinflusst die Belohnung)
     */
    public static void Quest(Player p, PlayerInventory pInv, int difficulty) {

        int level = (int) Math.round((p.getLevel() * Mathematik.DynamicVarianceGenerator(30, 30)));
        int xp = 0;
        int gold = 0;
        String message = "";

        if(p.getEnergie() < 2) {
            Dialogue.Confirm("Leider hast du nicht genug Energie, um eine Quest anzunehmen, " + p.getPlayerName() + ".");
            return;
        } // if(Energie)
        
        p.subEnergie(2);

        if (level > difficulty) {
           
            gold = Mathematik.getAbsoluteValue(p.getLevel(), 50);
            gold += (difficulty + Mathematik.Randomizer(difficulty));
            gold = (int) (gold * (Mathematik.DynamicVarianceGenerator(40, 40)));
           
            xp = Mathematik.getAbsoluteValue(p.getLevel(), 175);
            xp += Mathematik.Randomizer(difficulty);
            xp = (int) (xp * (Mathematik.DynamicVarianceGenerator(20, 20)));

            message = "Sehr schön, du hast die Quest erfolgreich erledigt und bekommst dafür: \n\n";
            message += gold + " Gold\n";
            message += xp + " Erfahrung";

        } else {
            message = "Leider konntest du deine Quest nicht erfolgreich abschließen, " + p.getPlayerName() + ".\nDu erhälst keine Belohnung.";
        } // if-else

        Dialogue.Confirm(message);

        p.addCurrentXP(xp);
        pInv.addGold(gold);      

    } // Quest

} // Quest-Klasse
