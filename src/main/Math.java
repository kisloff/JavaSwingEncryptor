package main;

import java.util.Random;
import java.util.regex.Pattern;

public class Math {

    public final static int MOVE = 3;
    public final static int SIZE = 8;

    public final static String BINARY_PATTERN = "[01]";

    public static String completeString(String string){
        int diff = SIZE - string.length();

        if(diff > 0){
            for(int i = diff; i > 0; i--){
                string = "0" + string;
            }
        }
        return string;
    }

    public static int[] generateGamma(){
        int[] gamma = new int[SIZE];
        Random random = new Random();
        for(int i = 0; i < SIZE; i++) {
            gamma[i] = random.nextInt(2);
        }
        return gamma;
    }

    public static int[] sumArrays(int[] array1, int[] array2){
        int[] sum = new int[SIZE];

        for(int i = 0; i < SIZE; i++)
            sum[i] = array1[i]^array2[i];
        return sum;
    }

    public static int[] removeGamma(int[] unshiftedInput, int[] gamma){
        int[] decrypted = new int[SIZE];
        for(int i = 0; i < SIZE; i++){
            decrypted[i] = unshiftedInput[i]^gamma[i];
        }
        return decrypted;
    }

    public static int[] shift(int[] array){
        int rest = SIZE - MOVE;
        int[] shiftedArray = new int[SIZE];

        for (int i = 0; i < rest; i++)
            shiftedArray[i] = array[i + MOVE];

        for (int i = rest, n = 0; i < SIZE; i++, n++)
            shiftedArray[i] = array[n];
        return shiftedArray;
    }

    public  static int[] unshift(int[] array){
        int[] unshifted = new int[SIZE];
        for (int n = SIZE - MOVE, i = 0; n < SIZE; i++, n++)
            unshifted[i] = array[n];

        for (int n = 0, i = MOVE; n < SIZE - MOVE; i++, n++)
            unshifted[i] = array[n];
        return unshifted;
    }

    public static boolean isBinary(String string){
        return Pattern.compile(BINARY_PATTERN).matcher(string).find();
    }

    public static int[] convertStringToArray(String string) {
        int length = string.length();
        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            int value = Integer.parseInt(String.valueOf(string.charAt(i)));
            array[i] = value;
        }
        return array;
    }
}
