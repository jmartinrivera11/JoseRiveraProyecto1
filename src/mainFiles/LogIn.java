
package mainFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LogIn extends JFrame {
    
    MenuInicio menuInicio;
    
    private JTextField username;
    private JPasswordField pass;
    
    public LogIn(MenuInicio menu) {
        menuInicio = menu;
        initUI();
    }
    
    private void initUI() {
        setTitle("Inicio de Sesión");
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 30, 100, 30);
        panel.add(usernameLabel);
        
        username = new JTextField(20);
        username.setBounds(160, 30, 180, 30);
        panel.add(username);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 80, 100, 30);
        panel.add(passwordLabel);
        
        pass = new JPasswordField(20);
        pass.setBounds(160, 80, 180, 30);
        panel.add(pass);
        
        JButton entrarButton = new JButton("ENTRAR");
        entrarButton.setBackground(new Color(0, 48, 143));
        entrarButton.setForeground(Color.WHITE);
        entrarButton.setBounds(50, 130, 120, 40);
        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                entrarButtonActionPerformed();
            }
        });
        panel.add(entrarButton);
        
        JButton salirButton = new JButton("SALIR");
        salirButton.setBackground(new Color(224, 48, 30));
        salirButton.setForeground(Color.WHITE);
        salirButton.setBounds(200, 130, 120, 40);
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirButtonActionPerformed();
            }
        });
        panel.add(salirButton);
        
        add(panel);
        setVisible(true);
    }
    
    private void entrarButtonActionPerformed() {
        String name = username.getText();
        String password = new String(pass.getPassword());
        
        if (!menuInicio.usuarios.checkName(name)) {
            JOptionPane.showMessageDialog(null, "El usuario no existe o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (menuInicio.usuarios.checkPass(name, password)) {
                menuInicio.logged = name;
                MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
                menuPrincipal.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void salirButtonActionPerformed() {
        menuInicio.setVisible(true);
        dispose();
    }
}
