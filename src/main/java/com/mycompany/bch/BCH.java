package com.mycompany.bch;

import java.util.Scanner;
import java.lang.NumberFormatException;

/**
 *
 * @author Absal
 */
public class BCH {

    static int charZero = (int) '0';

    //defining last 4 rows of generator Matrix
    public static int[] d7 = {4, 10, 9, 2, 1, 7};
    public static int[] d8 = {7, 8, 7, 1, 9, 6};
    public static int[] d9 = {9, 1, 7, 8, 7, 7};
    public static int[] d10 = {1, 2, 9, 10, 4, 1};
    public static int[][] d = {d7, d8, d9, d10};
    
    //To validate the input message code given by the user
    public static boolean isInputValid(String inputCode) {
        //checking if value is 6 digits
        if(inputCode.length() != 6) {
            return false;
        }
        for (int i = 0; i<inputCode.length(); i++) {
            try{
                int num = Integer.parseInt(inputCode);
            } catch (NumberFormatException e) {
                System.out.println(e);
                return false;
            }
            
        }
        
        return true;
    }

    //Encoding 6 digit code with generator matrix
    public static String generateBCH(String inputCode) {
        String encodedMessage = inputCode;
        
        for (int i = 0; i < d.length; i++) {
            int encodedBit = 0;
            for (int j = 0; j < d[i].length; j++) {
                encodedBit += ((inputCode.codePointAt(j) - charZero) * d[i][j]);
            }
            String encodedBitInString = Integer.toString(encodedBit%11);
            encodedMessage = encodedMessage.concat(encodedBitInString);
        }
        return encodedMessage;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a 6 digit integer to encode");
        String code = scan.nextLine();
        if(!isInputValid(code)){
            System.out.println("Please make sure that input is correct");
            System.exit(0);;
        }
        System.out.println("Encoded Message: " + generateBCH(code));
    }
}
