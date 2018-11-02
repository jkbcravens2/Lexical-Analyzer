package edu.ung.jrcrav8591.lexicalAnalyzerPackage;

import java.io.*;
import java.util.Scanner;

public class Main {
    /* Global declarations */
    /* Variables */
    private static int charClass;
    private static char[] lexeme = new char[100];
    private static char nextChar;
    private static int lexLen;
    private static String nextToken;
    private static BufferedReader inputFile;
    /* Character classes */
    private static final int LETTER = 0;
    private static final int DIGIT = 1;
    private static final int UNKNOWN = 99;
    private static final int EOF = -1;

    /* Token codes */
    private static final String INT_LIT = "INT_LIT";
    private static final String IDENT = "IDENT";
    private static final String ASSIGN_OP = "ASSIGN_OP";
    private static final String ADD_OP = "ADD_OP";
    private static final String SUB_OP = "SUB_OP";
    private static final String MULT_OP = "MULT_OP";
    private static final String DIV_OP = "DIV_OP";
    private static final String LEFT_PAREN = "LEFT_PAREN";
    private static final String RIGHT_PAREN = "RIGHT_PAREN";
    private static final String EOF_TOKEN = "EOF_TOKEN";

    /******************************************************/
    /* main driver */
    public static void main(String[] args) {
        System.out.println("Jakob Cravens");
        System.out.println("CSCI 4200 - DB ");
        System.out.println("Lexical Analyzer");
        //Read data from file
            try {
                File input = new File("src/edu/ung/jrcrav8591/lexicalAnalyzerPackage/lexinput.txt");
                inputFile = new BufferedReader(new FileReader(input));

                //Scanner to store the input into the in variable
                Scanner inputFileScanner = new Scanner(new BufferedReader(new FileReader(input)));
                do {
                    printStars();
                    String inputText = inputFileScanner.nextLine();
                    System.out.println("Input: " + inputText);
                    getChar();
                    do {
                        lex();
                    } while (!nextToken.equalsIgnoreCase(EOF_TOKEN));
                    printStars();
                }while((inputFileScanner.hasNextLine()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    /*****************************************************/
//Looks up what a specific character is
    private static String lookup(char  ch) {
        switch  (ch) {
            case  '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;

            case  ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;

            case  '+':
                addChar();
                nextToken = ADD_OP;
                break;

            case  '-':
                addChar();
                nextToken = SUB_OP;
                break;

            case  '*':
                addChar();
                nextToken = MULT_OP;
                break;

            case  '/':
                addChar();
                nextToken = DIV_OP;
                break;

            case  '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;

            case ' ':
                addChar();
                nextToken = EOF_TOKEN;

            default:
                addChar();
                nextToken = EOF_TOKEN;
                break;
        }
        return  nextToken;
    }


    /*****************************************************/
    //Adds characters to lexeme character array;
    private static void addChar() {
        if (lexLen <= 98) {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }
        else
            System.out.println("Error - lexeme is too long \n");
    }

    /*****************************************************/
//Gets the next character and assigns its class
    private static void getChar() {
        try {
            if ((nextChar = (char) inputFile.read()) != (char) EOF) {
                if (Character.isLetter(nextChar))
                    charClass = LETTER;
                else if (Character.isDigit(nextChar))
                    charClass = DIGIT;
                else charClass = UNKNOWN;
            } else
                charClass = EOF;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    /*****************************************************/
//Skips white space
    private static void getNonBlank() {
        while  (nextChar == ' ')
            getChar();
    }


    /*****************************************************/
//Lexical analyzer function
    private static String lex() {
        lexLen = 0;
        getNonBlank();
        switch (charClass) {
            //Parses the identifiers
            case LETTER:
                addChar();
                getChar();
                while  (charClass == LETTER || charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;

            //Parses the integers
            case DIGIT:
                addChar();
                getChar();
                while  (charClass == DIGIT) {
                    addChar();
                    getChar();
                }
                nextToken = INT_LIT;
                break;

            //Parses operators and special characters
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;

            //End of file
            case EOF:
                nextToken = EOF_TOKEN;
                lexeme[0] = 'E';
                lexeme[1] = 'O';
                lexeme[2] = 'F';
                lexeme[3] = 0;
                break;
        } /* End of switch */

        StringBuilder lexString = new StringBuilder();
        for (char c: lexeme){
            lexString.append(c);
        }
        System.out.println("Next token is: " + nextToken + ", Next lexeme is: " + lexString);
        lexString.setLength(0);
        lexeme = new char[100];
        return  nextToken;
    }  /* End of function lex */

    //prints 80 asterisks
    private static void printStars(){
        for(int i = 0; i < 80; i++){
            System.out.print("*");
        }
        System.out.println();
    }


}

