package AdventureOOP;

import javax.swing.*;
// import java.awt.*;

public class Dialogue {

    private static JFrame ReturnFrameCenteredVisible() { // Centered, Visible

        JFrame frame = new JFrame("Hauptfenster");
        frame.setUndecorated(true);          
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        return frame;
    } // Frame

    // Gibt eine Nachricht über JOptionPane aus, die bestätigt werden muss
    public static void Confirm(String message) {

        JFrame frame = ReturnFrameCenteredVisible();
        
        String confirmDialogue[] = {"Weiter"};
        JOptionPane.showOptionDialog(frame, message, "Um fortzufahren \"Weiter\" drücken.", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, confirmDialogue, confirmDialogue[0]);

        frame.dispose();

    } // UserInputConfirmDialog

    public static int Choice(String message, String title, String[] options, boolean check) {

        JFrame frame = ReturnFrameCenteredVisible();

        int userInput = 0;
        do {
            userInput = JOptionPane.showOptionDialog(frame, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if(check) {
                check = 1 == UserInputCheck(options[userInput]); // Prüft, ob die Eingabe bestätigt wurde oder nicht
            } // if
        } while (check);

        frame.dispose();

        return userInput;
    } // UserInputInt

    public static String UserInputString(String message, boolean check) {

        JFrame frame = ReturnFrameCenteredVisible();

        String userInput = "";
        do {
            userInput = JOptionPane.showInputDialog(frame, message);
            if(check) {
                check = 1 == UserInputCheck(userInput);
            } // if       
        } while (check);

        frame.dispose();

        return userInput;
    } // UserInputString    

    private static int UserInputCheck(String userInput) {

        JFrame frame = ReturnFrameCenteredVisible();

        String options[] = {"Ja", "Nein"};
        int check = JOptionPane.showOptionDialog(frame, "Bist du sicher?\nDeine Eingabe: " + userInput, "Bestätigen", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        frame.dispose();

        return check;
    }
} // UserInput-Klasse
