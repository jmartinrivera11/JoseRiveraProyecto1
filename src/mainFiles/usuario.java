
package mainFiles;

public class usuario {
  
    String nombre, contra;
    int puntos;

    public usuario(String nombre, String contra) {
        this.nombre = nombre;
        this.contra = contra;
        puntos = 0;
    }
    
    public String getContra() {
        return contra;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void setPuntos (int puntos) {
        this.puntos = puntos;
    }
    
    public String print(String name) {
        String text = "";
        if (name != null)
            text = "Username: " + name + " - Puntos: " + puntos;
        return text;
    }
}
