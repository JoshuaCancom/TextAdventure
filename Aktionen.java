package AdventureOOP;

// import javax.naming.InvalidNameException;

/**
 * Enhält alle Grund-Aktionen des Spiels, die über das Hauptmenü der {@link Main}-Methode aufgerufen werden können.<br>
 * Verarbeitet alle relevanten Informationen und leitet sie ggfs. an andere Methoden weiter.<br>
 * Wichtige Klassen:<br>
 * {@link Main}<br>
 * {@link Adventure}<br>
 */
public class Aktionen { 

    /** Methode um Ausrüstungslevel zu erhöhen.
     * Berechnet zuerst den Soll-Wert und ruft den Ist-Wert ab um diesen zu vergleichen, danach wird das Level des ausgewählten Gegenstandes erhöht und der Soll-Wert wird vom Ist-Wert abgezogen.
     * @param p Player-Objekt des ausführendes Spielers
     * @param i PlayerInventory-Objekt des Player-Objekts
     */
    public static void Upgrade(Player p, PlayerInventory i) { 
        
        // Initialisierung und Abfrage
        int upgrade = 0;
        int oldLevel = 1;
        int kosten[] = {50, 5, 0, 0};
        int gold = i.getGold();
        int holz = i.getHolz();
        int erz = i.getErz();
        int edelsteine = i.getEdelsteine();
        boolean confirmChoice = false;
        String message = "";

        // Abfrage nach dem Gegenstand, Ausgabe der Kosten und Bestätigung
        while(!confirmChoice) {
            upgrade = Dialogue.Choice("Welche Ausrüstung möchtest du upgraden?", "Ausrüstung upgraden", new String[] {"Waffe (Lvl " + i.getWaffe() + ")", "Rüstung (Lvl " + i.getRuestung() + ")", "Zweitwaffe (Lvl " + i.getZweitwaffe() + ")", "Schuhe (Lvl " + i.getSchuhe() + ")", "Abbrechen"}, false);
            switch(upgrade) {
                case 0:
                oldLevel = i.getWaffe();
                kosten = i.getUpgradeKostenWaffe();
                message = "Das Upgraden deiner Waffe kostet:\n\n";
                    break;
                case 1:
                oldLevel = i.getRuestung();
                kosten = i.getUpgradeKostenRuestung();
                message = "Das Upgraden deiner Rüstung kostet:\n\n";
                    break;
                case 2:
                oldLevel = i.getZweitwaffe();
                kosten = i.getUpgradeKostenZweitwaffe();
                message = "Das Upgraden deiner Zweitwaffe kostet:\n\n";
                    break;
                case 3:
                oldLevel = i.getSchuhe();
                kosten = i.getUpgradeKostenSchuhe();
                message = "Das Upgraden deiner Schuhe kostet:\n\n";
                    break;
                case 4:
                    return;                    
            } // switch

            message += (
            gold + "/" + kosten[0] + " Gold\n"
            + holz + "/" + kosten[1] + " Holz\n"
            + erz + "/" + kosten[2] + " Erz\n"
            + edelsteine + "/" + kosten[3] + " Edelsteine\n"
            + "\nBist du sicher, dass du deine Ausrüstung upgraden möchtest?");

            confirmChoice = 0 == Dialogue.Choice(message, "Bestätigen", new String[] {"Ja", "Nein"}, false);
        } // while

        if(oldLevel >= 20) { // Exception wenn die Ausrüstung das maximale Level erreicht hat
            Dialogue.Confirm("Du hast das maximale Level deiner Ausrüstung bereits erreicht.");
            return;
        } // if

        // Vergleich Soll-Wert mit Ist-Wert (Kosten & Inventar)
       
        int funds[] = {gold, holz, erz, edelsteine};
        String fundsName[] = {"Gold", "Holz", "Erz", "Edelsteine"};

        boolean zurueck = false;
        message = "";
        for(int x = 0; x < kosten.length; x++) { // Überprüft, ob die Upgradekosten bezahlt werden können.
            if(kosten[x] > funds[x]) {
                message += "Leider hast du nicht genug " + fundsName[x] + ". Dir fehlen: " + (kosten[x] - funds[x]) + "\n";
                zurueck = true;
            } // if
        } // for   
        if(zurueck) { // Wirft den Spieler zurück und gibt ihm Informationen darüber, welche Gegenstände ihm fehlen.
            Dialogue.Confirm(message);
            Upgrade(p, i);
            return;
        } // if
        
        // Entfernt die Materialien in Höhe der Kosten bei erfolgreichem Update aus dem Inventar

        i.subGold(kosten[0]);
        i.subHolz(kosten[1]);
        i.subErz(kosten[2]);
        i.subEdelsteine(kosten[3]);
        message = "";
        switch(upgrade) { // 0 = Waffe, 1 = Rüstung, 2 = Zweitwaffe, 3 = Schuhe
            case 0:
            i.levelWaffe();
            message = "Glückwunsch, deine Waffe wurde verbessert. Sie ist nun auf Level " + (i.getWaffe()) + ".";
                break;
            case 1:
            i.levelRuestung();
            message = "Glückwunsch, deine Rüstung wurde verbessert. Sie ist nun auf Level " + (i.getRuestung()) + ".";
                break;
            case 2:
            i.levelZweitwaffe();
            message = "Glückwunsch, deine Zweitwaffe wurde verbessert. Sie ist nun auf Level " + (i.getZweitwaffe()) + ".";
                break;
            case 3:
            i.levelSchuhe();
            message = "Glückwunsch, deine Schuhe wurde verbessert. Sie ist nun auf Level " + (i.getSchuhe()) + ".";
                break;
        } // switch
        Dialogue.Confirm(message);
    } // Upgrade-Methode

