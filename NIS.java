import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NIS extends JFrame {
    private JTextField shiftField;
    private JTextField keyField;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JComboBox<String> encryptionMethod;

    public NIS() {
        setTitle("Encryption and Decryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 550);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY); // Set background color

        JLabel messageLabel = new JLabel("Message:", SwingConstants.LEFT);
        messageLabel.setBounds(20, 20, 100, 30);
        panel.add(messageLabel);

        inputArea = new JTextArea();
        inputArea.setBounds(130, 20, 240, 100);
        panel.add(inputArea);

        JLabel methodLabel = new JLabel("Encryption Method:", SwingConstants.LEFT);
        methodLabel.setBounds(20, 140, 150, 30);
        panel.add(methodLabel);

        encryptionMethod = new JComboBox<>(new String[]{"Caesar Cipher", "Vernam Cipher"});
        encryptionMethod.setBounds(180, 140, 190, 30);
        panel.add(encryptionMethod);

        JLabel shiftLabel = new JLabel("Shift (Caesar Cipher):", SwingConstants.LEFT);
        shiftLabel.setBounds(20, 180, 150, 30);
        panel.add(shiftLabel);

        shiftField = new JTextField();
        shiftField.setBounds(180, 180, 190, 30);
        panel.add(shiftField);

        JLabel keyLabel = new JLabel("Key (Vernam Cipher):", SwingConstants.LEFT);
        keyLabel.setBounds(20, 220, 150, 30);
        panel.add(keyLabel);

        keyField = new JTextField();
        keyField.setBounds(180, 220, 190, 30);
        panel.add(keyField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(100, 280, 100, 30);
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(220, 280, 100, 30);
        panel.add(decryptButton);

        JLabel outputLabel = new JLabel("Output:", SwingConstants.LEFT);
        outputLabel.setBounds(20, 330, 100, 30);
        panel.add(outputLabel);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBounds(20, 370, 350, 100);
        panel.add(outputArea);

        add(panel);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputArea.getText();
                String encryptionMethodSelected = (String) encryptionMethod.getSelectedItem();

                if (encryptionMethodSelected.equals("Caesar Cipher")) {
                    int shift = Integer.parseInt(shiftField.getText());
                    String encryptedMessage = encryptCaesar(message, shift);
                    outputArea.setText(encryptedMessage);
                } else if (encryptionMethodSelected.equals("Vernam Cipher")) {
                    String key = keyField.getText();
                    String encryptedMessage = vernamEncrypt(message, key);
                    outputArea.setText(encryptedMessage);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String encryptedMessage = inputArea.getText();
                String encryptionMethodSelected = (String) encryptionMethod.getSelectedItem();

                if (encryptionMethodSelected.equals("Caesar Cipher")) {
                    int shift = Integer.parseInt(shiftField.getText());
                    String decryptedMessage = decryptCaesar(encryptedMessage, shift);
                    outputArea.setText(decryptedMessage);
                } else if (encryptionMethodSelected.equals("Vernam Cipher")) {
                    String key = keyField.getText();
                    String decryptedMessage = vernamDecrypt(encryptedMessage, key);
                    outputArea.setText(decryptedMessage);
                }
            }
        });
    }

    private String encryptCaesar(String message, int shift) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (Character.isUpperCase(c)) {
                encryptedMessage.append((char) ('A' + (c - 'A' + shift) % 26));
            } else if (Character.isLowerCase(c)) {
                encryptedMessage.append((char) ('a' + (c - 'a' + shift) % 26));
            } else {
                encryptedMessage.append(c);
            }
        }

        return encryptedMessage.toString();
    }

    private String decryptCaesar(String encryptedMessage, int shift) {
        return encryptCaesar(encryptedMessage, 26 - shift);
    }

    private String vernamEncrypt(String message, String key) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char encryptedChar = (char) (message.charAt(i) ^ key.charAt(i % key.length()));
            encryptedMessage.append(encryptedChar);
        }

        return encryptedMessage.toString();
    }

    private String vernamDecrypt(String encryptedMessage, String key) {
        return vernamEncrypt(encryptedMessage, key); // Vernam cipher is symmetrical
    }

    public static void main(String[] args) {
        NIS gui = new NIS();
        gui.setVisible(true);
    }
}
