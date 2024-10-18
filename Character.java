package AdventureOOP;
import java.io.*;
// import java.util.*;
import java.util.Random;

// import javax.swing.JOptionPane;

// ***** C H A R A C T E R - K L A S S E *****
/**
 * Hat keinen Konstruktor, da Objekte einer Kind-Klasse angehören müssen um zwischen Spieler und Gegner zu unterscheiden.<br>
 * Implementiert Serializable, damit die Spielerdaten gespeichert und erneut abgerufen werden können.<br>
 * Der Character-Klasse gehören zwei Kind-Klassen an: Player, Enemy<br>
 * Character = Spieler UND Gegner<br>
 * Player = Spieler<br>
 * Enemy = Gegner<br>
 * Setter sind z.T. unterteilt in adder und sibstractor um die Aufrufung einfacher zu gestalten und Vorzeichenfehler zu vermeiden<br>
 * add fügen den (WERT) hinzu<br>
 * sub reduzieren die Variable um den (WERT)<br>
 * Relevante Klassen:<br>
 * {@link Main}<br>
 * {@link Player}<br>
 * {@link Enemy}<br>
 * {@link PlayerInventory}<br>
 */
public class Character implements Serializable {
    private static final long serialVersionUID = 1L;

    int level = 1;
    int currentLebenspunkte = 100;
    int verteidigung = 5;
    int currentXP = 0; // steht beim Enemy für den xp-multiplier für Kämpfe

    // ***** ↓ ↓ ↓ ↓ ↓ ***** G E T T E R / C H A R A C T E R ***** ↓ ↓ ↓ ↓ ↓ *****

    /**
     * @return Character-Level
     */
    public int getLevel() {
        return this.level;
    } // getLevel

    /**
     * @return aktuelle Lebenspunkte
     */
    public int getCurrentLebenspunkte() {
        return this.currentLebenspunkte;
    } // getCurrentLebenspunkte

    /**
     * @return Character-Verteidigung
     */
    public int getVerteidigung() {
        return this.verteidigung;
    } // getVerteidigung

    /**
     * @return aktuelle XP
     */
    public int getCurrentXP() {
        return this.currentXP;
    } // getCurrentXP

    /** getBeweglichkeit wird von der Player- und Enemyklasse unterschiedlich interpretiert
     * @return für die Player-Klasse den Geschicklichkeitswert und für die Enemy-Klasse den Ausweichwert
     */
    public int getBeweglichkeit() { // notwendig, da "AdventureKampf.isHit darauf zugreifen muss"; Player-Klasse: Geschicklichkeit; Enemy-Klasse: Ausweichwert
        return 0;
    }    
    // ***** ↑ ↑ ↑ ↑ ↑ ***** G E T T E R / C H A R A C T E R ***** ↑ ↑ ↑ ↑ ↑ *****
    

    // ***** ↓ ↓ ↓ ↓ ↓ ***** S E T T E R / C H A R A C T E R ***** ↓ ↓ ↓ ↓ ↓ *****
    
    /**
     * Fügt ein Level zum Character hinzu, wird von Player- und Enemyklasse unterschiedlich interpretiert.
     * Die Playerklasse erweitert die addLevel-Methode um viele Funktionen siehe: {@link Player#addLevel()} 
     */
    protected void addLevel() {
        this.level++;
    } // addLevel

    /**
     * verringert das Level um 1.
     * Aktuell kein Nutzen.
     */
    protected void subLevel() {
        this.level--;
    } // subLevel

    /**
     * setzt die aktuellen Lebenspuntke aufs Maximum
     * @param lebenspunkte hier sollte immer die Variable maxLebenspunkte angegeben werden (am besten via {@link Player#getMaxLebenspunkte()}
     */
    protected void setCurrentLebenspunkteToMax(int lebenspunkte) {
        this.currentLebenspunkte = lebenspunkte;
    } // setCurrentLebenspunkteToMax

    /**
     * Erhöht die aktuellen Lebenspunkte um einen WERT.
     * Wird von der Player-Klasse überschrieben um zu kontrollieren, ob sie über den maxLebenspunkten der Player-Klasse liegen.
     * @param add der WERT um welchen die aktuellen Lebenspunkte erhöht werden.
     */
    public void addCurrentLebenspunkte(int add) {
        this.currentLebenspunkte += add;
    } // addCurrentLebenspunkte