    /** Methode um Attribute zu erhöhen, sollte nicht aufgerufen werden können, wenn die Skillpunkte unter 1 liegen (= 0 sind)
     * 
     * @param p Player-Objekt des nutzenden Spielers
     */
    public static void Skills(Player p) { 
        String message = "Welches Attribut möchtest du erhöhen? Du hast " + p.getSP() + " SKillpunkte";
        int choice = Dialogue.Choice(message, "Skillen", new String[] {"Gesundheit", "Staerke", "Geschicklichkeit", "Weisheit", "Verteidigung", "Abbrechen"}, false);
        if(p.getSP() < 1) {
            return; // Notfall-return, falls die Methode trotz 0 Skillpunkte aufgerufen wurde
        }
        switch(choice) {
            case 0:
            p.addMaxLebenspunkte(10);
            message = "Deine Lebenspunkte wurden erfolgreich erhöht.\n";
                break;
            case 1:
            p.addStaerke(1);
            message = "Deine Staerke wurden erfolgreich erhöht.\n";
                break;
            case 2:
            p.addGeschick(1);
            message = "Deine Geschicklichkeit wurden erfolgreich erhöht.\n";
                break;
            case 3:
            p.addWeisheit(1);
            message = "Deine Weisheit wurden erfolgreich erhöht.\n";
                break;
            case 4:
            p.addVerteidigung(1);
            message = "Deine Verteidigung wurden erfolgreich erhöht.\n";
                break;
            case 5:
                return;
        } // switch
        p.subSP(1); 
        Dialogue.Confirm(message);
        if(p.getSP() > 0) {
            Skills(p);
        } else {
            return;
        } // if-else
    } // Skill-Methode

    // Abenteuer
    /** Abenteuer:
     * Das Abenteuer findet in mehreren Phasen statt: eine Erkundung kostet Energie für jede Aktion innerhalb des Erkundens.
     * Für das Erkunden stehen einem mehrere Optionen zur Verfügung:
     * - Trainieren (gibt kein Gold, aber XP) -> Abhängig vom Spieler-Level
     * - Kämpfen (gibt Gold und XP, manchmal auch Ressourcen) -> skaliert mit Spieler-Level, hat aber einen Modifier der die Schwierigkeit beeinflusst
     * - Sammeln (gibt Ressourcen) -> Abhängig vom Spieler-Level
     * - Quests (wählt ein bestimmtes Szenario, gibt viel Gold und wenig XP, manchmal Ressourcen) -> skaliert NICHT mit Spieler-Level, es gibt aber verschiedene Schwierigkeitsgrade, aus denen man auswählen kann
     * - Dungeon (Late-Game Content) -> Zum Sammeln von Skillpunkten, skaliert mit Dungeon-Level 
     * 
     * @param p Player-Objekt des nutzenden Spielers
     * @param pInv PlayerInventory-Objekt des Player-Objektes
     */

