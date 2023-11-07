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
    public static boolean isInputValid(String inputCode, int digits) {
        //checking if value is 6 digits
        if(inputCode.length() != digits) {
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
    
    //identifying errors using syndrom
    public static double[] detectError(String inputCode) {
        double[] s = new double[4];
        int x = 0;
        for (int i = 0; i < 4; i++) {
            double result = 0;
            double temp = 0;
            for (int j = 1; j <= 10; j++) {
                if (j < 2) {
                    x = j;
                }
                temp = Math.pow(x * j, i)*(inputCode.codePointAt(j-1) - charZero) ;
                result += temp;

            }
            s[i] = result%11;
        }
        return s;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a 6 digit integer to encode");
        String code = scan.nextLine();
        double[] s = detectError(code);
        System.out.println("result" + s[0]);
        System.out.println("result" + s[1]);
        System.out.println("result" + s[2]);
        System.out.println("result" + s[3]);
//        if(!isInputValid(code, 6)){
//            System.out.println("Please make sure that input is correct");
//            System.exit(0);
//        }
//        System.out.println("Encoded Message: " + generateBCH(code));
    }
}
