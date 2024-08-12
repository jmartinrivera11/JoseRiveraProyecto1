
package mainFiles;

import programacion2_q3.Tablero;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;

public class PickSide {
    
    MenuInicio menuInicio;
    
    public PickSide(MenuInicio menu) {
        menuInicio = menu;
        
        JFrame frame = new JFrame("Escoger bando");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        JButton heroe = new JButton("HEROES");
        heroe.setBackground(new Color(0, 48, 143));
        heroe.setForeground(Color.WHITE);
        heroe.setBounds(50, 55, 120, 40);
        heroe.addActionListener((ActionEvent e) -> {
            menuInicio.loggedType = "Heroe";
            menuInicio.selectedType = "Villano";
            new Tablero(menuInicio);
            frame.dispose();
        });
        panel.add(heroe);
        
        JButton villano = new JButton("VILLANOS");
        villano.setBackground(new Color(224, 48, 30));
        villano.setForeground(Color.WHITE);
        villano.setBounds(230, 55, 120, 40);
        villano.addActionListener((ActionEvent e) -> {
            menuInicio.loggedType = "Villano";
            menuInicio.selectedType = "Heroe";
            new Tablero(menuInicio);
            frame.dispose();
        });
        panel.add(villano);
        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