    public static void generateAdventure(Player p, PlayerInventory pInv) {
        int choice = Dialogue.Choice("Du beschließt auf ein Abenteuer zu gehen. " + p.getPlayerName() + ", was möchtest du heute tun?"
                                            + "\nDu hast noch " + p.getEnergie() + " Energie."
                                            , "Auf Abenteuer gehen", new String[] {"Trainieren", "Kämpfen", "Sammeln", "Quests (ab Lvl 10)", "Dungeon (ab Lvl 50)", "Zurück"}, false);
        switch(choice) {
            case 0:
                p.subEnergie(1);
                Adventure.Training(p);
                break;
            
            case 1:
                p.subEnergie(1);
                Adventure.Fighting(p, pInv);
                break;

            case 2:
                Adventure.Sammeln(p, pInv);
                break;

            case 3:
                if(p.getLevel() < 10) {
                    Dialogue.Confirm("Leider ist dein Level zu niedrig," + p.getPlayerName() + ". Du bist mindestens Level 10 sein, um eine Quest anzunehmen.");
                    return;
                } // if(Level)
                if(p.getEnergie() < 2) {
                    Dialogue.Confirm("Du hast nicht gneug Energie, " + p.getPlayerName() + ". Für eine Quest benötigst du mindestens 2 Energie.");
                    return;
                } // if(Energie)
                Adventure.Quest(p, pInv);
                break;
            
            case 4:
                if(p.getLevel() < 50) {
                    Dialogue.Confirm("Leider ist dein Level zu niedrig, " + p.getPlayerName() + ". Du bist mindestens Level 50 sein, um den Dungeon betreten zu können.");
                    return;
                } // if(Level)
                if(p.getEnergie() < 3) {
                    Dialogue.Confirm("Du hast nicht gneug Energie, " + p.getPlayerName() + ". Für eine Quest benötigst du mindestens 3 Energie.");
                    return;
                } // if(Energie)
                // Adventure.Dungeon(p, pInv);
                break;

            case 5:
                return;
        } // switch (choice)
    } // generateAdventure

    /**
     * Ingame-Shop um gegen Gold Leistungen einzutauschen oder Gegenstände zu verkaufen.
     * Öffnet ein neues Untermenü.
     * @param p Player-Objekt
     * @param pInv zugehöriges PlayerInventory-Objekt
     */
    public static void shopChoice(Player p, PlayerInventory pInv) {
        int choice = Dialogue.Choice("Du wandelst durch die Hauptstraße der Stadt...\nWelchen Laden möchtest du betreten?", "Laden betreten" , new String[] {"Apotheke", "Marktplatz", "Inn", "Zurück"}, false);
        switch(choice) {
            case 0:
                shopApotheke(p, pInv);
                break;
            case 1:
                shopMarktplatz(p, pInv); 
                break;
            case 2:
                shopInn(p, pInv);
                break;
            case 3:
                return;
        } // switch

    } // shopChoice

