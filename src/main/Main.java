package main;

import java.util.Random;

public class Main {

    private static void print(String string){System.out.println(string + ": ");}
    private static void print(boolean expr){System.out.println(expr);}

    public static void printArray(int[] array){
        for(int n: array)
            System.out.print(n);
        System.out.println();
    }

    //returns true in case of wrong numbers found
    public static boolean checkForWrongDigits(String inputString){

        for(int i=0; i<inputString.length(); i++)
            if((Integer.parseInt(String.valueOf(inputString.charAt(i))) == 0 ||
                    Integer.parseInt(String.valueOf(inputString.charAt(i))) ==1)==!true)
            {return true;}
               return false;
    }

    //returns true in case of characters found
    public static boolean checkForCharacters(String inputString){
        boolean foundCharacters;
        for(int i=0; i<inputString.length(); i++)
            if(Character.isAlphabetic(inputString.charAt(i)))
            {return true;}
        return false;
    }

    public static boolean checkForWrongInput(String inputString){
        boolean foundWrongDigits, foundCharacters, wrongInput;

        foundCharacters = (checkForCharacters(inputString));
        foundWrongDigits = false;
        wrongInput = false;

        try{
            foundWrongDigits = (checkForWrongDigits(inputString));
        } catch(NumberFormatException e){
            System.out.println("Number format exception");
        }
        if(foundCharacters || foundWrongDigits)
            wrongInput = true;
        return wrongInput;
    }

    //private static void makeNewArray(String nameOfArray)

    private static int[] inputArr;
    private static int[] gamma;
    private static int[] inputArrWithGamma;
    private static int[] inputArrWithGammaShifted;
    private static int[] gammaUnshifted;
    private static int[] noGamma;

    public static void main(String[] args) {

        inputArr = new int[]{1, 1, 1, 0, 0, 1, 0, 1};
        gamma = new int[inputArr.length];

        print("array");
        printArray(inputArr);

        //заполним гамму
        Random random = new Random();

        for(int i=0; i<inputArr.length; i++)
            gamma[i] = random.nextInt(2);

        print("gamma");
        printArray(gamma);

        inputArrWithGamma = new int[inputArr.length];
        for(int i=0; i<inputArr.length; i++)
            inputArrWithGamma[i] = inputArr[i]^gamma[i];

        print("array with gamma");
        printArray(inputArrWithGamma);


        inputArrWithGammaShifted = new int[inputArr.length];
        for(int i = 0; i<inputArr.length-3; i++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[i+3];

       for(int i = inputArr.length-3, n = 0; i<inputArr.length; i++, n++)
            inputArrWithGammaShifted[i] = inputArrWithGamma[n];

        print("gamma shifted");
        printArray(inputArrWithGammaShifted);

        print("gamma unshifted: ");

        gammaUnshifted = new int[inputArr.length];

        for(int n = gammaUnshifted.length-3, i=0; n<gammaUnshifted.length; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];

        printArray(gammaUnshifted);

        for(int n = 0, i = 3; n<gammaUnshifted.length-3; i++, n++)
            gammaUnshifted[i] = inputArrWithGammaShifted[n];

        printArray(gammaUnshifted);

        print("no gamma (totally decrypted)");

        noGamma = new int[inputArr.length];
        for(int i=0; i<inputArr.length; i++)
            noGamma[i] = gammaUnshifted[i]^gamma[i];

        printArray(noGamma);


        //создана функция проверки входной строки на нежелательные символы
        String inputString = "911111";

        for(int i=0; i<inputString.length(); i++)
        if((Integer.parseInt(String.valueOf(inputString.charAt(i))) == 0 ||
                Integer.parseInt(String.valueOf(inputString.charAt(i))) ==1)==!true)
            System.out.println("wrong digits! Only 1 or 0 accepted");

        //System.out.print(Integer.parseInt(String.valueOf(inputString.charAt(0))));
        inputString += "0";
        String addStr = "000";
        String tmpStr = addStr + inputString;
        print(tmpStr);

        String str1 = "";
        String str2 = "0";
        String str3 = str1+str2;
        print(str3);
        System.out.println(str1.length());

        System.out.println("--------------");
        inputString = "11111";
        String addString = "";

        for(int i = 8 - inputString.length(); i>0; i--)
            addString += "0";

        print(addString);
        inputString = addString+inputString;
        print(inputString);

        String test = "12w";

        //test = "1010";//jr
        //test = "wwww";//checkForNumbers fails
        //test = "456";//ok

        System.out.println(checkForCharacters(test));
        try{System.out.println(checkForWrongDigits(test));
        } catch(NumberFormatException e){
            System.out.println("Number format exception");
        }

        System.out.println("--------------");
        print(checkForWrongInput(test));

    }
}