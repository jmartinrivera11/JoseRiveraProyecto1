
package mainFiles;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import javax.swing.JOptionPane;

public class Gestion implements Checking{
    
    usuario[] registro;
    String[] userArray;
    int eliminado;
    int puntos = 0;
    
    public Gestion() {
        registro = new usuario[10];
        userArray = new String[10];
        eliminado = 0;
    }
    
    @Override
    public boolean checkName(String nombre) {
        for (usuario user : registro) {
            if (user != null)
                if (nombre.equals(user.getNombre()))
                    return true;
        }
        return false;
    }
    
    @Override
    public boolean checkPass(String nombre, String contra) {
        for (int i = 0; i < registro.length ; i++){
            if (registro[i] != null)
                if (nombre.equals(registro[i].nombre) 
                        && contra.equals(registro[i].contra))
                    return true;
        }
        return false;
    }
    
    public boolean addUser(String nombre, String contra){
        if (!checkName(nombre))
            for (int i = 0; i < registro.length ; i++){
                if (registro[i] == null) {
                    registro[i] = new usuario(nombre, contra);
                    return true;
                }
            }
        return false;
    }
    
    public long contarUser() {
        return Arrays.stream(registro).filter(Objects::nonNull).count();
    }
    
    public String[] userList(String nombre) {
        int c = 0;
        for (int i = 0; i < registro.length ; i++){
            if (registro[i] != null && !nombre.equals(registro[i].nombre)) {
                userArray[c] = registro[i].nombre;
                c++;
            }
        }
        return userArray;
    }

    public void ranking() {
        usuario[] usuariosOrdenados = Arrays.stream(registro)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(usuario::getPuntos).reversed())
            .toArray(usuario[]::new);
        
        StringBuilder rankingTexto = new StringBuilder("Posicion - Username - Puntos\n");
        for (int i = 0; i < usuariosOrdenados.length; i++) {
            usuario user = usuariosOrdenados[i];
            rankingTexto.append(String.format("%d - %s - %d\n", i + 1, user.getNombre(), user.getPuntos()));
        }
        
        JOptionPane.showMessageDialog(null, rankingTexto.toString(), "Ranking de Jugadores", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void batallas() {
        int u = 0;
        for (int i = 0; i < registro.length ; i++){
            if (registro[i] != null)
                u++;
        }
        
        JOptionPane.showMessageDialog(null, "Usuarios activos: " + (u - eliminado) + 
                "\nUsuarios historicos: " + u +
                "\nPartidas jugadas: ", 
                "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void puntos(String nombre) {
        for (usuario user : registro) {
            if (user != null) {
                if (nombre.equals(user.getNombre())) {
                    puntos += 3;
                    user.setPuntos(puntos);
                }
            }
        }
    }
}
