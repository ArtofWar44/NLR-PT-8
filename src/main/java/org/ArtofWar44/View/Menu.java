package org.ArtofWar44.View;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;


//borrowed Menu from Tom's Vending Machine to get my selections to work

public class Menu<T> {

    private final PrintWriter out;
    private final Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    /*
     Displays the menu options and gets the user's choice.
     */
    public T getChoiceFromOptions(T[] options) {
        T choice = null;
        while (choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    /*
     Gets the user's choice from their input.
     */
    private T getChoiceFromUserInput(T[] options) {
        T choice = null;
        String userInput = in.nextLine();
        try {
            int selectedOption = Integer.parseInt(userInput);
            if (selectedOption > 0 && selectedOption <= options.length) {
                choice = options[selectedOption - 1];
            }
        } catch (NumberFormatException e) {  // eat the exception, an error message will be displayed below since choice will be null

        }
        if (choice == null) {
            out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
        }
        return choice;
    }

    /*
     Displays the menu options to the user.
     */
    private void displayMenuOptions(T[] options) {
        out.println();
        for (int i = 0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println(optionNum + ") " + options[i]);
        }
        out.print(System.lineSeparator() + "Please choose an option >>> ");
        out.flush();
    }
}