    /**
     * Senkt die aktuellen Lebenspunkte um einen WERT und kontrolliert, ob diese unter 0 gesenkt werden.
     * Falls die aktuellen Lebenspunkte unter 0 fallen werden sie = 0 gesetzt.
     * @param sub der WERT um welchen die aktuellen Lebenspunkte gesenkt werden.
     */
    public void subCurrentLebenspunkte(int sub) {
        this.currentLebenspunkte -= sub;
        if(currentLebenspunkte < 0) {
            currentLebenspunkte = 0;
        } // if
    } // subCurrentLebenspunkte

    /**
     * Erhöht die Verteidigung um einen WERT.
     * @param add der WERT, um den die Verteidigung erhöht wird.
     */
    protected void addVerteidigung(int add) {
        this.verteidigung += add;
    } // addVerteidigung

    /** 
     * Senkt die Verteidigung um einen WERT.
     * Aktuell kein Nutzen.
     * @param sub der WERT, um den die Verteidigung verringert wird.
     */
    protected void subVerteidigung(int sub) {
        this.verteidigung -= sub;
    } // subVerteidigung
    
    /**
     * Fügt den aktuellen Erfahrungspunkten einen WERT hinzu. 
     * Wird durch Player-Klasse um die Abfrage erweitert, ob currentXP das Limit von maxXP erreicht haben, um ein Level-Up durchzufürehn (siehe {@link Player#addCurrentXP()}).
     * Steht in der Enemy-Klasse für den XP-Multiplikator im Kampf.
     * @param add der WERT, der den aktuellen Erfahrungspunkten hinzugefügt wird.
     */
    protected void addCurrentXP(int add) {
        this.currentXP += add;
    }
    protected void subCurrentXP(int sub) {
        this.currentXP -= sub;
    } // ***** ↑ ↑ ↑ ↑ ↑ ***** S E T T E R / C H A R A C T E R ***** ↑ ↑ ↑ ↑ ↑ *****
} // ***** C H A R A C T E R - K L A S S E *****

// ***** ***** ***** P L A Y E R - K L A S S E ***** ***** *****
/**
 * Erstellt ein Objekt der Player-Klasse, ein spielbarer Charakter mit allen Werten. Um einen Charakter zu erstellen wird ein Name und eine Startklasse benötigt.
 */
class Player extends Character {

    int maxLebenspunkte = 100;
    int staerke = 5;
    int geschicklichkeit = 5;
    int weisheit = 5;
    int maxXP = 10;
    int sp = 2;
    int energie = 10;

    String playerName;
    int playerKlasse;   // 0 = Soldat, 1 = Waldlaeufer, 2 = Lehrling, 3 = Krieger, 4 = Ritter, 5 = Jaeger, 6 = Dieb, 7 = Magier, 8 = Alchemist, 9 = Feldherr, 10 = Tempelritter, 11 = Scharfschuetze, 12 = Assassine, 13 = Zaubermeister, 14 = Weiser
    String playerKlasseName[] = {"Soldat", "Waldlaeufer", "Lehrling", "Krieger", "Ritter", "Jaeger", "Dieb", "Magier", "Alchemist", "Feldherr", "Tempelritter", "Scharfschuetze", "Assassine", "Zaubermeister", "Weiser"};
    int playerKlasseWachstum[][] = { // 0 = Lebenspunkte, 1 = Staerke, 2 = Geschicklichkeit, 3 = Weisheit, 4 = Verteidigung || SP pro Level: 6 / 10 / 16
        {10, 2, 0, 0, 2}, // Soldat
        {10, 1, 2, 0, 1}, // Waldlaeufer
        {10, 0, 0, 4, 0}, // Lehrling
        {15, 3, 1, 0, 3}, // Krieger
        {15, 3, 2, 0, 2}, // Ritter
        {10, 3, 4, 0, 1}, // Jaeger
        {15, 2, 3, 0, 2}, // Dieb
        {10, 0, 1, 6, 1}, // Magier
        {15, 0, 2, 4, 1}, // Alchemist
        {20, 5, 3, 0, 4}, // Feldherr
        {25, 4, 2, 0, 5}, // Temeplritter
        {15, 5, 6, 0, 2}, // Scharfschuetze
        {20, 3, 8, 0, 1}, // Assassine
        {10, 0, 3, 9, 2}, // Zaubermeister
        {20, 0, 3, 6, 3} // Weiser
    }; // int[] playerKlassenWachstum