    /**
     * Apotheke, um gegen Gold Heilung zu erhalten.
     * Öffnet ein neues Untermenü.
     * @param p Player-Object
     * @param pInv zugehöriges PlayerInventory-Object
     */
    private static void shopApotheke(Player p, PlayerInventory pInv) {
        int choice = Dialogue.Choice("Willkommen, Reisender. Wenn ich deine Wunden versorgen soll, sag mir Bescheid. (Leben: " + p.getCurrentLebenspunkte() + "/" + p.getMaxLebenspunkte() + ")", "Wunden versorgen", new String[] {"Gerne", "Jetzt nicht"}, false);
        if(choice == 1) {
            shopChoice(p, pInv);
            return;
        } // if
        int kostenKleineHeilung = (p.getMaxLebenspunkte() / 4);
        int kostenVollständigeHeilung =(p.getMaxLebenspunkte() * 2);
        choice = Dialogue.Choice("Nun gut, leg dich bitte auf die Trage hier... (Leben: " + p.getCurrentLebenspunkte() + "/" + p.getMaxLebenspunkte() + ")", "Wunden versorgen", new String[] {"kleine Heilung (" + kostenKleineHeilung + " Gold)", "vollständige Heilung (" + kostenVollständigeHeilung + " Gold)", "Doch nicht..."}, false);
        switch(choice) {
            case 0:
            if(pInv.getGold() < kostenKleineHeilung) {
                Dialogue.Confirm("Leider hast du nicht genug Gold.");
                shopChoice(p, pInv);   
                return;
            } else {
                Dialogue.Confirm("Dein Schmerz wird ein klein wenig gelindert und deine Wunden beginnen zu heilen...\nDu hast " + (p.getMaxLebenspunkte() / 10) + " Lebenspunkte dazuerhalten.");
                p.addCurrentLebenspunkte((p.getMaxLebenspunkte() / 10));
                pInv.subGold(kostenKleineHeilung);
            } //if-else
                break;
            case 1:
            if(pInv.getGold() < kostenVollständigeHeilung) {
                Dialogue.Confirm("Leider hast du nicht genug Gold.");
                shopApotheke(p, pInv); 
            } else {
                Dialogue.Confirm("All deine Wunden heilen vollständig und jeglicher Schmerz ist von dir genommen...\nDu bist wieder vollständig geheilt.");
                p.setCurrentLebenspunkteToMax(p.maxLebenspunkte);
                pInv.subGold(kostenVollständigeHeilung);
            } //if-else
                break;
            case 2:
            shopChoice(p, pInv);
                return;
        } // switch
    } // Apotheke

    /**
     * Marktplatz, um Gegenstände zu kaufen oder verkaufen, Preise sind unterschiedlich je nachdem ob man An- oder Verkauft.
     * Öffnen verschiedene Untermenüs zur Auswahl von Produkt und Anzahl  --> Änderung geplant: statt vorgegebenen Mengen soll eine Dialoge.UserInputString-Abfrage stattfinden.
     * @param p Player-Object
     * @param pInv zugehöriges PlayerInventory-Object
     */
    private static void shopMarktplatz(Player p, PlayerInventory pInv) {
        int choice = Dialogue.Choice("Willkommen, willkommen. Ich biete nur die feinsten Waren an! Möchtest du etwas Kaufen oder Verkaufen?", "Marktplatz", new String[] {"Kaufen", "Verkaufen", "Zurück"}, false);
        double preisMultiplier = 1.0;
        int preis = 0;
        String buyOrSell = "";
        switch(choice) {
            case 0:
            choice = Dialogue.Choice("Sieh dich in Ruhe um...", "Marktplatz", new String[] {"Holz", "Erz", "Edelsteine", "Doch nicht..."}, false);
            buyOrSell = "Kaufen";
            preisMultiplier = 1.5;
                break;
            case 1:
            choice = Dialogue.Choice("Was möchtest du verkaufen?", "Markplatz", new String[] {"Holz", "Erz", "Edelsteine", "Doch nicht..."}, false);
            buyOrSell = "Verkaufen";
            preisMultiplier = 1.0;
                break;
            case 2:
            shopChoice(p, pInv);
                return;
        } // switch 
        String item = "";
        int numberMax = 0;
        switch(choice) {
            case 0:
            preis = (int) (26.0 * preisMultiplier);
            item = "Holz";
            numberMax = pInv.getHolz();
                break;
            case 1:
            preis = (int) (52.0 * preisMultiplier);
            item = "Erz";
            numberMax = pInv.getErz();
                break;
            case 2:
            preis = (int) (94.0 * preisMultiplier);
            item = "Edelsteine";
            numberMax = pInv.getEdelsteine();
                break;
            case 3:
            shopMarktplatz(p, pInv);
                return;
        } // switch
        int preis1 = preis;
        int preis10 = preis * 10;
        int preis100 = preis * 100;       
        if(buyOrSell.equals("Kaufen")) {
            numberMax = pInv.getGold() / preis;
        } // if      
        int preisMax = numberMax * preis;
        int itemCount = 1;
        choice = Dialogue.Choice("Wie viele davon (" + item + ") möchtest du " + buyOrSell + "?", buyOrSell, new String[] {"1 (" + preis1 + " Gold)", "10 (" + preis10 + " Gold)", "100 (" + preis100 + " Gold)", "MAX: " + numberMax + " (" + preisMax + " Gold)", "Zurück"}, false);
        switch(choice) {
            case 0:
            preis = preis1;
            itemCount = 1;
               break;
            case 1:
            preis = preis10;
            itemCount = 10; 
                break;
            case 2:
            preis = preis100;
            itemCount = 100;
                break;
            case 3:
            preis = preisMax;
            itemCount = numberMax;
                break;
            case 4:
            shopMarktplatz(p, pInv);
                return;
        } // switch
        switch(item) {
            case "Holz":
            if(buyOrSell.equals("Kaufen")) {
                pInv.addHolz(itemCount);
                pInv.subGold(preis);
            } else {
                pInv.subHolz(itemCount);
                pInv.addGold(preis);
            } // if-else
                break;
            case "Erz":
            if(buyOrSell.equals("Kaufen")) {
                pInv.addErz(itemCount);
                pInv.subGold(preis);
            } else {
                pInv.subErz(itemCount);
                pInv.addGold(preis);
            } // if-else
                break;
            case "Edelsteine":
            if(buyOrSell.equals("Kaufen")) {
                pInv.addEdelsteine(itemCount);
                pInv.subGold(preis);
            } else {
                pInv.subEdelsteine(itemCount);
                pInv.addGold(preis);
            } // if-else
                break;
        } // switch
        Dialogue.Confirm("Deine Transaktion war erfolgreich!");
        shopMarktplatz(p, pInv);
    } // Tauschbörse

