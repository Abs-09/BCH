package com.mycompany.bch;

import java.util.Scanner;
import java.lang.NumberFormatException;

/**
 *
 * @author Absal
 */
public class BCH {

    
    static int charZero = (int) '0';

    //defining of generator Matrix
    public static int[] d1 = {1, 0, 0, 0, 0, 0};
    public static int[] d2 = {0, 1, 0, 0, 0, 0};
    public static int[] d3 = {0, 0, 1, 0, 0, 0};
    public static int[] d4 = {0, 0, 0, 1, 0, 0};
    public static int[] d5 = {0, 0, 0, 0, 1, 0};
    public static int[] d6 = {0, 0, 0, 0, 0, 1};
    public static int[] d7 = {4, 10, 9, 2, 1, 7};
    public static int[] d8 = {7, 8, 7, 1, 9, 6};
    public static int[] d9 = {9, 1, 7, 8, 7, 7};
    public static int[] d10 = {1, 2, 9, 10, 4, 1};
    public static int[][] d = {d1, d2, d3, d4, d5, d6, d7, d8, d9, d10};

    //defining H matrix for parity check
    public static int[] h1 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1,};
    public static int[] h2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public static int[] h3 = {1, 4, 9, 5, 3, 3, 5, 9, 4, 1};
    public static int[] h4 = {1, 8, 5, 9, 4, 7, 2, 6, 3, 10};
    public static int[][] H = {h1, h2, h3, h4};

    //To validate the input message code given by the user
    public static boolean isInputValid(String inputCode, int digits) {
        //checking if value is 6 digits
        if (inputCode.length() != digits) {
            return false;
        }
        for (int i = 0; i < inputCode.length(); i++) {
            try {
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
        String encodedMessage = "";

        for (int i = 0; i < d.length; i++) {
            int encodedBit = 0;
            for (int j = 0; j < inputCode.length(); j++) {
                encodedBit += ((inputCode.codePointAt(j) - charZero) * d[i][j]);
            }
            String encodedBitInString = Integer.toString(encodedBit % 11);
            encodedMessage = encodedMessage.concat(encodedBitInString);
        }
        return encodedMessage;
    }

    //identifying errors using syndrom
    public static Integer[] detectError(String inputCode) {
        Integer[] syndrom = new Integer[4];

        for (int i = 0; i < H.length; i++) {
            int s = 0;
            for (int j = 0; j < inputCode.length(); j++) {
                s += ((inputCode.codePointAt(j) - charZero) * H[i][j]);
            }
            syndrom[i] = s % 11;
        }

        return syndrom;
    }

    //Calculate PQR from syndromes
    public static Integer[] calculatePQR(Integer[] syndrom) {
        Integer[] PQR = new Integer[4];
        PQR[0] = (int) Math.pow(syndrom[1], 2) - (syndrom[0] * syndrom[2]); //P
        PQR[1] = (syndrom[0] * syndrom[3]) * (syndrom[1] * syndrom[2]); //Q
        PQR[2] = (int) Math.pow(syndrom[2], 2) - (syndrom[1] * syndrom[3]); //R
        return PQR;
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a 6 digit integer to encode");
        String word = scan.nextLine();
        //validation of 6 inputs
        if(!isInputValid(word, 6)){
            System.out.println("Please make sure that input is correct");
            System.exit(0);
        }
        //generating BCH with parity
        String code = generateBCH(word);
        System.out.println("Encoded Message:" + code);
        //detecting error and generating syndrom
        Integer[] s = detectError(code);
        System.out.println("\n Syndrom \n====================");
        System.out.println("S1: " + s[0]);
        System.out.println("S2: " + s[1]);
        System.out.println("S3: " + s[2]);
        System.out.println("S4: " + s[3]);
        //Calculating PQR values
        Integer[] PQR = calculatePQR(s);
        System.out.println("\n PQR \n====================");
        System.out.println("P " + PQR[0]);
        System.out.println("Q " + PQR[1]);
        System.out.println("R " + PQR[2]);
    }
}
