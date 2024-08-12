
package mainFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;

public class MenuInicio extends JFrame implements ActionListener {
    
    public Gestion usuarios;
    public String logged, selected;
    public String loggedType, selectedType;
    public ArrayList<String> logs = new ArrayList<>();
    
    private JPanel panelMenu;
    private JButton logInBoton;
    private JButton crearPlayerBoton;
    private JButton salirBoton;
    
    public MenuInicio() {
        
        usuarios = new Gestion();
        
        setTitle("Menu Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        
        panelMenu = new JPanel();
        panelMenu.setLayout(null); 
        panelMenu.setBackground(new Color(160, 160, 160));
        
        logInBoton = new JButton("Iniciar Sesión");
        crearPlayerBoton = new JButton("Crear Jugador");
        salirBoton = new JButton("Salir");
        
        logInBoton.setBackground(new Color(0, 48, 143));
        logInBoton.setForeground(Color.WHITE);
        
        crearPlayerBoton.setBackground(new Color(85, 153, 0));
        crearPlayerBoton.setForeground(Color.WHITE);
        
        salirBoton.setBackground(new Color(224, 48, 30));
        salirBoton.setForeground(Color.WHITE);
        
        logInBoton.setBounds(100, 30, 200, 40);
        crearPlayerBoton.setBounds(100, 90, 200, 40);
        salirBoton.setBounds(100, 150, 200, 40);
        
        logInBoton.addActionListener(this);
        crearPlayerBoton.addActionListener(this);
        salirBoton.addActionListener(this);
        
        panelMenu.add(logInBoton);
        panelMenu.add(crearPlayerBoton);
        panelMenu.add(salirBoton);
        
        add(panelMenu);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInBoton) {
            LogIn loginForm = new LogIn(this);
            loginForm.setVisible(true);
            this.dispose();
            
        } else if (e.getSource() == crearPlayerBoton) {
            CrearPlayer crearPlayerForm = new CrearPlayer(this);
            crearPlayerForm.setVisible(true);
            this.dispose();
            
        } else if (e.getSource() == salirBoton) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new MenuInicio();
    }
}
