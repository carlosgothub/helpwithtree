/**
 * Created on 4/22/24
 */
package edu.ilstu;

import java.io.*;
import java.util.*;

/**
 * Class that will house the tree containing the letters, which will be accessed via morse code
 * Inner class will contain the information for the nodes used in the tree
 *
 * @author cvarga9
 */

public class MorseTree {
    //Inner class start
    public static class Node {
        //Creating needed variables
        private char data;
        private Node leftChild;
        private Node rightChild;

        public Node(char data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }

//        public Node(Node leftChild, Node rightChild, char data) {
//            this.leftChild = leftChild;
//            this.rightChild = rightChild;
//            this.data = data;
//        }
    }//End of Inner class
    //Start of MorseTree class

    //Needed Vars
    private Node root;
    private int treeLevel = 0;

    //Default constructor
    public MorseTree() {
        this.root = null;
        this.treeLevel = 0;
    }

    //Methods
    //Method to read a spreadsheet file (Morse Code, then Letter)
    public String[][] readFile(String name) {
        //Creating needed vars
        String[][] letterPlacement = new String[26][2]; //First slot is a placeholder
        int index = 0;
        //Creating file
        File userFile = new File(name);
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(userFile);
            //Breaking up the spreadsheet into an array
            while (fileReader.hasNext()) {
                String temp = fileReader.nextLine().trim();
                if (!temp.isEmpty()) {
                    String[] line = temp.split(",");
                    //Adding to array
                    if (line.length == 2) {
                        letterPlacement[index][0] = line[0];
                        letterPlacement[index][1] = line[1];
                        index++;
                    } else {
                        System.out.println("Incorrect line: " + temp);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally { //Closing the scanner
            if (fileReader != null) {
                fileReader.close();
            }
        }

        return letterPlacement;
    }

    //Starter method to add char to tree using spreadsheet
    public void addNodeToTree(String[][] data) {
        root = new Node(' '); //Initial node for root
        addNodeToTreeRex(root, data, 0);
    }

    //Recursive method to add char to tree using spreadsheet
    private Node addNodeToTreeRex(Node curr, String[][] data, int index) {
        //Base case
        if (index == data.length) {
            return null;
        }
        //Creating needed vars
        String morse = data[index][0];
        char letter = data[index][1].charAt(0);
        String morseNext = data[index + 1][0];
        if (curr != root) {
            curr = new Node(letter);
        }
        //Creating tree
        if (root == null) { //Check if tree exists
            root = new Node(' ');
            return root;
        } else if (index != data.length) {
            if (morse.charAt(morse.length() - 1) == '.' && morseNext.charAt(morseNext.length() - 1) == '_') { //Adding to tree, left subtree and right
                curr.leftChild = addNodeToTreeRex(curr.leftChild, data, index + 1);
                curr.rightChild = addNodeToTreeRex(curr.rightChild, data, index + 2);
            } else if (morse.charAt(morse.length() - 1) == '_' && morseNext.charAt(morseNext.length() - 1) == '.') { //Adding to tree, right subtree and left
                curr.rightChild = addNodeToTreeRex(curr.rightChild, data, index + 1);
                curr.leftChild = addNodeToTreeRex(curr.leftChild, data, index + 2);
            }
        } else if (morse.charAt(morse.length() - 1) == '.') { //Adding to tree, left subtree only
            curr.leftChild = addNodeToTreeRex(curr.leftChild, data, index + 1);
        } else if (morse.charAt(morse.length() - 1) == '_') { //Adding to tree, right subtree only
            curr.rightChild = addNodeToTreeRex(curr.rightChild, data, index + 1);
        }
        return curr;
    }

    //Starter method to find the char from the tree
    public char findChar(String userMorse) {
        String[] array = userMorse.split(" ");
        return findCharRex(root, array, 0, 0);
    }

    //Recursive method to find the char from the tree
    private char findCharRex(Node curr, String[] userMorse, int index, int arrayIndex) {
        if (curr == null) {
            return '0'; //Will return if the element wasn't in the array
        }
        if (index == userMorse[arrayIndex].length()) {
            return curr.data; //Returning the letter that was stored in the tree
        }
        if (userMorse[arrayIndex].charAt(index) == '.') { //Goes left
            return findCharRex(curr.leftChild, userMorse, index + 1, arrayIndex);
        } else if (userMorse[arrayIndex].charAt(index) == '_') { //Goes right
            return findCharRex(curr.rightChild, userMorse, index + 1, arrayIndex);
        } else {
            System.out.println("The symbol " + userMorse[arrayIndex].charAt(index) + " is not valid");
            return '?'; //Will return if the morse code is invalid (Specific character)
        }
    }
}