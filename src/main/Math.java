package main;

import java.util.Random;

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

    public int getMOVE(){return MOVE;}
    public int getSIZE(){return SIZE;}

    public void setAddString(String addString){this.addString = addString;}
    public String getAddString(){return addString;}

    public void setInputString(String inputString){this.inputString = inputString;}
    public String getInputString(){return inputString;}
    public int getInputStringLength(){
        return inputString.length();
    }

    public void setInputArrElements(int i, int val){
        inputArr[i] = val;
    }

    public int[] getGamma() {return gamma;}
    public int[] getGammaUnshifted() {return gammaUnshifted;}
    public int[] getInputArrWithGamma() {return inputArrWithGamma;}
    public int[] getInputArrWithGammaShifted() {return inputArrWithGammaShifted;}

    public int[] getNoGamma() {return noGamma;}

    public String generateAddString(){
        for(int i = (SIZE - inputString.length()); i>0; i--)
            addString += "0";
        return addString;
    }

    public  void generateGamma(){
        Random random = new Random();
        for(int i=0; i<inputArr.length; i++)
            gamma[i] = random.nextInt(2);
    }

    public  void addGamma(){
        for(int i=0; i<gamma.length; i++)
            inputArrWithGamma[i] = inputArr[i]^gamma[i];
    }

    public void removeGamma(){
        for(int i=0; i<noGamma.length; i++)
            noGamma[i] = gammaUnshifted[i]^gamma[i];
    }

    public void shift(){
        for (int i = 0; i < inputArrWithGamma.length - MOVE; i++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[i + MOVE];

        for (int i = inputArrWithGamma.length - MOVE, n = 0; i < inputArrWithGamma.length; i++, n++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[n];
    }

    public  void unshift(){
        for (int n = gammaUnshifted.length - MOVE, i = 0; n < gammaUnshifted.length; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];

        for (int n = 0, i = MOVE; n < gammaUnshifted.length - MOVE; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];
    }

    Math(){
        SIZE = 8;
        MOVE = 3;
        addString = "";
        inputArr = new int[SIZE];
        gamma = new int[SIZE];
        inputArrWithGamma = new int[SIZE];
        inputArrWithGammaShifted = new int[SIZE];
        gammaUnshifted = new int[SIZE];
        noGamma = new int[SIZE];
    }

    Math(int size, int move){
        SIZE = size;
        MOVE = move;
        addString = "";
        inputArr = new int[SIZE];
        gamma = new int[SIZE];
        inputArrWithGamma = new int[SIZE];
        inputArrWithGammaShifted = new int[SIZE];
        gammaUnshifted = new int[SIZE];
        noGamma = new int[SIZE];
    }

    public static void main(String[] args){
        System.out.println();
        Math math = new Math();

        System.out.println(math.getMOVE());
        System.out.println(math.getSIZE());
        math.setAddString("My awesome add string");
        System.out.println(math.getAddString());
    }
}
