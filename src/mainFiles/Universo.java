
package mainFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Universo extends JFrame {
    
    MenuInicio menuInicio;
    
    public Universo(MenuInicio menu) {
        menuInicio = menu;
        initUI();
    }
    
    private void initUI() {
        setTitle("Universo Marvel");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        JButton rankingButton = new JButton("RANKING");
        JButton batallasButton = new JButton("JUGADORES");
        JButton salirButton = new JButton("Salir");
        
        rankingButton.setBackground(new Color(0, 48, 143));
        rankingButton.setForeground(Color.WHITE);
        
        batallasButton.setBackground(new Color(128, 0, 128));
        batallasButton.setForeground(Color.WHITE);
        
        salirButton.setBackground(new Color(224, 48, 30));
        salirButton.setForeground(Color.WHITE);
        
        rankingButton.setBounds(100, 50, 200, 40);
        batallasButton.setBounds(100, 110, 200, 40);
        salirButton.setBounds(100, 170, 200, 40);
        
        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuInicio.usuarios.ranking();
            }
        });
        
        batallasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuInicio.usuarios.batallas();
            }
        });
        
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal mp = new MenuPrincipal(menuInicio);
                mp.setVisible(true);
                dispose();
            }
        });
        
        panel.add(rankingButton);
        panel.add(batallasButton);
        panel.add(salirButton);
        
        add(panel);
        setVisible(true);
    }
}