    // ***** ↓ ↓ ↓ ↓ ↓ ***** K O N S T R U K T O R E N / P L A Y E R ***** ↓ ↓ ↓ ↓ ↓ *****
    /**
     * Erstellt ein Player-Object mit einem Namen und einer Klasse.
     * Alle anderen Werte sind standarisiert und immer gleich.
     * Der Klassenname ist abhängig von der Klasse.
     * @param playerName freie String-Eingabe vom Nutzer über JOptionPane, aktuell keine Einschränkungen
     * @param playerKlasse int-Eingabe vom Nutzer über JOptionPane mit Auswahlmöglichkeit
     */
    public Player(String playerName, int playerKlasse) {
        this.playerName = playerName;
        this.playerKlasse = playerKlasse;
    } 
    // ***** ↑ ↑ ↑ ↑ ↑ ***** K O N S T R U K T O R E N / P L A Y E R ***** ↑ ↑ ↑ ↑ ↑ *****


    // ***** ↓ ↓ ↓ ↓ ↓ ***** G E T T E R / P L A Y E R ***** ↓ ↓ ↓ ↓ ↓ *****
    /**
     * @return Name des Spielers
     */
    public String getPlayerName() {
        return this.playerName; 
    } //getPlayerName

    /**
     * @return maximale Lebenspunkte des Spielers
     */
    public int getMaxLebenspunkte() {
        return this.maxLebenspunkte;
    } // getMaxLebenspunkte

    /**
     * @return Stäke-Attribut des Spielers
     */
    public int getStaerke() {
        return this.staerke;
    } // getStaerke

    /**
     * @return Geschicklichkeits-Attribut des Spielers
     */
    public int getGeschick() {
        return this.geschicklichkeit;
    } // getGeschick

    /**
     * Ersetzt {@link Character#getBeweglichkeit()}.
     * @return Geschicklichkeits-Attribut des Spielers
     */
    @Override
    public int getBeweglichkeit() {
        return this.geschicklichkeit;
    } // getBeweglichkeit

    /**
     * @return Weisheits-Attribut des Spielers
     */
    public int getWeisheit() {
        return this.weisheit;
    } // getWeisheit

    /**
     * @return Verteidigungswert des Spielers
     */
    public int getVerteidigung() {
        return this.verteidigung;
    } //getVerteidigung

    /**
     * @return die aktuellen Skillpunkte des Spielers
     */
    public int getSP() {
        return this.sp;
    } // getSP

    /**
     * @return die maximalen Erfahrungspunkte (die benötigten Erfahrunfspunkte für ein Level-Up)
     */
    public int getMaxXP() {
        return this.maxXP;
    } // getMaxXP

    /**
     * @return die aktuelle Energie des Spielers (Maximalwert ist 10)
     */
    public int getEnergie() {
        return this.energie;
    } // getEnergie

    /**
     * @return die Playerklasse als Integer-Wert
     */
    public int getPlayerKlasse() {
        return this.playerKlasse;
    } // getPlayerKlasse 

    /**
     * @return die Playerklasse als String-Wert (Name)
     */
    public String getPlayerKlasseName() {
        return this.playerKlasseName[this.playerKlasse];
    } // getPlayerKlasseName

    /**
     * Abrufen aller relevanten Informationen der Player-Klasse
     * @return als einzelner String untereinander alle Informationen für eine Status-Ausgabe
     */
    public String getAllPlayer() {
        String message = "Name: " + getPlayerName() + "\nKlasse: " + this.playerKlasseName[this.playerKlasse] + "\n\nStatus: ";
        message += "\nLevel: " + getLevel();
        message += "\nErfahrung: " + getCurrentXP() + "/" + getMaxXP();
        message += "\nLeben: " + getCurrentLebenspunkte() + "/" + getMaxLebenspunkte();
        message += "\nStaerke: " + getStaerke();
        message += "\nGeschicklichkeit: " + getGeschick();
        message += "\nWeisheit: " + getWeisheit();
        message += "\nVerteidigung: " + getVerteidigung();
        message += "\nSkillpunkte: " + getSP();
        message += "\nEnergie: " + getEnergie();       
        return message;
    } // getAllPlayer
    // ***** ↑ ↑ ↑ ↑ ↑ ***** G E T T E R / P L A Y E R ***** ↑ ↑ ↑ ↑ ↑ *****

