package main;

import java.util.Random;

/**
 * Created by Кирилл on 31.10.2015.
 */
public class Math {
    private static int MOVE = 3;
    private static int SIZE = 8;

    private String inputString;
    private String addString;
    private int[] inputArr;
    private int[] gamma;
    private int[] inputArrWithGamma;
    private int[] inputArrWithGammaShifted;
    private int[] gammaUnshifted;
    private int[] noGamma;

    public void generateAddString(String inputString){
        for(int i = (SIZE - inputString.length()); i>0; i--)
            addString += "0";
    }

    public static void generateGamma(int[] inputArr, int[] gamma){
        Random random = new Random();
        for(int i=0; i<inputArr.length; i++)
            gamma[i] = random.nextInt(2);
    }

    public static void addGamma(int[] inputArrWithGamma, int[] inputArr, int[] gamma){
        for(int i=0; i<gamma.length; i++)
            inputArrWithGamma[i] = inputArr[i]^gamma[i];
    }

    public void removeGamma(int[] noGamma, int[] gammaUnshifted, int[] gamma){
        for(int i=0; i<noGamma.length; i++)
            noGamma[i] = gammaUnshifted[i]^gamma[i];
    }

    public static void shift(int[] inputArrWithGammaShifted, int[] inputArrWithGamma){
        for (int i = 0; i < inputArrWithGamma.length - MOVE; i++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[i + MOVE];

        for (int i = inputArrWithGamma.length - MOVE, n = 0; i < inputArrWithGamma.length; i++, n++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[n];
    }

    public static void unshift(int[] gammaUnshifted, int[] inputArrWithGammaShifted){
        for (int n = gammaUnshifted.length - MOVE, i = 0; n < gammaUnshifted.length; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];

        for (int n = 0, i = MOVE; n < gammaUnshifted.length - MOVE; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];
    }

    Math(){
        addString = "";
        inputArr = new int[SIZE];
        gamma = new int[SIZE];
        inputArrWithGamma = new int[SIZE];
        inputArrWithGammaShifted = new int[SIZE];
        gammaUnshifted = new int[SIZE];
        noGamma = new int[SIZE];
    }
}
