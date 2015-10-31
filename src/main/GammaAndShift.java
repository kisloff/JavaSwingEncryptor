package main;

import javax.swing.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void clearAllTextFields(){
        sourcePhraseTextField.setText("");
        encryptedPhraseTextField.setText("");
        decryptedPhraseTextField.setText("");
        binarySourcePhraseTextField.setText("");
        afterAddingGammasField1.setText("");
        gammaUnshiftedTextField.setText("");
        sourcePhraseTextField.requestFocus();
    }

    GammaAndShift(){
        super("GammaAndShift");
        setContentPane(basePanel);
        Math math = new Math();

        encryptButton.addActionListener(e -> {
                try {
                    if (sourcePhraseTextField.getText().isEmpty()) {
                        throw new IllegalArgumentException("1");
                    }

                    math.setInputString(sourcePhraseTextField.getText());

                    for (int i = 0; i < math.getInputStringLength(); i++) {
                        if (!Character.isDigit(math.getInputString().charAt(i))) {
                            throw new IllegalArgumentException("2");
                        }
                    }

                    Pattern p = Pattern.compile("[2-9]");
                    Matcher m = p.matcher(math.getInputString());

                    if (m.find())
                        throw new IllegalArgumentException("3");

                    //add check class
                    if(math.getInputStringLength() == math.getSIZE()) {
                        for (int i = 0; i < math.getInputStringLength(); i++){
                            int val = Integer.parseInt(String.valueOf(math.getInputString().charAt(i)));
                            math.setInputArrElements(i, val);
                        }
                    }else{
                        //math.generateAddString();
                         math.setInputString(math.generateAddString()+math.getInputString());
                        for (int i = 0; i < math.getInputStringLength(); i++){
                            int val = Integer.parseInt(String.valueOf(math.getInputString().charAt(i)));
                            math.setInputArrElements(i, val);
                        }
                    }

                    math.generateGamma();
                    math.addGamma();
                    math.shift();
                    math.setInputString("");

                    binarySourcePhraseTextField.setText(Arrays.toString(math.getGamma()));
                    afterAddingGammasField1.setText(Arrays.toString(math.getInputArrWithGamma()));
                    encryptedPhraseTextField.setText(Arrays.toString(math.getInputArrWithGammaShifted()));

                    gammaUnshiftedTextField.setText("");
                    decryptedPhraseTextField.setText("");

                }
                catch (IllegalArgumentException ex){
                    if(ex.getMessage().equals("1")){
                        JOptionPane.showMessageDialog(null, "Input is empty. Enter phrase to cipher. Input only " +
                                "numbers: 0 or 1", "alert", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(ex.getMessage().equals("2")) {
                        JOptionPane.showMessageDialog(null, "Input includes letters. input only numbers: 0 or 1",
                                "alert", JOptionPane.INFORMATION_MESSAGE);
                        math.setInputString("");
                        clearAllTextFields();
                    }
                    else if(ex.getMessage().equals("3")){
                        JOptionPane.showMessageDialog(null, "wrong digits. input only numbers: 0 or 1", "alert",
                                JOptionPane.INFORMATION_MESSAGE);
                        math.setInputString("");
                        clearAllTextFields();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Неизвестная АШЫПКА!!! Караул!!!", "alert",
                                JOptionPane.INFORMATION_MESSAGE);
                        math.setInputString("");
                        clearAllTextFields();
                    }
                }
        });

        decryptButton.addActionListener(e-> {
            if (sourcePhraseTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Input is empty. Enter phrase to cipher", "info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (binarySourcePhraseTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Press encrypt to get ciphered phrase first!", "info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                math.unshift();
                math.removeGamma();

                gammaUnshiftedTextField.setText(Arrays.toString(math.getGammaUnshifted()));
                decryptedPhraseTextField.setText(Arrays.toString(math.getNoGamma()));
            }
        });

        cleanButton.addActionListener( e -> clearAllTextFields());

        sourcePhraseTextField.setDocument(new FixedSizeDocument(math.getSIZE()));
        encryptedPhraseTextField.setEditable(false);
        decryptedPhraseTextField.setEditable(false);
        binarySourcePhraseTextField.setEditable(false);
        afterAddingGammasField1.setEditable(false);
        gammaUnshiftedTextField.setEditable(false);

        this.setSize(500, 700);
        setVisible(true);
    }
}