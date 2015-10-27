package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Кирилл on 23.10.2015.
 */
public class GammaAndShift extends JFrame{
    private JTextField sourcePhraseTextField;
    private JButton encryptButton;
    private JTextField encryptedPhraseTextField;
    private JButton decryptButton;
    private JTextField decryptedPhraseTextField;
    private JPanel basePanel;
    private JTextField binarySourcePhraseTextField;
    private JTextField afterAddingGammasField1;
    private JTextField gammaUnshiftedTextField;
    private JButton cleanButton;

    private String inputString;
    private String addString;
    private int[] inputArr;
    private int[] gamma;
    private int[] inputArrWithGamma;
    private int[] inputArrWithGammaShifted;
    private int[] gammaUnshifted;
    private int[] noGamma;

    public static void clearAllTextFields(){

    }

    public static boolean checkInputStringNumbers(String inputString){
        boolean foundWrongNumbers = false;
        for(int i=0; i<inputString.length(); i++)
            if((Integer.parseInt(String.valueOf(inputString.charAt(i))) == 0 ||
                    Integer.parseInt(String.valueOf(inputString.charAt(i))) ==1)== false)
                foundWrongNumbers = true;
        return foundWrongNumbers;
    }

    public void generateAddString(String inputString){
        for(int i = 8 - inputString.length(); i>0; i--)
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
            for (int i = 0; i < inputArrWithGamma.length - 3; i++)
                inputArrWithGammaShifted[i] = inputArrWithGamma[i + 3];

            for (int i = inputArrWithGamma.length - 3, n = 0; i < inputArrWithGamma.length; i++, n++)
                inputArrWithGammaShifted[i] = inputArrWithGamma[n];
    }

    public static void unshift(int[] gammaUnshifted, int[] inputArrWithGammaShifted){
            for (int n = gammaUnshifted.length - 3, i = 0; n < gammaUnshifted.length; i++, n++)
                gammaUnshifted[i] = inputArrWithGammaShifted[n];

            for (int n = 0, i = 3; n < gammaUnshifted.length - 3; i++, n++)
                gammaUnshifted[i] = inputArrWithGammaShifted[n];
    }
    
    GammaAndShift(){
        super("GammaAndShift");
        setContentPane(basePanel);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //в случае пустого ввода даем предупреждение
                checkForEmptyString:
                if(sourcePhraseTextField.getText().isEmpty()){
                    JOptionPane.showConfirmDialog(GammaAndShift.this, "Input is empty. Enter phrase to cipher");}
                else{
                    inputString = sourcePhraseTextField.getText();

                    addString = "";
                    inputArr = new int[8];
                    gamma = new int[8];
                    inputArrWithGamma = new int[8];
                    inputArrWithGammaShifted = new int[8];
                    gammaUnshifted = new int[8];
                    noGamma = new int[8];

                    for(int i=0; i<inputString.length(); i++){
                        if (Character.isAlphabetic(inputString.charAt(i))) {
                            JOptionPane.showMessageDialog(null, "input only numbers: 0 or 1", "alert",
                                    JOptionPane.ERROR_MESSAGE);
                            sourcePhraseTextField.setText("");
                            inputString = "";
                            break checkForEmptyString;
                        }

                        if(inputString.length() == 8) {
                            inputArr[i] = Integer.parseInt(String.valueOf(inputString.charAt(i)));
                        }else{
                           // JOptionPane.showMessageDialog(null, "input string is less than 8 digits", "alert",
                             //       JOptionPane.ERROR_MESSAGE);
                            generateAddString(inputString);
                            inputString = addString + inputString;
                        }
                    }

                    generateGamma(inputArr, gamma);
                    addGamma(inputArrWithGamma, inputArr, gamma);
                    shift(inputArrWithGammaShifted, inputArrWithGamma);

                    binarySourcePhraseTextField.setText(Arrays.toString(gamma));
                    afterAddingGammasField1.setText(Arrays.toString(inputArrWithGamma));
                    encryptedPhraseTextField.setText(Arrays.toString(inputArrWithGammaShifted));

                    gammaUnshiftedTextField.setText("");
                    decryptedPhraseTextField.setText("");
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourcePhraseTextField.getText().isEmpty()){
                    JOptionPane.showConfirmDialog(GammaAndShift.this, "Input is empty. Enter phrase to cipher");}
                else if(binarySourcePhraseTextField.getText().isEmpty()){
                    JOptionPane.showConfirmDialog(GammaAndShift.this, "Press encrypt to get ciphered phrase first!");
                }
                else{
                    unshift(gammaUnshifted, inputArrWithGammaShifted);
                    removeGamma(noGamma, gammaUnshifted, gamma);

                    gammaUnshiftedTextField.setText(Arrays.toString(gammaUnshifted));
                    decryptedPhraseTextField.setText(Arrays.toString(noGamma));
                }
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        sourcePhraseTextField.setDocument(new FixedSizeDocument(8));
        sourcePhraseTextField.requestFocus(); //пропадает после 1-го прогона
        //sourcePhraseTextField.setInputVerifier();
        encryptedPhraseTextField.setEditable(false);
        decryptedPhraseTextField.setEditable(false);
        binarySourcePhraseTextField.setEditable(false);
        afterAddingGammasField1.setEditable(false);
        gammaUnshiftedTextField.setEditable(false);
        inputString = sourcePhraseTextField.getText();

        this.setSize(500, 700);
        setVisible(true);
    }
}