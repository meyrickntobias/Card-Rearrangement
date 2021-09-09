package week11;

import java.util.Arrays;
import java.util.Scanner;

/**
 * CP.java - May 2020.
 * A class that transforms a pile of cards by specification
 * @author Toby Meyrick
 */

public class CP implements CardPile {
        
    /** Pile of cards array. */
    private int[] cards;
    /** Used for containing temporary states of pile. */
    private int[] temp;
    /** Past this length of commands, start loading numbers into pile array.*/
    private static final int COMMAND_LOAD_POINT = 3;
        
    /** An array of all of the different specifications allowed. */
    private static final String[] SPECS = new String[]
        {"TL","BL","TR","BR","LT","LB","RT","RB"};
                
        
    /** 
     * The main method which handles command line arguments and stdin.
     * @param args the command line arguments which determines the 
     * subsequent output
     */
    public static void main(String[] args) {
        int sizeOfPile;
        int r; //row length
        CP c = new CP();
        /* Handle command line arguments */
        if (args.length > 0) {
            if (args.length == 2) {
                sizeOfPile = Integer.parseInt(args[0]);
                r = Integer.parseInt(args[1]);
                c.load(sizeOfPile);
                for (String spec : SPECS) {
                    System.out.println(spec + " " + 
                                       Integer.toString(c.count(r, spec)));
                }
            } else if (args.length >= COMMAND_LOAD_POINT) {
                sizeOfPile = Integer.parseInt(args[0]);
                r = Integer.parseInt(args[1]);
                c.load(sizeOfPile);
                c.printPile();
                int transformations = args.length - 2;
                for (int i = 0; i < transformations; i++) {
                    String t = args[2+i];
                    c.transform(r, t);
                    c.printPile();
                }
            }
        } else {
            handleInput(c);
        }               
    }
        
    /** 
     * Method for handling input from stdin.
     * @param c CP object to pass into method
     */
    private static void handleInput(CP c) {
        /* Handle system input */
        Scanner sc = new Scanner(System.in);
        int rowLength = 0;
        String spec = "";
        int cpLength = 0;
        while (sc.hasNextLine()) {
            if (sc.hasNext()) {
                String command = sc.next();
                switch(command) {
                    case "c":
                        // count(r, s)
                        rowLength = sc.nextInt();       
                        spec = sc.next();
                        cpLength = c.getPile().length;
                        if (cpLength % rowLength != 0 || !inSpecs(spec)) {
                            break;
                        }
                        System.out.println(c.count(rowLength, spec));
                        break;
                    case "l":
                        // load the pile with cards from 1 to n
                        c.load(sc.nextInt());
                        break;
                    case "L":
                        // load the pile with given numbers
                        int countNums = 0;
                        int[] arrInt = new int[10];
                        while (sc.hasNextInt()) {
                            if (countNums == arrInt.length-1) {
                                arrInt = Arrays.copyOf(arrInt, arrInt.length*2);
                            }
                            arrInt[countNums] = Integer.parseInt(sc.next());
                            countNums++;
                        }
                        arrInt = Arrays.copyOf(arrInt, countNums);
                        c.load(arrInt);
                        break;
                    case "p":
                        // print the cards as a white space separated list
                        c.printPile();
                        break;
                    case "P":
                        // print the cards in rows of length n
                        int n = sc.nextInt();
                        cpLength = c.getPile().length;
                        if (n < 1 || cpLength % n != 0) {
                            break;
                        }
                        c.printPile(n); 
                        break;
                    case "t":
                        // transform(r, s)
                        rowLength = sc.nextInt();
                        spec = sc.next();
                        cpLength = c.getPile().length;
                        if (cpLength % rowLength != 0 || !inSpecs(spec)) {
                            break;
                        }
                        c.transform(rowLength, spec);
                        break;
                    default:
                    	break;
                }
            } else {
            	System.exit(0);
            }
        }
    }
        
    /**
     * Checks if the String is in the SPECS array.
     * @param s the String you want to check against
     * @return true if it is, false if it isn't
     */
    private static boolean inSpecs(String s) {
        for (String spec : SPECS) {
            if (s.equals(spec)) {
                return true;
            }
        }       
        return false;
    }
        
    /** 
     * Returns a copy of the pile, doesn't modify original array.
     * @return a copy of the original array
     */
    public int[] getPile() {
        int[] rtnArr = Arrays.copyOf(this.cards, this.cards.length);
        return rtnArr;
    }
        
    /**
     * Initalises the pile of cards to consist of the contents of the array 
     * provided.
     * @param cards an array of numbers that represents cards
     */
    public void load(int[] cards) {
        this.cards = cards;
        this.temp = getPile();
    }
        
