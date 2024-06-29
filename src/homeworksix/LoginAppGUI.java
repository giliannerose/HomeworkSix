package homeworksix;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginAppGUI extends JFrame {
    private static final String CREDENTIALS_FILE = "credentials.tsv";
    private static Map<String, String> credentialsMap = new HashMap<>();

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginAppGUI() {
        setTitle("Login Application");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadCredentials();

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new LoginButtonListener());
        panel.add(loginButton);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(messageLabel);

        add(panel);
    }

    private void loadCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split("\t");
                if (credentials.length == 2) {
                    credentialsMap.put(credentials[0], credentials[1]);
                }
            }
        } catch (IOException e) {
            messageLabel.setText("Error reading credentials file.");
        }
    }

    private boolean authenticate(String username, String password) {
        return credentialsMap.containsKey(username) && credentialsMap.get(username).equals(password);
    }

  private class LoginButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticate(username, password)) {
            messageLabel.setText("Login successful! Welcome, " + username + ".");
            
            // Instantiate and show EmployeeInfoGUI
            SwingUtilities.invokeLater(() -> {
                EmployeeInfoGUI employeeInfoGUI = new EmployeeInfoGUI();
                employeeInfoGUI.main(null); // Ensure the EmployeeInfoGUI main method initializes correctly
            });
            
            // Close or hide LoginAppGUI
            setVisible(false); // or dispose() to close
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginAppGUI loginApp = new LoginAppGUI();
            loginApp.setVisible(true);
        });
    }
}
