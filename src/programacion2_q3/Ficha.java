
package programacion2_q3;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Ficha extends FichaBase {
    
    public Ficha(ImageIcon icono, int rango, Bando bando, ImageIcon ocultoIcon) {
        super(icono, rango, bando, ocultoIcon);
    }
    
    @Override
    public void setVisibleIcono(JButton boton, boolean visible) {
        if (visible) {
            boton.setIcon(icono);
        } else {
            boton.setIcon(ocultoIcon);
        }
        boton.revalidate();
        boton.repaint();
    }
    
    public boolean esVillano() {
        return bando == Bando.VILLANO;
    }
}
