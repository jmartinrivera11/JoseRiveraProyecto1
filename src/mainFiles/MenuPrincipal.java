
package mainFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;

public class MenuPrincipal extends JFrame implements ActionListener {
    
    MenuInicio menuInicio;
    
    private JButton nuevaPartidaBoton;
    private JButton cuentaBoton;
    private JButton logoutBoton;
    private JButton universoBoton;
    
    public MenuPrincipal(MenuInicio menu) {
        menuInicio = menu;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Menú Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        nuevaPartidaBoton = new JButton("Nueva Partida");
        cuentaBoton = new JButton("Mi Perfil");
        logoutBoton = new JButton("Cerrar Sesión");
        universoBoton = new JButton("Universo Marvel");
        
        nuevaPartidaBoton.setBackground(new Color(0, 48, 143));
        nuevaPartidaBoton.setForeground(Color.WHITE);
        
        cuentaBoton.setBackground(new Color(85, 153, 0));
        cuentaBoton.setForeground(Color.WHITE);
        
        logoutBoton.setBackground(new Color(224, 48, 30));
        logoutBoton.setForeground(Color.WHITE);
        
        universoBoton.setBackground(new Color(128, 0, 128));
        universoBoton.setForeground(Color.WHITE);
        
        Dimension buttonSize = new Dimension(200, 40);
        nuevaPartidaBoton.setPreferredSize(buttonSize);
        cuentaBoton.setPreferredSize(buttonSize);
        logoutBoton.setPreferredSize(buttonSize);
        universoBoton.setPreferredSize(buttonSize);
        
        nuevaPartidaBoton.addActionListener(this);
        cuentaBoton.addActionListener(this);
        logoutBoton.addActionListener(this);
        universoBoton.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        nuevaPartidaBoton.setBounds(100, 30, 200, 40);
        cuentaBoton.setBounds(100, 90, 200, 40);
        universoBoton.setBounds(100, 150, 200, 40);
        logoutBoton.setBounds(100, 210, 200, 40);
        
        panel.add(nuevaPartidaBoton);
        panel.add(cuentaBoton);
        panel.add(universoBoton);
        panel.add(logoutBoton);
        
        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nuevaPartidaBoton) {
            if (menuInicio.usuarios.contarUser() >= 2) {
                PickUser pick = new PickUser(menuInicio);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Se necesitan 2 o mas jugadores para iniciar una partida", 
                        null, JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else if (e.getSource() == cuentaBoton) {
            InfoCuenta infoCuenta = new InfoCuenta(menuInicio);
            infoCuenta.setVisible(true);
            this.dispose();
            
        } else if (e.getSource() == logoutBoton) {
            int input = JOptionPane.showConfirmDialog(null, "¿Estás seguro?");
            if (input == 0) {
                menuInicio.setVisible(true);
                this.dispose();
            }
            
        } else if (e.getSource() == universoBoton) {
            Universo u = new Universo(menuInicio);
            u.setVisible(true);
            this.dispose();
        }
    }
}
