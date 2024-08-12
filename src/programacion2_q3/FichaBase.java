
package programacion2_q3;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class FichaBase {
    protected ImageIcon icono;
    protected int rango;
    protected Bando bando;
    protected ImageIcon ocultoIcon;

    public FichaBase(ImageIcon icono, int rango, Bando bando, ImageIcon ocultoIcon) {
        this.icono = icono;
        this.rango = rango;
        this.bando = bando;
        this.ocultoIcon = ocultoIcon;
    }

    public ImageIcon getIcono() {
        return icono;
    }

    public int getRango() {
        return rango;
    }

    public Bando getBando() {
        return bando;
    }

    public abstract void setVisibleIcono(JButton boton, boolean visible);
}
