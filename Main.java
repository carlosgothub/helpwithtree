/**
 * Created on 4/22/24
 */
package edu.ilstu;

import java.util.*;

/**
 * Class that controls the flow as well as accesses the MorseTree class. Will take the users input and will return the correct word decoded
 *
 * @author cvarga9
 */
public class Main {
    //Creating static tree for use in methods
    static MorseTree tree = new MorseTree();

    public static void main(String[] args) {
        //Creating needed vars
        Scanner sc = new Scanner(System.in);
        String input;
        //Filling tree with Morse Code translations
        buildTree();
        //What the user interacts with
        System.out.println("Hello! Welcome to the Morse Code decoder!");
        input = getUser(sc);
        //Checking if morse code is valid
        while (!input.equalsIgnoreCase("stop")) {
            if (validateChoice(input, sc)) {//Is valid
                System.out.print("The decoded message is: " + decode(input));
                input = getUser(sc);
            }
        }
        if (input.equalsIgnoreCase("stop")) { //Double check to ensure the user entered stop
            System.out.println("Thank you for using our decoder!");
            sc.close();
        }
    }

    //Methods
    //decode
    public static String decode(String user) {
        //Creating needed vars
        StringBuilder userResult = new StringBuilder();
        //Decoding Morse Code
        userResult.append(tree.findChar(user));
        return userResult.toString();
    }

    //addFile
    public static void buildTree() {
        tree.addNodeToTree(tree.readFile("Morse Code Alphabet.csv"));
    }

    //getUser
    public static String getUser(Scanner sc) {
        System.out.print("Please enter encoded message or stop to exit (Must use periods and underscores): ");
        return sc.next();
    }

    //validateChoice
    public static boolean validateChoice(String choice, Scanner sc) {
        boolean valid = false;
        if (!choice.contains(".") && !choice.contains("_")) { //Not valid
            System.out.println("Please enter a valid Morse Code! ");
            valid = validateChoice(getUser(sc), sc);
        } else {
            valid = true;
        }
        return valid;
    }
}