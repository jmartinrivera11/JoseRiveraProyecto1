
package mainFiles;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PickUser {
    
    private JComboBox<String> comboBox;
    MenuInicio menuInicio;
    PickSide pickSide;
    
    public PickUser(MenuInicio menu) {
        menuInicio = menu;
        
        JFrame frame = new JFrame("Escoger oponente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        comboBox = new JComboBox<>(menuInicio.usuarios.userList(menuInicio.logged));
        comboBox.setBounds(50, 50, 300, 30);
        panel.add(comboBox);
        
        JButton aceptar = new JButton("Aceptar");
        aceptar.setBackground(new Color(0, 48, 143));
        aceptar.setForeground(Color.WHITE);
        aceptar.setBounds(140, 100, 120, 40);
        aceptar.addActionListener((ActionEvent e) -> {
            menuInicio.selected = (String) comboBox.getSelectedItem();
            pickSide = new PickSide(menuInicio);
            frame.dispose();
        });
        panel.add(aceptar);
        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    
    public String getSelected() {
        return menuInicio.selected;
    }
}
