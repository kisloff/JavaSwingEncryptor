package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

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

    public void clearAllTextFields(){
        sourcePhraseTextField.setText("");
        encryptedPhraseTextField.setText("");
        decryptedPhraseTextField.setText("");
        binarySourcePhraseTextField.setText("");
        afterAddingGammasField1.setText("");
        gammaUnshiftedTextField.setText("");
        sourcePhraseTextField.requestFocus();
    }

    public void generateAddString(String inputString){
        for(int i = (8 - inputString.length()); i>0; i--)
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

            for (int n = 0, i = 3; n < gammaUnshifted.length - MOVE; i++, n++)
                gammaUnshifted[i] = inputArrWithGammaShifted[n];
    }
    
    GammaAndShift(){
        super("GammaAndShift");
        setContentPane(basePanel);

        addString = "";
        inputArr = new int[SIZE];
        gamma = new int[SIZE];
        inputArrWithGamma = new int[SIZE];
        inputArrWithGammaShifted = new int[SIZE];
        gammaUnshifted = new int[SIZE];
        noGamma = new int[SIZE];

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                again:
                try {
                    if (sourcePhraseTextField.getText().isEmpty()) {
                        throw new IllegalArgumentException("1");
                    }
                    //sourcePhraseTextField.requestFocus();
                    inputString = sourcePhraseTextField.getText();

                    for (int i = 0; i < inputString.length(); i++) {
                        if (!Character.isDigit(inputString.charAt(i))) {
                            throw new IllegalArgumentException("2");
                        }
                    }
                    if (inputString.matches("[23456789]+"))
                        throw new IllegalArgumentException("3");

                    /*if (inputString.matches("[01]+"))
                        throw new IllegalArgumentException("4");*/

                    if(inputString.length() == SIZE) {
                        for (int i = 0; i < inputString.length(); i++)
                            inputArr[i] = Integer.parseInt(String.valueOf(inputString.charAt(i)));
                    }else{
                        generateAddString(inputString);
                        inputString = addString + inputString;
                        for (int i = 0; i < SIZE; i++)
                            inputArr[i] = Integer.parseInt(String.valueOf(inputString.charAt(i)));
                    }

                    generateGamma(inputArr, gamma);
                    addGamma(inputArrWithGamma, inputArr, gamma);
                    shift(inputArrWithGammaShifted, inputArrWithGamma);
                    addString = "";

                    binarySourcePhraseTextField.setText(Arrays.toString(gamma));
                    afterAddingGammasField1.setText(Arrays.toString(inputArrWithGamma));
                    encryptedPhraseTextField.setText(Arrays.toString(inputArrWithGammaShifted));

                    gammaUnshiftedTextField.setText("");
                    decryptedPhraseTextField.setText("");

                }
                catch (IllegalArgumentException ex){
                    if(ex.getMessage() == "1"){
                    JOptionPane.showMessageDialog(null, "Input is empty. Enter phrase to cipher. Input only numbers: " +
                                    "0 or 1", "alert", JOptionPane.INFORMATION_MESSAGE);
                    //break again;
                    }
                    else if(ex.getMessage() == "2") {
                        JOptionPane.showMessageDialog(null, "Input includes letters. input only numbers: 0 or 1", "alert",
                                JOptionPane.INFORMATION_MESSAGE);
                        inputString = "";
                        clearAllTextFields();
                       // break again;
                    }
                    else if(ex.getMessage() == "3"){
                        JOptionPane.showMessageDialog(null, "wrong digits. input only numbers: 0 or 1", "alert",
                                JOptionPane.INFORMATION_MESSAGE);
                        inputString = "";
                        clearAllTextFields();
                       // break again;
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Неизвестная АШЫПКА!!! Караул!!!", "alert",
                                JOptionPane.INFORMATION_MESSAGE);
                        inputString = "";
                        clearAllTextFields();
                    }
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sourcePhraseTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Input is empty. Enter phrase to cipher", "info",
                            JOptionPane.INFORMATION_MESSAGE);}

                else if(binarySourcePhraseTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Press encrypt to get ciphered phrase first!", "info",
                            JOptionPane.INFORMATION_MESSAGE);
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
                clearAllTextFields();
            }
        });

        sourcePhraseTextField.setDocument(new FixedSizeDocument(SIZE));
        sourcePhraseTextField.requestFocus();
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