    /**
     * Inn, um Energie gegen einen Goldbetrag wieder zu regenerieren. || HINZUFÜGEN: Kosten steigen mit aktivem Spielerlevel 
     * @param p Player-Object
     * @param pInv zugehöriges PlayerInventory-Object
     */
    private static void shopInn(Player p, PlayerInventory pInv) {

        boolean repeat = true;
        int choice = 0;
        while(repeat) {
            String message = "";
            choice = Dialogue.Choice("Brauchst du eine Erholung? Kannst dir ein Zimmer nehmen, für ein paar Stunden oder ne ganze Nacht, ganz wie du magst.", "Inn", new String[] {"ein paar Stunden", "die ganze Nacht", "lieber gar nicht"}, false);
            switch(choice) {
                case 0:
                message = "Für ein paar Stunden kostet das Bett 300 Gold, dafür regenerierst du die Hälfte deiner Energie.";                
                    break;
                case 1:
                message = "Wenn du die ganze Nacht hier verbringst kostet dich das 500 Gold, dafür bist du danach vollständig erholt.";
                    break;  
                case 2: shopChoice(p, pInv);
                    return;
            } // switch
            repeat = 1 == Dialogue.Choice(message, "Inn", new String[] {"Okay!", "Nein danke..."}, false);
        } // while
        if(choice == 0) {
            if(pInv.getGold() < 300) {
                Dialogue.Confirm("Leider hast du nicht genug Gold. Du brauchst mindestens 300 Gold.");
                shopInn(p, pInv);  
                return;
            } // if
            Dialogue.Confirm("Du ruhst dich ein paar Stunden raus... (+ 5 Energie)");
            pInv.subGold(300);
            p.addEnergie(5);
        } // if
        if(choice == 1) {
            if(pInv.getGold() < 500) {
                Dialogue.Confirm("Leider hast du nicht genug Gold. Du brauchst mindestens 500 Gold.");
                shopInn(p, pInv);  
                return;
            } // if
            Dialogue.Confirm("Du ruhst dich ein paar Stunden raus... (+ 5 Energie)");
            pInv.subGold(500);
            p.addEnergie(10);
        } // if

    } // Inn

} // Aktionen-Klasse