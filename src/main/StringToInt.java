package main;

/**
 * Created by Кирилл on 24.10.2015.
 */
public class StringToInt {
    public static void main(String[] args){
        String inputString = "10110";
        int inputNums[] = new int[inputString.length()];


       // intArray[i] = Integer.parseInt(String.valueOf(s.charAt(i)));

        for(int i=0; i<inputString.length(); i++){
            if (!Character.isDigit(inputString.charAt(i))) {
                System.out.println("Contains an invalid digit");
                break;
            }

            inputNums[i] = Integer.parseInt(String.valueOf(inputString.charAt(i)));
        }
        Main.printArray(inputNums);}
}