    // ***** ↓ ↓ ↓ ↓ ↓ ***** S E T T E R / P L A Y E R ***** ↓ ↓ ↓ ↓ ↓ *****
    /**
     * Setzt den Integer-Wert der playerKlasse. Der Integer-Wert bestimmt die Ausgabe von {@link #getPlayerName()} und den Wachstumsfakor bei einem Level-Up {@link #playerKlasseWachstum}.
     * @param playerKlasse der neue Integer-Wert für die playerKlasse (ersetzt den alten Wert)
     */
    public void setPlayerKlasse (int playerKlasse) {
        this.playerKlasse = playerKlasse;
    } // serPlayerKlasse

    /**
     * Setzt den Namen des Spielers. Dieser wird als Anrede während Testausgaben verwenden sowie als Speichername für die Savedateien.
     * Wird i.d.R. nur einmal zu Beginn eines neuen Saves abgefragt und erstellt.
     * @param playerName der neue Name des Spielers (ersetzt den alten Wert)
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    } // setPlayerName

    /**
     * Fügt den einen WERT der aktuellen Energie hinzu, überprüft dabei ob das Ergebnis über 10 liegt und korrigiert dementsprechend.
     * @param energie der WERT, welcher der aktuellen Energie hinzugefügt wird
     */
    public void addEnergie(int energie) {
        if(this.energie < 10) {
            this.energie += energie;
            if(this.energie > 10) {
                this.energie = 10;
            } // if (energie > 10)       
            System.out.println("Energie wurde aufgefüllt.");            
        } // if (energie < 10)
    } // addEnergie

    /**
     * Reduziert die aktuelle Energie um einen WERT, überprüft dabei ob das Ergebnis unter 0 liegt und korrigiert dementsprechend.
     * @param energie der WERT, um welchen die aktuelle Energie reduziert wird
     */
    public void subEnergie(int energie) {
        if(this.energie > 0) {
            this.energie -= energie;
            if(this.energie < 0) {
                this.energie = 0;
            } // if (energie < 0)
            System.out.println("Energie wurde verbraucht.");
        } // if (energie > 0)
    } // subEnergie

    /**
     * Überschreibt {@link Character#addCurrentLebenspunkte(int)} und fügt eine Kontrolle hinzu, dass die aktuellen Lebenspunkte nicht über den maximalen Lebenspunkten liegt.
     * @param lebenspunkte der WERT, um den die aktuellen Lebenspunkte erhöht werden
     */
    @Override // Check für Integrität des Lebenspunkte-Wertes
    public void addCurrentLebenspunkte(int lebenspunkte) {

        super.addCurrentLebenspunkte(lebenspunkte);
        if(this.currentLebenspunkte > this.maxLebenspunkte) {
            this.currentLebenspunkte =  this.maxLebenspunkte;
        } // if > maxLebenspunkte
    } // addCurrentLebenspunkte

    /**
     * Erhöht die maximalen Lebenspunkte um einen WERT. Setzt außerdem die aktuellen Lebenspunkte = den neuen maximalen Lebenspunkten.
     * @param lebenspunkte der WERT, um den die maximalen Lebenspunkte erhöht werden
     */
    public void addMaxLebenspunkte(int lebenspunkte) {
        this.maxLebenspunkte += lebenspunkte;
        setCurrentLebenspunkteToMax(this.maxLebenspunkte);
    } // addMaxLebenspunkte

    /**
     * Reduziert die maximalen Lebenspunkte um einen WERT. Setzt außerdem die aktuellen Lebenspunke = den neuen maximalen Lebenspunkten.
     * @param lebenspunkte der WERT, um den die maximalen Lebenspunkte reduziert werden
     */
    public void subMaxLebenspunkte(int lebenspunkte) {
        this.maxLebenspunkte += lebenspunkte;
        setCurrentLebenspunkteToMax(this.maxLebenspunkte);
    } // subMaxLebenspunke

