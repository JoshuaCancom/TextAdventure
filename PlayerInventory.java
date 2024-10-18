package AdventureOOP;
import java.io.*;

/**
 * Das PlayerInventory ist eine Erweiterung der Player-Unterklasse und fügt ein Inventar hinzu. <br>
 * Das Inventar enthält Gold, Materialien und die Ausrüstung des Players. Die Namensgebung sollte eindeutig zuordbar sein.<br>
 * Ist serializable, damit das Objekt gespeichert und geladen werden kann.<br>
 * Wichtige Klassen:<br>
 * {@link Main}<br>
 * {@link Character}<br>
 * {@link Player}<br>
 */
public class PlayerInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    int gold = 50;
    int holz = 0;
    int erz = 0;    
    int edelsteine = 0;

    int waffe = 1;
    int ruestung = 1;
    int zweitwaffe = 1;
    int schuhe = 1;

    // Kosten: 0 = Gold, 1 = Holz, 2 = Erz, 3 = Edelstiene
    int[] upgradeKostenWaffe = {50, 5, 0, 0};
    int[] upgradeKostenRuestung = {55, 6, 0, 0};
    int[] upgradeKostenZweitwaffe = {45, 4, 0, 0};
    int[] upgradeKostenSchuhe = {40, 4, 0, 0};

    // Konstruktor
    /**
     * Konstruktor zum Erstellen eines Inventars für den zugehörigen Spieler.
     * Bedarf keiner Parameter, da jedes Inventar gleich erstellt wird.
     */
    public PlayerInventory() {
    } // Konstruktor

    // Getter
    /** 
     * @return Anzahl Gold im Inventar des Spielers
     */
    public int getGold() {
        return this.gold;
    } // getGold

    /**
     * @return Anzahl Erz im Inventar des Spielers
     */
    public int getErz() {
        return this.erz;
    } // getErz

    /**
     * @return Anzahl Holz im Inventar des Spielers
     */
    public int getHolz() {
        return this.holz;
    } // getHolz

    /**
     * @return Anzahl Edelsteine im Inventar des Spielers
     */
    public int getEdelsteine() {
        return this.edelsteine;
    } // getEdelsteine

    /**
     * @return Level der Waffe des Spielers
     */
    public int getWaffe() {
        return this.waffe;
    } // getWaffe

    /**
     * @return Level der Rüstung des Spielers
     */
    public int getRuestung() {
        return this.ruestung;
    } // getRuestung

    /**
     * @return Level der Zweitwaffe des Spielers
     */
    public int getZweitwaffe() {
        return this.zweitwaffe;
    } // getZweitwaffe

    /**
     * @return Level der Schuhe des Spielers
     */
    public int getSchuhe() {
        return this.schuhe;
    } // getSchuhe

    /**
     * Fasst alle Werte in einem String zusammen.
     * @return String mit allen zusammengeassten Werten
     */
    public String getAll() {
        String message = "Gold: " + getGold();        
        message += "\nHolz: " + getHolz();
        message += "\nErz: " + getErz();
        message += "\nEdelsteine: " + getEdelsteine();
        message += "\n\nWaffe: Stufe " + getWaffe();
        message += "\nRüstung: Stufe " + getRuestung();
        message += "\nZweitwaffe: Stufe " + getZweitwaffe();
        message += "\nSchuhe: Stufe " + getSchuhe();
        return message;
    } // getAll
    
    /**
     * @return ein Array mit den Kosten zum Upgraden auf das nächste Waffen-Level
     */
    public int[] getUpgradeKostenWaffe() { // 0 = Waffe, 1 = Ruestung, 2 = Zweitwaffe, 3 = Schuhe
        return upgradeKostenWaffe;
    } // getUpgradeKostenWaffe

    /**
     * @return ein Array mit den Kosten zum Upgraden auf das nächste Rüstungs-Level
     */
    public int[] getUpgradeKostenRuestung() {
        return upgradeKostenRuestung;
    } // getUpgradeKostenRuestung

    /**
     * @return ein Array mit den Kosten zum Upgraden auf das nächste Zweitwaffen-Level
     */
    public int[] getUpgradeKostenZweitwaffe() {
        return upgradeKostenZweitwaffe;
    } // getUpgradeKostenZweitwaffe

    /**
     * @return ein Array mit den Kosten zum Upgraden auf das nächste Schuh-Level
     */
    public int[] getUpgradeKostenSchuhe() {
        return upgradeKostenSchuhe;
    } // getUpgradeKostenSchuhe
    // Getter

    // Setter
    /**
     * Fügt Gold hinzu.
     * @param gold die Menge an Gold, die hinzugefügt wird
     */
    public void addGold(int gold) {
        this.gold += gold;
    } // addGold

    /**
     * Entfernt Gold. Kann nicht weniger als 0 werden.
     * @param gold die Menge an Gold, die entfernt wird
     */
    public void subGold(int gold) {
        if(this.gold - gold < 0) {
            Dialogue.Confirm("Nicht genug Gold.");
        } else {
            this.gold -= gold;
        } // if
    } // subGold

    /**
     * Fügt Erz hinzu.
     * @param erz die Menge die hinzugefügt wird
     */
    public void addErz(int erz) {
        this.erz += erz;
    } // addErz

    /**
     * Entfernt Erz. Kann nicht weniger als 0 sein.
     * @param erz die Menge, die entfernt wird.
     */
    public void subErz(int erz) {
        if(this.erz - erz < 0) {
            Dialogue.Confirm("Nicht genug Erz.");
        } else {
            this.erz -= erz;
        } // if
    } // subErz

    /**
     * Fügt Holz hinzu.
     * @param holz die Menge die hinzugefügt wird
     */
    public void addHolz(int holz) {
        this.holz += holz;
    } // addHolz

    /**
     * Entfernt Holz. Kann nicht weniger als 0 sein.
     * @param holz die Menge, die entfernt wird
     */
    public void subHolz(int holz) {
        if(this.holz - holz < 0) {
            Dialogue.Confirm("Nicht genug Holz.");
        } else {
            this.holz -= holz;
        } // if
    } // subHolz

    /**
     * Fügt Edelsteine hinzu.
     * @param edelsteine die Menge, die hinzugefügt wird
     */
    public void addEdelsteine(int edelsteine) {
        this.edelsteine += edelsteine;
    } // addEdelsteine

    /**
     * Entfernt Edelsteine. Kann nicht weniger als 0 sein.
     * @param edelsteine die Menge, die entfernt wird.
     */
    public void subEdelsteine(int edelsteine) {
        if(this.edelsteine - edelsteine < 0) {
            Dialogue.Confirm("Nicht genug Edelsteine.");
        } else {
            this.edelsteine -= edelsteine;
        } // iff
    } // subEdelsteine

    /**
     * Erhöht das Level der Waffe um 1.
     * Ruft Funktion {@link #setUpgradeKostenWaffe()} auf, um die Kosten für das neue Level anzupassen.
     */
    public void levelWaffe() {
        setUpgradeKostenWaffe();
        this.waffe += 1;
    } // levelWaffe
    
    /**
     * Passt die Upgradekosten an das nächste Level der Waffe an.
     */
    private void setUpgradeKostenWaffe() { // 0 =  Gold, 1 = Holz, 2 = Erz, 3 = Edelsteine
            upgradeKostenWaffe[0] += ((waffe * 10) + (Mathematik.getAbsoluteValue((waffe * 2), 25)));
            upgradeKostenWaffe[1] += ((waffe * 3) + (Mathematik.getAbsoluteValue((waffe * 2), 150)));
            upgradeKostenWaffe[2] += ((waffe * 2) + (Mathematik.getAbsoluteValue((waffe * 2), 300)));
            upgradeKostenWaffe[3] += ((waffe * 1) + (Mathematik.getAbsoluteValue((waffe * 2), 450)));
    } // setUpgradekostenWaffe

    /**
     * Erhöht das Level der Rüstung um 1.
     * Ruft Funktion {@link #setUpgradeKostenRuestung()} auf, um die Kosten für das neue Level anzupassen.
     */
    public void levelRuestung() {
        setUpgradeKostenRuestung();
        this.ruestung += 1;
    } // levelRuestung

    /**
     * Passt die Upgradekosten an das nächste Level der Ruestung an.
     */    
    private void setUpgradeKostenRuestung() { // 0 =  Gold, 1 = Holz, 2 = Erz, 3 = Edelsteine
        upgradeKostenRuestung[0] += ((ruestung * 10) + (Mathematik.getAbsoluteValue((ruestung * 2), 20)));
        upgradeKostenRuestung[1] += ((ruestung * 3) + (Mathematik.getAbsoluteValue((ruestung * 2), 125)));
        upgradeKostenRuestung[2] += ((ruestung * 2) + (Mathematik.getAbsoluteValue((ruestung * 2), 250)));
        upgradeKostenRuestung[3] += ((ruestung * 1) + (Mathematik.getAbsoluteValue((ruestung * 2), 375)));
    } // setUpgradeKostenRuestung

    /**
     * Erhöht das Level der Zweitwaffe um 1.
     * Ruft Funktion {@link #setUpgradeKostenZweitwaffe()} auf, um die Kosten für das neue Level anzupassen.
     */
    public void levelZweitwaffe() {
        setUpgradeKostenZweitwaffe();
        this.zweitwaffe += 1;
    } // levelZweitwaffe

    /**
     * Passt die Upgradekosten an das nächste Level der Zweitwaffe an.
     */ 
    private void setUpgradeKostenZweitwaffe() { // 0 =  Gold, 1 = Holz, 2 = Erz, 3 = Edelsteine
        upgradeKostenZweitwaffe[0] += ((zweitwaffe * 10) + (Mathematik.getAbsoluteValue((zweitwaffe * 2), 35)));
        upgradeKostenZweitwaffe[1] += ((zweitwaffe * 3) + (Mathematik.getAbsoluteValue((zweitwaffe * 2), 175)));
        upgradeKostenZweitwaffe[2] += ((zweitwaffe * 2) + (Mathematik.getAbsoluteValue((zweitwaffe * 2), 350)));
        upgradeKostenZweitwaffe[3] += ((zweitwaffe * 1) + (Mathematik.getAbsoluteValue((zweitwaffe * 2), 525)));
    } // setUpgradeKostenZweitwaffe

    /**
     * Erhöht das Level der Schuhe um 1.
     * Ruft Funktion {@link #setUpgradeKostenSchuhe()} auf, um die Kosten für das neue Level anzupassen.
     */
    public void levelSchuhe() {
        setUpgradeKostenSchuhe();
        this.schuhe += 1;
    } // levelSchuhe

    /**
     * Passt die Upgradekosten an das nächste Level der Schuhe an.
     */
    private void setUpgradeKostenSchuhe() { // 0 =  Gold, 1 = Holz, 2 = Erz, 3 = Edelsteine
        upgradeKostenSchuhe[0] += ((schuhe * 10) + (Mathematik.getAbsoluteValue((schuhe * 2), 40)));
        upgradeKostenSchuhe[1] += ((schuhe * 3) + (Mathematik.getAbsoluteValue((schuhe * 2), 200)));
        upgradeKostenSchuhe[2] += ((schuhe * 2) + (Mathematik.getAbsoluteValue((schuhe * 2), 400)));
        upgradeKostenSchuhe[3] += ((schuhe * 1) + (Mathematik.getAbsoluteValue((schuhe * 2), 600)));
    } // Setter    
} // PlayerInenvtory-Klasse