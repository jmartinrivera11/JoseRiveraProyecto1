
package mainFiles;

import javax.swing.*;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearPlayer extends JFrame {
    
    MenuInicio menuInicio;
    
    private JTextField textUsername;
    private JTextField textPassword;
    
    public CrearPlayer(MenuInicio menu) {
        menuInicio = menu;
        initUI();
    }
    
    private void initUI() {
        setTitle("Crear Jugador");
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        JLabel usernameLabel = new JLabel("Ingresar username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 30, 150, 30);
        panel.add(usernameLabel);
        
        textUsername = new JTextField(20);
        textUsername.setBounds(210, 30, 150, 30);
        panel.add(textUsername);
        
        JLabel passwordLabel = new JLabel("Ingresar contraseña (5 caracteres):");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 80, 200, 30);
        panel.add(passwordLabel);
        
        textPassword = new JTextField(20);
        textPassword.setBounds(250, 80, 110, 30);
        panel.add(textPassword);
        
        JButton ingresarButton = new JButton("INGRESAR");
        ingresarButton.setBackground(new Color(0, 48, 143));
        ingresarButton.setForeground(Color.WHITE);
        ingresarButton.setBounds(130, 140, 120, 40);
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton1ActionPerformed();
            }
        });
        panel.add(ingresarButton);
        
        add(panel);
        setVisible(true);
    }
    
    private void jButton1ActionPerformed() {
        String nombre = textUsername.getText();
        String password = textPassword.getText();
        
        if (password.length() != 5) {
            JOptionPane.showMessageDialog(null, "La contraseña tiene que tener exactamente 5 caracteres", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (menuInicio.usuarios.addUser(nombre, password)) {
                JOptionPane.showMessageDialog(null, "Usuario agregado", null, JOptionPane.INFORMATION_MESSAGE);
                menuInicio.setVisible(true);
                textUsername.setText("");
                textPassword.setText("");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