    /**
     * Erhöht das Stärke-Attribut des Spielers um einen WERT
     * @param staerke der WERT, um den das Stärke-Attribut erhöht wird
     */
    public void addStaerke(int staerke) {
        this.staerke += staerke;
    } // addStaerke

    /**
     * Erhöht das Geschicklichkeits-Attribut des Spielers um einen WERT
     * @param geschick der WERT, um den das Geschicklichkeits-Attribut erhöht wird
     */
    public void addGeschick(int geschick) {
        this.geschicklichkeit += geschick;
    } // addGeschick

    /**
     * Erhöht das Weisheit-Attribut des Spielers um einen WERT
     * @param weisheit der WERT, um den das Weisheit-Attribut erhöht wird
     */
    public void addWeisheit(int weisheit) {
        this.weisheit += weisheit;
    } // addWeisheit

    /**
     * Erhöht die Skillpunkte des Spielers um einen WERT
     * @param sp der WERT, um den die Skillpunkte erhöht werden
     */
    public void addSP(int sp) {
        this.sp += sp;
    } // addSP

    /**
     * Verringert die Skillpunkte des Spielers um einen WERT
     * @param sp der WERT, um den die SKillpunkte verringert werden
     */
    public void subSP(int sp) {
        this.sp -= sp;
    } // subSP

    /**
     * Überschreibt {@link Character#addCurrentXP(int)} und erweitert die Funktion um einen Vergleich mit den maximalen XP.
     * Wenn {@link Character#currentXP} > {@link #maxXP} erfolgt ein {@link Player#addLevel()} und eine Anpassung der maxXP {@link #setMaxXP()}
     */
    @Override
    public void addCurrentXP(int xp) {
        super.addCurrentXP(xp);
        while(this.currentXP >= this.maxXP) { // Levelerhöhung, falls die XP gleich oder größer der XPMax sind.
            addLevel(); 
            this.currentXP -= this.maxXP;   // Anpassung der aktuellen XP und der benötigten XP für das nächste Level-Up
            setMaxXP();
        } // while xp > maxXP
    } // addCurrentXP
   
    /**
     * Passt die maximalen XP (= benötigte XP für ein Level-Up) nach einer bestimmten Wachstumsformel an.
     */
    private void setMaxXP() {
        // this.maxXP = (int) ((this.maxXP * ((this.level/100) + 1.0)) + (this.level * 8));      // Anpassung der maximalen XP.
        this.maxXP += ((level * 2) + (Mathematik.getAbsoluteValue(this.level, 85)));
    }
    
    /**
     * Fügt ein Level hinzu. Überprüft dabei ob gewisse Levelgrenzen erreicht wurden und beendet dementsprechend die Methode oder ruft weitere Methoden auf.
     * Levelgrenzen sind:
     * 19 & 49 - die zweite bzw. dritte Klasse wird gewählt {@link #setNeueKlasse()}
     * 100 - beendet die Methode, da dies das maximale Level ist
     * Erhöht außerdem die betroffenen Attribute des Spielers anhängig von {@link #playerKlasse} und {@link #playerKlasseWachstum}.
     */
    @Override
    protected void addLevel() {       

        Player temp = new Player("Temp", 0); 
        this.playerKlasseWachstum = temp.playerKlasseWachstum; // Überschreibt veraltete Array-Initialisierungen
    
        if (this.level == 100) {
            return;
        } // if (level = 100)

        Dialogue.Confirm("Glückwunsch, " + this.playerName + ", du hast ein Level-Up gemacht! Du wirst stärker und erhälst 2 SP.");
    
        if (this.level == 19 || this.level == 49) { // 2. oder 3. Klasse wählen
            setNeueKlasse();
            Dialogue.Confirm("Herzlichen Glückwunsch, " + getPlayerName() + ". Deine Klasse ist ab sofort: " + (this.playerKlasseName[this.playerKlasse]));
        } // if Klassenwechsel

        // Erhöhung des Levels und aller Attribute
        super.addLevel();
        addEnergie(2);
        addMaxLebenspunkte(playerKlasseWachstum[playerKlasse][0]);
        addStaerke(playerKlasseWachstum[playerKlasse][1]);
        addGeschick(playerKlasseWachstum[playerKlasse][2]);
        addWeisheit(playerKlasseWachstum[playerKlasse][3]);
        addVerteidigung(playerKlasseWachstum[playerKlasse][4]);
        addSP(2);
    } // ***** ↑ ↑ ↑ ↑ ↑ ***** S E T T E R / P L A Y E R ***** ↑ ↑ ↑ ↑ ↑ *****