    /**
     * Initalises the pile of cards to consist of numbers 1 through n.
     * @param n the length of the pile
     */
    public void load(int n) {
        if (n <= 0) {
            throw new CardPileException("Please enter a positive integer");
        }
        this.cards = new int[n];
        for (int i = 0; i < n; i++) {
            this.cards[i] = i+1;
        }
        this.temp = this.getPile();
    }
        
    /**
     * Transforms a pile of cards depending on the specification inputted.
     * @param rowLength the length of the rows
     * @param spec the specification of how the pile should be transformed
     */
    public void transform(int rowLength, String spec) {
        if (this.cards.length % rowLength != 0) {
            throw new CardPileException
            ("The size of the pile must be a multiple of row length");
        }
        int noOfRows = this.cards.length/rowLength; 
        int[][] specArr = new int[noOfRows][rowLength];
        for (int i = 0; i < noOfRows; i++) {
            specArr[i] = Arrays.copyOfRange
                (this.temp, i * rowLength, i*rowLength+rowLength);
        }
        updateTemp(temp, specArr, rowLength, noOfRows, spec);
    }
    
    /** 
     * Method that works for the transform method.
     * Depending on rowLength and spec, will update the temp array
     * @param temp the temporary array to update
     * @param specArr the 2d array to use to transform the array
     * @param rowLength the length of the rows
     * @param noOfRows the number of rows
     * @param spec the specification of how the pile should be transformed
     */
    private void updateTemp(int[] temp, int[][] specArr, int rowLength, int noOfRows, String spec) {
    	switch(spec) {
            case "TL":          
                for (int i = 0; i < rowLength; i++) {
                    for (int j = 0; j < noOfRows; j++) {
                        temp[j+(noOfRows*i)] = specArr[j][i];
                    }
                }
                break;
            case "TR":
                for (int i = 0; i < rowLength; i++) {
                    for (int j = 0; j < noOfRows; j++) {
                        temp[j+(noOfRows*i)] = specArr[j][(rowLength-1)-i];
                    }
                }
                break;
            case "BL":
                for (int i = 0; i < rowLength; i++) {
                    for (int j = 0; j < noOfRows; j++) {
                        temp[j+(noOfRows*i)] = specArr[((noOfRows-1)-j)][i];
                    }
                }
                break;
            case "BR":
                for (int i = 0; i < rowLength; i++) {
                    for (int j = 0; j < noOfRows; j++) {
                        temp[j+(noOfRows*i)] =
                            specArr[(noOfRows-1)-j][(rowLength-1)-i];
                    }
                }
                break;
            case "LT":
                for (int i = 0; i < noOfRows; i++) {
                    for (int j = 0; j < rowLength; j++) {
                        temp[j+(i*rowLength)] = specArr[i][j];
                    }
                }
                break;
            case "RT":
                for (int i = 0; i < noOfRows; i++) {
                    for (int j = 0; j < rowLength; j++) {
                        temp[(j)+(rowLength*i)] = specArr[i][(rowLength-1)-j];
                    }
                }
                break;
            case "LB":
                for (int i = 0; i < noOfRows; i++) {
                    for (int j = 0; j < rowLength; j++) {
                        temp[j+(rowLength*i)] = specArr[(noOfRows-1)-i][j]; 
                    }
                }
                break;
            case "RB":
                for (int i = 0; i < noOfRows; i++) {
                    for (int j = 0; j < rowLength; j++) {
                        temp[j+(rowLength*i)] =
                            specArr[(noOfRows-1)-i][(rowLength-1)-j]; 
                    }
                }
                break;
            default:
                throw new CardPileException("Not a valid specification");
        }
    }
        
    /**
     * Counts how many transformations to get to original state.
     * @return the number of transformations 
     * @param rowLength the length of the rows
     * @param spec the String specification
     */
    public int count(int rowLength, String spec) {
        int counter = 0;
        // While the temporary array is not equal to 
        do {
            this.transform(rowLength, spec);
            counter++;
        } while (!Arrays.equals(this.temp, this.cards));
        return counter;
    }
        
    /** 
     * Print the pile in a white separated list.
     */
    public void printPile() {
        for (int i = 0; i < this.temp.length; i++) {
            System.out.print(temp[i] + " ");
        }
        System.out.println();
    }
        
    /** 
     * Print the pile in a grid format.
     * @param n the number of rows you want to print 
     */
    public void printPile(int n) {
        if (this.cards.length % n != 0) {
            throw new CardPileException
            ("Can only print by rows that are a factor of length");
        }
        for (int i = 0; i < this.temp.length; i+=n) {
            for (int j = 0; j < n; j++) {
                System.out.print(temp[i+j] + " ");
            }
            System.out.println();
        }
    }
        
}
