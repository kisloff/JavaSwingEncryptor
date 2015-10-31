package main.view;

import main.encryption.Decryptor;
import main.encryption.EncryptionProtocol;
import main.encryption.Encryptor;
import main.utils.StringUtils;

import javax.swing.*;
import java.util.Arrays;

public class EncryptionFrame extends JFrame {

    public final static String HEADER = "Encryption";
    public final static String EMPTY_INPUT_MESSAGE = "Input is empty. Enter phrase to cipher.";
    public final static String WRONG_INPUT_MESSAGE = "Input is not binary. 0 or 1 are only allowed.";
    public final static String ALERT = "Alert";

    private JPanel basePanel;
    private JButton generateGammaButton;
    private JTextField gammaTextField;
    private JTextField inputTextField;
    private JButton encryptButton;
    private JTextField encryptedPhraseTextField;
    private JButton decryptButton;
    private JTextField decryptedPhraseTextField;
    private JButton cleanButton;

    public EncryptionFrame() {
        super(HEADER);
        setContentPane(basePanel);
        this.setSize(500, 700);
        setVisible(true);

        gammaTextField.setEditable(false);
        inputTextField.setDocument(new FixedSizeDocument(EncryptionProtocol.SIZE));
        encryptedPhraseTextField.setEditable(false);
        decryptedPhraseTextField.setEditable(false);

        generateGammaButton.addActionListener(e -> generateGamma());
        encryptButton.addActionListener(e -> encrypt());
        decryptButton.addActionListener(e -> decrypt());
        cleanButton.addActionListener(e -> clearAllTextFields());
    }

    private void generateGamma(){
        EncryptionProtocol.generateGamma();
        String gamma = Arrays.toString(EncryptionProtocol.gamma);
        gammaTextField.setText(gamma);
    }

    private void encrypt(){
        clearAllExceptInput();
        String input = inputTextField.getText();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(null, EMPTY_INPUT_MESSAGE, ALERT, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!StringUtils.isBinary(input)) {
            JOptionPane.showMessageDialog(null, WRONG_INPUT_MESSAGE, ALERT, JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String encrypted = Encryptor.encrypt(input);

        gammaTextField.setText(Arrays.toString(EncryptionProtocol.gamma));
        encryptedPhraseTextField.setText(encrypted);
    }

    private void decrypt(){
        String encrypted = encryptedPhraseTextField.getText();
        encrypted = StringUtils.cleanString(encrypted);
        String decrypted = Decryptor.decrypt(encrypted);
        decryptedPhraseTextField.setText(decrypted);
    }

    private void clearAllTextFields() {
        clearAllExceptInput();
        inputTextField.setText("");
    }

    private void clearAllExceptInput() {
        encryptedPhraseTextField.setText("");
        decryptedPhraseTextField.setText("");
        gammaTextField.setText("");
        inputTextField.requestFocus();
    }
}