    // ***** ***** ***** N E U E   K L A S S E   W Ä H L E N ***** ***** *****    
    /**
     * Bei den Levelgrenzen von 19 auf 20 und 49 auf 50 wird eine neue Klasse (Integer-Wert) gewählt {@link #playerKlasse}. 
     * Aauswahl erfolt über JOptionPane mit Usereingabe, die Auswahl ist abhängig von der alten Klasse.
     */    
    private void setNeueKlasse() { // 0 = Soldat, 1 = Waldlaeufer, 2 = Lehrling, 3 = Krieger, 4 = Ritter, 5 = Jaeger, 6 = Dieb, 7 = Magier, 8 = Alchemist, 9 = Feldherr, 10 = Tempelritter, 11 = Scharfschuetze, 12 = Assassine, 13 = Zaubermeister, 14 = Weiser
        String[] neueKlasse = {"0", "0"};
        String[] neueKlasseBeschreibung = {"0", "0"};
        int neueKlasseAdd = 0;
        switch(this.playerKlasse) {
            case 0:
                neueKlasse[0] = "Krieger";
                neueKlasseBeschreibung[0] = "Krieger sind mächtige Kämpfer, die stark im Angriff und in der Verteidigung sind.";
                neueKlasse[1] = "Ritter";
                neueKlasseBeschreibung[1] = "Ritter sind wendige Kämpfer, die schnelle Reflexe und harte Angriffe vorweisen können.";
                neueKlasseAdd = 3;
                break;
            case 1:
                neueKlasse[0] = "Jaeger";
                neueKlasseBeschreibung[0] = "Jaeger sind unglaublich geschickte Kämpfer, die aus dem verborgenen großen Schaden anrichten können.";
                neueKlasse[1] = "Dieb";
                neueKlasseBeschreibung[1] = "Diebe bleiben im Verborgenen, aber ihre Erfahrung zwischen den Straßen haben ihnen ein gutes Durchhaltevermögen gegeben.";
                neueKlasseAdd = 5;
                break;
            case 2:
                neueKlasse[0] = "Magier";
                neueKlasseBeschreibung[0] = "Magier sind sehr mächtige Zauberer, ihre Angriffsmacht ist unübertroffen, allerdings macht sie das auch verletzlich.";
                neueKlasse[1] = "Alchemist";
                neueKlasseBeschreibung[1] = "Alchemisten haben sich der Wissenschaft verflichtet und sind kluge Kampfstrategen. Ihre Attribute sind relativ ausgeglichen.";
                neueKlasseAdd = 7;
                break;
            case 3:
            case 4:
                neueKlasse[0] = "Feldherr";
                neueKlasseBeschreibung[0] = "Feldherren sind äußert gefährlich, da sie sowohl eine große Stärke als auch Wendigkeit vorweisen.";
                neueKlasse[1] = "Tempelritter";
                neueKlasseBeschreibung[1] = "Tempelritter sind wie menschliche Mauern in Rüstung. Kaum eine Waffe kann ihre Verteidigung durchdringen.";
                neueKlasseAdd = 9;
                break;
            case 5:
            case 6:
                neueKlasse[0] = "Scharfschuetze";
                neueKlasseBeschreibung[0] = "Scharfschützen treffen immer ins Schwarze und richten immensen Schaden an, bevor sie wieder im Nebel verschwinden";
                neueKlasse[1] = "Assassine";
                neueKlasseBeschreibung[1] = "Assassinen töten ihre Opfer, bevor diese überhaupt wissen, wie ihnen geschiet. Keiner hat je das Gesicht eines Assassinen gesehen und überlebt.";
                neueKlasseAdd = 11;
                break;
            case 7:
            case 8:
                neueKlasse[0] = "Zaubermeister";
                neueKlasseBeschreibung[0] = "Nichts hält ihre mächtigen Zerstörungszauber auf, aber ihre Verteidigung ist quasi nicht vorhanden.";
                neueKlasse[1] = "Weiser";
                neueKlasseBeschreibung[1] = "Weise sind sehr ausgeglichene Kämpfer, die sich auf magische Angriffe spezialisiert haben. Sie sind für jede Begegnung gewappnet.";
                neueKlasseAdd = 13;
                break;
        } // switch
        boolean repeat = true;
        int neueKlasseChoice = 0;
        while(repeat) {
            neueKlasseChoice = Dialogue.Choice("Du hast ausreichend Erfahrung gesammelt, um dich weiterzuentwickeln. Du kannst nun eine der folgenden Klassen wählen, " + this.playerName + ":", "Neue Klasse", neueKlasse, false);
            repeat = 1 == Dialogue.Choice(neueKlasseBeschreibung[neueKlasseChoice] + "\n\nBis du sicher?", "Klasse bestätigen?", new String[] {"Ja", "Nein"}, false);
        } // while
        this.playerKlasse = neueKlasseChoice + neueKlasseAdd;
    } // ***** ***** ***** N E U E   K L A S S E   W Ä H L E N ***** ***** *****
} // ***** ***** ***** P L A Y E R - K L A S S E ***** ***** *****

