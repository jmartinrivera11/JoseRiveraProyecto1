
package mainFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class InfoCuenta extends JFrame implements ActionListener {
    
    MenuInicio menuInicio;
    
    private JButton cambiarPassBoton;
    private JButton eliminarCuentaBoton;
    private JButton verLogsBoton;
    private JButton salirBoton;
    
    public InfoCuenta (MenuInicio menu) {
        menuInicio = menu;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Información Cuenta");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(160, 160, 160));
        
        cambiarPassBoton = new JButton("Cambiar Contraseña");
        eliminarCuentaBoton = new JButton("Eliminar Cuenta");
        verLogsBoton = new JButton("Ver Logs");
        salirBoton = new JButton("Salir");
        
        cambiarPassBoton.setBackground(new Color(0, 48, 143));
        cambiarPassBoton.setForeground(Color.WHITE);
        
        eliminarCuentaBoton.setBackground(new Color(224, 48, 30));
        eliminarCuentaBoton.setForeground(Color.WHITE);
        
        verLogsBoton.setBackground(new Color(128, 0, 128));
        verLogsBoton.setForeground(Color.WHITE);
        
        salirBoton.setBackground(new Color(85, 153, 0));
        salirBoton.setForeground(Color.WHITE);
        
        cambiarPassBoton.setBounds(100, 30, 200, 40);
        eliminarCuentaBoton.setBounds(100, 90, 200, 40);
        verLogsBoton.setBounds(100, 150, 200, 40);
        salirBoton.setBounds(100, 210, 200, 40);
        
        cambiarPassBoton.addActionListener(this);
        eliminarCuentaBoton.addActionListener(this);
        verLogsBoton.addActionListener(this);
        salirBoton.addActionListener(this);
        
        panel.add(cambiarPassBoton);
        panel.add(eliminarCuentaBoton);
        panel.add(verLogsBoton);
        panel.add(salirBoton);
        
        add(panel);
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cambiarPassBoton) {
            String name = JOptionPane.showInputDialog("Nombre de usuario");
            String oldPass = JOptionPane.showInputDialog("Contraseña actual");
            
            if (!menuInicio.usuarios.checkName(name)) {
                JOptionPane.showMessageDialog(null, "El usuario no existe "
                        + "o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (menuInicio.usuarios.checkPass(name, oldPass)) {
                    String newPass = JOptionPane.showInputDialog("Nueva contraseña");
                    for (int i = 0; i < menuInicio.usuarios.registro.length; i++) {
                        if (menuInicio.usuarios.registro[i] != null) {
                            if (name.equals(menuInicio.usuarios.registro[i].nombre) 
                                    && oldPass.equals(menuInicio.usuarios.registro[i].contra)) {
                                menuInicio.usuarios.registro[i].contra = newPass;
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña"
                            + "incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } else if (e.getSource() == eliminarCuentaBoton) {
            int input = JOptionPane.showConfirmDialog(null, "¿Desea continuar?");
        
            if (input == 0) {
                String name = JOptionPane.showInputDialog("Ingresar nombre de usuario");
                String oldPass = JOptionPane.showInputDialog("Ingresar contraseña");

                if (!menuInicio.usuarios.checkName(name)) {
                    JOptionPane.showMessageDialog(null, "El usuario no existe "
                        + "o esta ingresado incorrectamente", null, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (menuInicio.usuarios.checkPass(name, oldPass)) {
                        for (int i = 0; i < menuInicio.usuarios.registro.length; i++) {
                            if (menuInicio.usuarios.registro[i] != null) {
                                if (name.equals(menuInicio.usuarios.registro[i].nombre) 
                                        && oldPass.equals(menuInicio.usuarios.registro[i].contra)) {
                                    menuInicio.usuarios.registro[i].nombre = null;
                                    menuInicio.usuarios.registro[i].contra = null;
                                    menuInicio.usuarios.eliminado += 1;
                                    menuInicio.setVisible(true);
                                    this.dispose();
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Contraseña"
                                + " incorrecta", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            
        } else if (e.getSource() == verLogsBoton) {
            String logs = "";
            for (String str : menuInicio.logs) {
                if (str.contains(menuInicio.logged))
                    logs += str + "\n";
            }
            
            JOptionPane.showMessageDialog(null, logs, 
                "Ultimos logs", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (e.getSource() == salirBoton) {
            MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
            menuPrincipal.setVisible(true);
            this.dispose();
        }
    }
}
