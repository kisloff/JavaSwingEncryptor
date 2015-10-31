package main;

import javax.swing.*;
import java.util.Arrays;

public class EncryptionFrame extends JFrame {

    public final static String HEADER = "Encryption";
    public final static String EMPTY_INPUT_MESSAGE =
            "Input is empty. Enter phrase to cipher.";
    public final static String WRONG_INPUT_MESSAGE = "Input is not binary. 0 or 1 are only allowed.";
    public final static String ALERT = "Alert";

    private JPanel basePanel;

    private JTextField inputTextField;
    private JButton encryptButton;
    private JTextField gammaTextField;
    private JTextField afterAddingGammasField;
    private JTextField encryptedPhraseTextField;
    private JButton decryptButton;
    private JTextField gammaUnshiftedTextField;
    private JTextField decryptedPhraseTextField;
    private JButton cleanButton;

    private int[] encryptedInput;
    private int[] gamma;

    EncryptionFrame() {
        super(HEADER);
        setContentPane(basePanel);

        inputTextField.setDocument(new FixedSizeDocument(Math.SIZE));
        encryptedPhraseTextField.setEditable(false);
        decryptedPhraseTextField.setEditable(false);
        gammaTextField.setEditable(false);
        afterAddingGammasField.setEditable(false);
        gammaUnshiftedTextField.setEditable(false);

        this.setSize(500, 700);
        setVisible(true);

        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
        cleanButton.addActionListener(e -> clearAllTextFields());
    }

    private void encrypt(){
        clearAllExceptInput();
        String input = inputTextField.getText();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, EMPTY_INPUT_MESSAGE, ALERT, JOptionPane.INFORMATION_MESSAGE);
        }

        if (!Math.isBinary(input)) {
            JOptionPane.showMessageDialog(null, WRONG_INPUT_MESSAGE, ALERT, JOptionPane.INFORMATION_MESSAGE);
        }

        String completedString = Math.completeString(input);
        int[] inputArray = Math.convertStringToArray(completedString);
        gamma = Math.generateGamma();
        int[] sum = Math.sumArrays(inputArray, gamma);
        encryptedInput = Math.shift(sum);

        gammaTextField.setText(Arrays.toString(gamma));
        afterAddingGammasField.setText(Arrays.toString(sum));
        encryptedPhraseTextField.setText(Arrays.toString(encryptedInput));
    }

    private void decrypt(){

        int[] unshiftedInput = Math.unshift(encryptedInput);
        int[] decrypted = Math.removeGamma(unshiftedInput ,gamma);

        gammaUnshiftedTextField.setText(Arrays.toString(unshiftedInput));
        decryptedPhraseTextField.setText(Arrays.toString(decrypted));
    }

    private void clearAllTextFields() {
        inputTextField.setText("");
        encryptedPhraseTextField.setText("");
        decryptedPhraseTextField.setText("");
        gammaTextField.setText("");
        afterAddingGammasField.setText("");
        gammaUnshiftedTextField.setText("");
        inputTextField.requestFocus();
    }

    private void clearAllExceptInput() {
        encryptedPhraseTextField.setText("");
        decryptedPhraseTextField.setText("");
        gammaTextField.setText("");
        afterAddingGammasField.setText("");
        gammaUnshiftedTextField.setText("");
        inputTextField.requestFocus();
    }
}