// ***** ***** ***** E N E M Y - K L A S S E ***** ***** *****
/**
 * Erstellt ein Objekt der Enemy-Klasse, einen nicht spielbaren Charakter, der als Gegner fungiert. Werte sind abhängig vom Level eines Player-Objektes sowie einer zufälligen Schwierigkeit.
 * Die Gegner werden zufallisbasiert auf diesen Abhängigkeiten erstellt und können unterschiedlichen Arten (Name) angehören und Attribute entwickeln.
 */
class Enemy extends Character {

int angriff = 5;
int ausweichwert = 5;

double lootMultiplier;
String enemyName;
int enemyKlasse;        
String enemyKlasseName[] = {"Ratte", "Krabbe", "Schleim", "Goblin", "Bandit", "Skelett", "Wolf", "Zombie", "Harpie", "Golem", "Dämon", "Lindwurm", "Chimären", "Lich", "Vampir", "Hydra", "Behemoth", "Ifrit", "Shiva", "Drache"};

// Das Wachstum von Gegnern funktioniert ohne die Attribut-Unterteilung von Staerke, Geschick und Weisheit. Gegner haben nur "Leben", "Angriff (=Staerke)" und "Verteidigung" plus einen Loot-Multiplier
int enemyKlassenWachstum[][] = { // 0 = Leben, 1 = Angriff, 2 = Ausweichwert 3 = Verteidigung, 4 = Loot-Multiplier
    {3, 1, 1, 1, 5}, // Ratte
    {3, 2, 1, 1, 6}, // Krabbe
    {6, 2, 1, 1, 7}, // Schleim
    {6, 3, 1, 1, 8}, // Goblin
    {6, 3, 2, 1, 9}, // Bandit
    {6, 3, 2, 2, 10}, // Skelett
    {6, 4, 2, 2, 11}, // Wolf
    {9, 4, 2, 2, 12}, // Zombie
    {9, 4, 3, 2, 13}, // Harpie
    {12, 4, 3, 2, 14}, // Golem
    {12, 5, 4, 2, 15}, // Dämon
    {15, 6, 4, 2, 16}, // Lindwurm
    {18, 7, 4, 2, 17}, // Chimäre
    {18, 8, 5, 2, 18}, // Lich
    {18, 9, 6, 2, 19}, // Vampir
    {24, 9, 6, 3, 20}, // Hydra
    {27, 10, 6, 4, 21}, // Behemoth
    {33, 11, 6, 4, 22}, // Ifrit
    {33, 9, 6, 6, 22}, // Shiva
    {39, 12, 7, 6, 25}, // Drache
}; // int[] enemyKlassenWachstum
            
// ***** ↓ ↓ ↓ ↓ ↓ ***** K O N S T R U K T O R E N / E N E M Y ***** ↓ ↓ ↓ ↓ ↓ *****
/**
 * Erstellt einen zufälligen Gegner mit zufälligen Attributen und Attributwachstum. 
 * @param enemyLevel das Gegner-Level, wird benötigt um das Wachstum der Attribute anzuwenden.
 */
public Enemy(int enemyLevel) {

    this.level = enemyLevel;

    Random randomizer = new Random();
    int strongestEnemy = ((level +3) / 3);
    if(strongestEnemy < 1) { // kann nicht kleiner als 1 sein
        strongestEnemy = 1;
    } // if
    if(strongestEnemy > enemyKlasseName.length) { // damit das Index nicht überschritten wird
        strongestEnemy = enemyKlasseName.length;
    } // if
    this.enemyKlasse = randomizer.nextInt(strongestEnemy);
    this.enemyName = enemyKlasseName[enemyKlasse];
    this.level = enemyLevel;
    this.currentLebenspunkte /= 2;
    this.lootMultiplier = (enemyKlassenWachstum[enemyKlasse][4] / 10.0);

    for(int i = 0; i < enemyLevel; i++) { // Passt die Werte des Gegners seinem Level an
        this.currentLebenspunkte += enemyKlassenWachstum[enemyKlasse][0];
        this.angriff += enemyKlassenWachstum[enemyKlasse][1];  
        this.ausweichwert += enemyKlassenWachstum[enemyKlasse][2];
        this.verteidigung += enemyKlassenWachstum[enemyKlasse][3];
    } // for

    this.currentLebenspunkte *= Mathematik.DynamicVarianceGenerator(10, 30);
    this.angriff *= Mathematik.DynamicVarianceGenerator(20, 20);
    this.ausweichwert *= Mathematik.DynamicVarianceGenerator(20, 20);
    this.verteidigung *= Mathematik.DynamicVarianceGenerator(20, 20);
} // ***** ↑ ↑ ↑ ↑ ↑ ***** K O N S T R U K T O R E N / E N E M Y ***** ↑ ↑ ↑ ↑ ↑ *****  


// ***** ↓ ↓ ↓ ↓ ↓ ***** G E T T E R / E N E M Y ***** ↓ ↓ ↓ ↓ ↓ *****

    /**
     * @return Angriffwert des Gegners
     */
    public int getAngriff() {
        return this.angriff;
    } // getAngriff

    /**
     * @return Ausweichwert des Gegners 
     */
    public int getAusweichwert() {
        return this.ausweichwert;
    } // getAusweichwert

    /**
     * Überschreibt {@link Character#getBeweglichkeit()}
     * @return Ausweichwert des Gegners
     */
    @Override
    public int getBeweglichkeit() {
        return this.ausweichwert;
    } // getBeweglichkeit

    /**
     * @return String-Wert des Gegnertyps
     */
    public String getEnemyName() {
        return this.enemyName;
    } // getEnemyName

    /**
     * @return Integer-Wert des Gegnertyps
     */
    public int getEnemeyKlasse() {
        return this.enemyKlasse;
    } // getEnemyKlasse

    /**
     * @return Faktor zum Multiplizieren des XP-Wertes aus {@link AdventureKampf#Victory()}
     */
    public double getLootMultiplier() {
        return this.lootMultiplier;
    } // getLootMultiplier

    /**
     * Fasst alle wichtigen Getter in einem String gebündelt zusammen.
     * @return alle relevanten Informationen zum Gegner.
     */
    public String getAllEnemy() {
        String message = "Level " + getLevel() + " " + getEnemyName();
        message += "\nLebenspunkte: " + getCurrentLebenspunkte();
        message += "\nAngriff: " + getAngriff();
        message += "\nAusweichswert: " + getAusweichwert();
        message += "\nVerteidigung: " + getVerteidigung();
        // message += "\nXP-Value: " + getCurrentXP();
        return message;
    } // ***** ↑ ↑ ↑ ↑ ↑ ***** G E T T E R / E N E M Y ***** ↑ ↑ ↑ ↑ ↑ *****


// ***** ↓ ↓ ↓ ↓ ↓ ***** S E T T E R / E N E M Y ***** ↓ ↓ ↓ ↓ ↓ *****
// ***** ↑ ↑ ↑ ↑ ↑ ***** S E T T E R / E N E M Y ***** ↑ ↑ ↑ ↑ ↑ *****

} // ***** ***** ***** E N E M Y - K L A S S E ***** ***** *****