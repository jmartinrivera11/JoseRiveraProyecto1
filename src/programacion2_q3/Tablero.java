
package programacion2_q3;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import mainFiles.MenuInicio;
import mainFiles.MenuPrincipal;

public class Tablero extends JPanel {
    
    MenuInicio menuInicio;
    
    private JButton[][] botones;
    private Ficha[][] fichas;
    private Random random;
    private ImageIcon ocultoIcon;
    
    private Ficha fichaSeleccionada = null;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private boolean turnoHeroe = true; //LOS HEROES EMPIEZAN
    private boolean movimientoHecho = false;
    
    private JPanel panelHeroes;
    private JPanel panelVillanos;
    private JPanel panelInfo;
    private JLabel labelRango;
    private JLabel labelBando;
    private Set<JButton> remarcados = new HashSet<>();
    
    public Tablero(MenuInicio menu) {
        menuInicio = menu;
        crearTablero();
    }
    
    private void crearTablero() {
        int sizeTablero = 700;
        int sizeBoton = sizeTablero / 10;
        int sizePanel = 150;
        int sizeInfoPanel = 80;
        
        JFrame frame = new JFrame("ESTRATEGO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(sizeTablero + 2 * sizePanel + sizeInfoPanel, sizeTablero + 100);
        frame.setLayout(new BorderLayout());
        
        panelHeroes = new JPanel();
        panelHeroes.setLayout(new BoxLayout(panelHeroes, BoxLayout.Y_AXIS));
        panelHeroes.setPreferredSize(new Dimension(sizePanel, sizeTablero));
        panelHeroes.setBorder(BorderFactory.createTitledBorder("Heroes Derrotados"));
        
        panelVillanos = new JPanel();
        panelVillanos.setLayout(new BoxLayout(panelVillanos, BoxLayout.Y_AXIS));
        panelVillanos.setPreferredSize(new Dimension(sizePanel, sizeTablero - sizeInfoPanel));
        panelVillanos.setBorder(BorderFactory.createTitledBorder("Villanos Derrotados"));
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setPreferredSize(new Dimension(sizePanel, sizeInfoPanel));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Informacion"));
        
        labelRango = new JLabel("Rango: ");
        labelBando = new JLabel("Bando: ");
        panelInfo.add(labelRango);
        panelInfo.add(labelBando);
        
        JPanel tableroPanel = new JPanel(new GridLayout(10, 10));
        tableroPanel.setPreferredSize(new Dimension(sizeTablero, sizeTablero));
        setLayout(new GridLayout(10, 10));
        botones = new JButton[10][10];
        fichas = new Ficha[2][40];
        random = new Random();
        
        //INICIALIZAR BOTONES
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setPreferredSize(new Dimension(sizeBoton, sizeBoton));
                final int fila = i;
                final int columna = j;
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controlMovimiento(fila, columna);
                    }
                });
                tableroPanel.add(botones[i][j]);
            }
        }
        
        cargarFichas(sizeBoton);
        colocarImagenes();
        
        //ACTUALIZAR VISIBILIDAD DE LAS FICHAS
        actualizarVisibilidad();
        
        JPanel panelControl = new JPanel(new BorderLayout());
        panelControl.setPreferredSize(new Dimension(sizeTablero, 80));
        
        JLabel villanoLabel = new JLabel(menuInicio.loggedType.equals("Villano") ? "Villanos: " 
                + menuInicio.logged : "Villano: " + menuInicio.selected);
        JLabel heroeLabel = new JLabel(menuInicio.loggedType.equals("Heroe") ? "Heroes: " 
                + menuInicio.logged : "Heroes: " + menuInicio.selected);
        
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setPreferredSize(new Dimension(sizeTablero, 40));
        panelArriba.add(villanoLabel, BorderLayout.WEST);
        panelArriba.add(heroeLabel, BorderLayout.EAST);
        panelControl.add(panelArriba, BorderLayout.NORTH);
        
        JButton rendirseBoton = new JButton("Rendirse");
        JPanel panelBoton = new JPanel();
        panelBoton.add(rendirseBoton);
        panelControl.add(panelBoton, BorderLayout.SOUTH);

        //PANELES A LA DERECHA, VILLANOS DERROTADOS Y PANEL DE INFO
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.add(panelVillanos, BorderLayout.NORTH);
        panelDerecho.add(panelInfo, BorderLayout.SOUTH);
        
        frame.add(panelHeroes, BorderLayout.WEST);
        frame.add(tableroPanel, BorderLayout.CENTER);
        frame.add(panelDerecho, BorderLayout.EAST);
        frame.add(panelControl, BorderLayout.SOUTH);
        
        //BOTON DE RENDIRSE
        rendirseBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlRendirse();
            }
        });
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void cargarFichas(int tamanoBoton) {
        int sizeFicha = tamanoBoton - 5;

        // IMAGEN DE FICHA OCULTA
        ocultoIcon = new ImageIcon(getClass().getResource("images\\Oculto.png"));
        Image ocultoIMG = ocultoIcon.getImage().getScaledInstance(sizeFicha, sizeFicha, java.awt.Image.SCALE_SMOOTH);
        ocultoIcon = new ImageIcon(ocultoIMG);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 40; j++) {
                String nombreIMG;
                int rango;
                Bando bando = (i == 0) ? Bando.VILLANO : Bando.HEROE;
                if (bando == Bando.VILLANO) {
                    nombreIMG = "v - " + (j + 1) + ".png";
                    rango = getRangoVillano(j);
                } else {
                    nombreIMG = "h - " + (j + 1) + ".png";
                    rango = getRangoHeroe(j);
                }
                ImageIcon icon = new ImageIcon(getClass().getResource("images\\" + nombreIMG));
                Image imagen = icon.getImage();
                Image nuevaIMG = imagen.getScaledInstance(sizeFicha, sizeFicha, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(nuevaIMG);
                fichas[i][j] = new Ficha(icon, rango, bando, ocultoIcon);
            }
        }
    }
    
    private int getRangoVillano(int i) {
        int[] rangos = {0, 10, 9, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 5, 4, 4, 4, 
            4, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 1, -1, -1, -1, -1, -1, -1};
        return rangos[i];
    }
    
    private int getRangoHeroe(int i) {
        int[] rangos = {0, 10, 9, 8, 8, 7, 7, 7, 6, 6, 6, 6, 5, 5, 5, 5, 4, 4, 4, 
            4, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 1, -1, -1, -1, -1, -1, -1};
        return rangos[i];
    }
    
    private void colocarImagenes() {
        Set<Point> ocupadosVillanos = new HashSet<>();
        Set<Point> ocupadosHeroes = new HashSet<>();

        //COLOCAR TIERRA Y BOMBAS
        colocarTierraBombas(0, ocupadosVillanos, true);
        colocarTierraBombas(9, ocupadosHeroes, false);
        
        //COLOCAR RANGOS 26-33
        colocarIMGRango(2, 3, 26, 33, ocupadosVillanos, true);
        colocarIMGRango(6, 7, 26, 33, ocupadosHeroes, false);

        //COLOCAR LAS IMAGENES SOBRANTES
        colocarIMGRestantes(0, 3, 2, 25, ocupadosVillanos, true);
        colocarIMGRestantes(6, 9, 2, 25, ocupadosHeroes, false);
        
        //COLOCAR ZONAS PROHIBIDAS
        colocarProhibidas();
    }
    
    private void colocarTierraBombas(int fila, Set<Point> ocupados, boolean villano) {
        int tierraColumna = random.nextInt(8) + 1;
        Ficha fichaTierra = fichas[villano ? 0 : 1][0];
        botones[fila][tierraColumna].setIcon(fichaTierra.getIcono());
        botones[fila][tierraColumna].putClientProperty("ficha", fichaTierra);
        ocupados.add(new Point(fila, tierraColumna));
        
        int[] bombas = {35, 36, 37, 38, 39, 40};
        int contadorBombas = 0;
        
        int[][] direcciones = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, //VERTICAL Y HORIZONTAL
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1} //DIAGONAL
        };
        
        for (int[] direccion : direcciones) {
            int filaBomba = fila + direccion[0];
            int columnaBomba = tierraColumna + direccion[1];
            if (filaBomba >= 0 && filaBomba < 10 && columnaBomba >= 0 && columnaBomba < 10) {
                Ficha bombaFicha = fichas[villano ? 0 : 1][bombas[contadorBombas] - 1];
                botones[filaBomba][columnaBomba].setIcon(bombaFicha.getIcono());
                botones[filaBomba][columnaBomba].putClientProperty("ficha", bombaFicha);
                ocupados.add(new Point(filaBomba, columnaBomba));
                contadorBombas++;
                if (contadorBombas == 5) break;
            }
        }
        
        colocarBombaSobrante(fila == 0 ? 1 : 8, ocupados, villano);
    }
    
    private void colocarBombaSobrante(int fila, Set<Point> ocupados, boolean villano) {
        int colBombaExtra;
        do {
            colBombaExtra = random.nextInt(10);
        } while (ocupados.contains(new Point(fila, colBombaExtra)));
        Ficha bombaFichaExtra = fichas[villano ? 0 : 1][39];
        botones[fila][colBombaExtra].setIcon(bombaFichaExtra.getIcono());
        botones[fila][colBombaExtra].putClientProperty("ficha", bombaFichaExtra);
        ocupados.add(new Point(fila, colBombaExtra));
    }
    
    private void colocarIMGRango(int filaInicio, int filaFin, int inicioIMG, int finIMG, Set<Point> ocupados, boolean villano) {
        int bando = villano ? 0 : 1;
        for (int i = inicioIMG - 1; i < finIMG; i++) {
            int fila, col;
            do {
                fila = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
                col = random.nextInt(10);
            } while (ocupados.contains(new Point(fila, col)));
            Ficha ficha = fichas[bando][i];
            botones[fila][col].setIcon(ficha.getIcono());
            botones[fila][col].putClientProperty("ficha", ficha);
            ocupados.add(new Point(fila, col));
        }
    }
    
    private void colocarIMGRestantes(int filaInicio, int filaFin, int inicioImg, int finImg, Set<Point> ocupados, boolean villano) {
        int bando = villano ? 0 : 1;
        for (int i = inicioImg - 1; i < finImg; i++) {
            int fila, col;
            do {
                fila = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
                col = random.nextInt(10);
            } while (ocupados.contains(new Point(fila, col)));
            Ficha ficha = fichas[bando][i];
            botones[fila][col].setIcon(ficha.getIcono());
            botones[fila][col].putClientProperty("ficha", ficha);
            ocupados.add(new Point(fila, col));
        }
        
        int fila34, col34;
        do {
            fila34 = random.nextInt(filaFin - filaInicio + 1) + filaInicio;
            col34 = random.nextInt(10);
        } while (ocupados.contains(new Point(fila34, col34)));
        Ficha ficha34 = fichas[bando][33];
        botones[fila34][col34].setIcon(ficha34.getIcono());
        botones[fila34][col34].putClientProperty("ficha", ficha34);
        ocupados.add(new Point(fila34, col34));
    }
    
    private void colocarProhibidas() {
        int[][] zonasProhibidas = {
            {4, 2}, {4, 3}, {5, 2}, {5, 3},
            {4, 6}, {4, 7}, {5, 6}, {5, 7}
        };
        
        for (int[] zona : zonasProhibidas) {
            int fila = zona[0];
            int col = zona[1];
            ImageIcon icon = new ImageIcon(getClass().getResource("images\\Prohibido.png"));
            Image imagen = icon.getImage();
            Image nuevaIMG = imagen.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(nuevaIMG);
            botones[fila][col].setIcon(icon);
        }
    }
    
    private void actualizarVisibilidad() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton boton = botones[i][j];
                Ficha ficha = getFicha(i, j);
                if (ficha != null) {
                    if (turnoHeroe && ficha.esVillano()) {
                        ficha.setVisibleIcono(boton, false);
                    } else if (!turnoHeroe && !ficha.esVillano()) {
                        ficha.setVisibleIcono(boton, false);
                    } else {
                        ficha.setVisibleIcono(boton, true);
                    }
                }
            }
        }
    }
    
    private Ficha getFicha(int fila, int columna) {
        JButton boton = botones[fila][columna];
        return (Ficha) boton.getClientProperty("ficha");
    }
    
    private void controlMovimiento(int fila, int columna) {
        Ficha ficha = getFicha(fila, columna);

        if (fichaSeleccionada == null) {
            if (ficha != null && fichaTurnoActual(ficha)) {
                if (fichaInmovil(ficha)) { //SI ES TIERRA O BOMBA
                    JOptionPane.showMessageDialog(null, "No se puede mover esta ficha", 
                            null, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                fichaSeleccionada = ficha;
                filaSeleccionada = fila;
                columnaSeleccionada = columna;
                System.out.println("Ficha seleccionada (" + fila + ", " + columna + ")");
                
                remarcarMovPermitidos(fila, columna); //MARCAR CASILLAS DISPONIBLES
                mostrarINFO(ficha); //MOSTRAR INFO DE LA FICHA
            }
            
        } else if (ficha != null && fichaTurnoActual(ficha)) {
            
            volverColorOriginal(); //QUITAR REMARCADO
            if (fichaInmovil(ficha)) {//SI ES TIERRA O BOMBA
                JOptionPane.showMessageDialog(null, "No se puede mover esta ficha", 
                            null, JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            fichaSeleccionada = ficha;
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
            System.out.println("Nueva ficha seleccionada (" + fila + ", " + columna + ")");
            
            remarcarMovPermitidos(fila, columna); //MARCAR CASILLAS DISPONIBLES
            mostrarINFO(ficha); //MOSTRAR INFO DE LA FICHA
            
        } else {
            //ATACARRRRRRRRR
            if (ficha != null && fichaTurnoActual(fichaSeleccionada) && !fichaTurnoActual(ficha)) {
                realizarAtaque(fila, columna);
                
            } else if (movimientoValido(fila, columna)) { //MOVER SI LA CASILLA ESTA VACIA
                moverFicha(fila, columna);
                movimientoHecho = true;
                cambiarTurno();
                volverColorOriginal(); //QUITAR REMARCADO
                limpiarINFO(); //LIMPIAR INFO
                
            } else {
                JOptionPane.showMessageDialog(null, "Movimiento no permitido", 
                            null, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void mostrarINFO(Ficha ficha) {
        labelRango.setText("Rango: " + ficha.getRango());
        labelBando.setText("Bando: " + (ficha.esVillano() ? "Villano" : "Heroe"));
    }
    
    private void limpiarINFO() {
        labelRango.setText("Rango: ");
        labelBando.setText("Bando: ");
    }
    
    private boolean fichaInmovil(Ficha ficha) { //TIERRA Y BOMBAS NO SE PUEDEN MOVER
        return ficha.getRango() == 0 || ficha.getRango() == -1;
    }
    
    private void realizarAtaque(int filaDestino, int columnaDestino) {
        Ficha fichaAtacante = fichaSeleccionada;
        Ficha fichaDefensora = getFicha(filaDestino, columnaDestino);
        
        if (fichaAtacante == null || fichaDefensora == null) {
            JOptionPane.showMessageDialog(null, "No es posible el ataque", 
                            null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int filaDif = Math.abs(filaDestino - filaSeleccionada);
        int columnaDif = Math.abs(columnaDestino - columnaSeleccionada);
        boolean ataqueValido = false;
        
        if (fichaAtacante.getRango() == 2) { //FICHAS RANGO 2 ATAQUE SI EL CAMINO ESTA LIBRE
            if ((filaDif == 0 || columnaDif == 0) && caminoLibre(filaDestino, columnaDestino)) {
                ataqueValido = true;
            }
            
        } else { //OTROS RANGOS ATAQUE SI ESTAN ADAYACENTES
            ataqueValido = (filaDif == 1 && columnaDif == 0) || (filaDif == 0 && columnaDif == 1);
        }

        if (!ataqueValido) {
            JOptionPane.showMessageDialog(null, "No es posible el ataque", 
                            null, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        boolean atacanteGana = false;
        String msj = "";
        ImageIcon ganadorIcon = null;
        ImageIcon perdedorIcon = null;
        
        if (fichaAtacante.getRango() == fichaDefensora.getRango()) { //FICHAS CON MISMO RANGO
            eliminarFicha(filaSeleccionada, columnaSeleccionada);
            eliminarFicha(filaDestino, columnaDestino);
            msj = "Empate! Ambas fichas se destruyen";
            ganadorIcon = fichaAtacante.getIcono();
            perdedorIcon = fichaDefensora.getIcono();
            
        } else if (fichaAtacante.getRango() == 1 && fichaDefensora.getRango() == 10) { //RANGO 1 DESTRUYE A RANGO 10
            atacanteGana = true;
            msj = "Gana el atacante!";
            ganadorIcon = fichaAtacante.getIcono();
            perdedorIcon = fichaDefensora.getIcono();
            
        } else if (fichaDefensora.getRango() == -1) { //BOMBAS DESTRUYEN A TODAS LAS FICHAS
            if (fichaAtacante.getRango() == 3) {
                atacanteGana = true;
                msj = "Gana el atacante!";
                ganadorIcon = fichaAtacante.getIcono();
                perdedorIcon = fichaDefensora.getIcono();
                
            } else {
                eliminarFicha(filaSeleccionada, columnaSeleccionada); //BOMBA DESTRUYE
                msj = "Gana la bomba!";
                ganadorIcon = fichaDefensora.getIcono();
                perdedorIcon = fichaAtacante.getIcono();
            }
            
        } else if (fichaAtacante.getRango() > fichaDefensora.getRango() || fichaDefensora.getRango() == 0) {
            atacanteGana = true;
            msj = "Gana el atacante!";
            ganadorIcon = fichaAtacante.getIcono();
            perdedorIcon = fichaDefensora.getIcono();

            //SI LA FICHA ERA LA TIERRA, VICTORIA
            if (fichaDefensora.getRango() == 0) {
                victoriaTierraCapturada(filaDestino, columnaDestino);
                return;
            }
            
        } else { //SE DESTRUYE EL ATACANTE
            eliminarFicha(filaSeleccionada, columnaSeleccionada);
            msj = "Gana el defensor!";
            ganadorIcon = fichaDefensora.getIcono();
            perdedorIcon = fichaAtacante.getIcono();
        }
        
        if (atacanteGana) {
            eliminarFicha(filaDestino, columnaDestino); //ELIMINAR FICHA
            moverFicha(filaDestino, columnaDestino); //MOVER FICHA A NUEVA POSICION
        }
        
        //PANEL DE RESULTADO
        JPanel panelResultado = new JPanel(new GridLayout(2, 2, 10, 10));
        panelResultado.add(new JLabel(ganadorIcon));
        panelResultado.add(new JLabel(perdedorIcon));
        panelResultado.add(new JLabel("Ganador", SwingConstants.CENTER));
        panelResultado.add(new JLabel("Perdedor", SwingConstants.CENTER));
        
        JOptionPane.showMessageDialog(this, panelResultado, msj, JOptionPane.INFORMATION_MESSAGE);

        cambiarTurno();
        volverColorOriginal();
    }
    
    private void agregarFicha(JPanel panel, Ficha ficha) {
        int nuevoSize = 40; //HACERLA MAS PEQUEÑA 
        ImageIcon icon = ficha.getIcono();
        Image imagen = icon.getImage();
        Image nuevaIMG = imagen.getScaledInstance(nuevoSize, nuevoSize, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(nuevaIMG);
        
        JLabel labelFicha = new JLabel();
        labelFicha.setIcon(icon);
        panel.add(labelFicha);
        panel.revalidate();
        panel.repaint();
    }
    
    private void eliminarFicha(int fila, int columna) {
        Ficha ficha = getFicha(fila, columna);
        if (ficha != null) {
            if (ficha.esVillano()) {
                agregarFicha(panelVillanos, ficha);
            } else {
                agregarFicha(panelHeroes, ficha);
            }
        }
        
        botones[fila][columna].setIcon(null);
        botones[fila][columna].putClientProperty("ficha", null);
        System.out.println("Ficha eiliminada (" + fila + ", " + columna + ")");
    }
    
    private void remarcarMovPermitidos(int fila, int columna) {
        volverColorOriginal(); //QUITAR REMARCADO
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (movimientoValido(i, j)) {
                    botones[i][j].setBackground(Color.YELLOW); //CAMBIAR A AMARILLO
                    remarcados.add(botones[i][j]);
                }
            }
        }
    }
    
    private void volverColorOriginal() {
        for (JButton boton : remarcados) {
            boton.setBackground(null);
        }
        remarcados.clear();
    }
    
    private boolean fichaTurnoActual(Ficha ficha) {
        return (turnoHeroe && !ficha.esVillano()) || (!turnoHeroe && ficha.esVillano());
    }
    
    private boolean movimientoValido(int filaDestino, int columnaDestino) {
        
        //FUERA DE ALCANCE
        if (filaDestino < 0 || filaDestino >= 10 || columnaDestino < 0 || columnaDestino >= 10) {
            return false;
        }
        
        //CASILLA OCUPADA
        if (getFicha(filaDestino, columnaDestino) != null) {
            return false;
        }
        
        //ZONA PROHIBIDA
        if (zonaProhibida(filaDestino, columnaDestino)) {
            return false;
        }
        
        int filaDif = Math.abs(filaDestino - filaSeleccionada);
        int columnaDif = Math.abs(columnaDestino - columnaSeleccionada);
        Ficha ficha = getFicha(filaSeleccionada, columnaSeleccionada);
        
        if (ficha.getRango() == 2) {
            if (filaDif == 0 || columnaDif == 0) {
                return caminoLibre(filaDestino, columnaDestino);
            }
        } else {
            return (filaDif == 1 && columnaDif == 0) || (filaDif == 0 && columnaDif == 1);
        }
        return false;
    }
    
    private boolean caminoLibre(int filaDestino, int columnaDestino) {
        int filaIni = Math.min(filaSeleccionada, filaDestino);
        int filaFin = Math.max(filaSeleccionada, filaDestino);
        int colIni = Math.min(columnaSeleccionada, columnaDestino);
        int colFin = Math.max(columnaSeleccionada, columnaDestino);
        
        //VERIFICAR MOV VERTICAL O HORIZONTAL
        if (filaSeleccionada == filaDestino) { //MOV HORIZONTAL
            for (int i = colIni + 1; i < colFin; i++) {
                if (getFicha(filaSeleccionada, i) != null || zonaProhibida(filaSeleccionada, i)) {
                    return false;
                }
            }
        } else if (columnaSeleccionada == columnaDestino) { //MOV VERTICAL
            for (int i = filaIni + 1; i < filaFin; i++) {
                if (getFicha(i, columnaSeleccionada) != null || zonaProhibida(i, columnaSeleccionada)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean zonaProhibida(int fila, int columna) {
        int[][] zonasProhibidas = {
            {4, 2}, {4, 3}, {5, 2}, {5, 3},
            {4, 6}, {4, 7}, {5, 6}, {5, 7}
        };
        
        for (int[] zona : zonasProhibidas) {
            if (zona[0] == fila && zona[1] == columna) {
                return true;
            }
        }
        return false;
    }
    
    private void moverFicha(int filaDestino, int columnaDestino) {
        Ficha ficha = getFicha(filaSeleccionada, columnaSeleccionada);
        
        if (ficha != null) { //FICHA A TIERRA
            Ficha fichaDestino = getFicha(filaDestino, columnaDestino);
            
            if (fichaDestino != null && fichaDestino.getRango() == 0) {
                victoriaTierraCapturada(filaDestino, columnaDestino);
                return;
            }
            
            //CASILLA VIEJA AHORA ES NULL
            botones[filaSeleccionada][columnaSeleccionada].setIcon(null);
            botones[filaSeleccionada][columnaSeleccionada].putClientProperty("ficha", null);
            
            //COLOCAR FICHA EN DESTINO
            botones[filaDestino][columnaDestino].setIcon(ficha.getIcono());
            botones[filaDestino][columnaDestino].putClientProperty("ficha", ficha);
            
            fichaSeleccionada = null;
            filaSeleccionada = -1;
            columnaSeleccionada = -1;
            
        } else {
            System.out.println("No hay ficha");
        }
    }
    
    private void victoriaTierraCapturada(int filaDestino, int columnaDestino) {
        String perdedor, bandoPerdedor, ganador, bandoGanador;

        if (turnoHeroe) {
            ganador = menuInicio.logged; 
            bandoGanador = menuInicio.loggedType;
            perdedor = menuInicio.selected;
            bandoPerdedor = menuInicio.selectedType;
        } else {
            ganador = menuInicio.selected; 
            bandoGanador = menuInicio.selectedType;
            perdedor = menuInicio.logged;
            bandoPerdedor = menuInicio.loggedType;
        }

        //CREAR MENSAJE
        String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String mensaje = String.format("%s usando %s ha %s la TIERRA! Venciendo a %s usando %s – %s",
                ganador, bandoGanador, turnoHeroe ? "SALVADO" : "CAPTURADO",
                perdedor, bandoPerdedor, fecha);
        JOptionPane.showMessageDialog(this, mensaje, "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        
        menuInicio.logs.add(mensaje); //AGREGAR AL ARRAY DE LOGS
        menuInicio.usuarios.puntos(ganador); //ASIGNAR 3 PTS AL GANADOR
        
        MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
        menuPrincipal.setVisible(true);
        for (Frame f : JFrame.getFrames()) {
            if (f.isVisible()) {
                f.dispose();
                break;
            }
        }
    }
    
    private void cambiarTurno() {
        turnoHeroe = !turnoHeroe;
        System.out.println("Turno actual: " + (turnoHeroe ? "Heroes" : "Villanos"));
        actualizarVisibilidad();
        fichaSeleccionada = null; //REMOVER SELECCION DE FICHA
        movimientoHecho = false; //PERMITIR MOVIMIENTO NUEVO
    }
    
    private void controlRendirse() {
        String ganador, perdedor, bandoGanador, bandoPerdedor;

        //VERIFICAR QUIEN ESTA EN TURNO Y EL BANDO
        if (turnoHeroe && menuInicio.loggedType.equals("Heroe")) {
            perdedor = menuInicio.logged; 
            bandoPerdedor = menuInicio.loggedType;
            ganador = menuInicio.selected;
            bandoGanador = menuInicio.selectedType;
        } else if (turnoHeroe && menuInicio.selectedType.equals("Heroe")) {
            perdedor = menuInicio.selected;
            bandoPerdedor = menuInicio.selectedType;
            ganador = menuInicio.logged;
            bandoGanador = menuInicio.loggedType;
        } else if (!turnoHeroe && menuInicio.loggedType.equals("Villano")) {
            perdedor = menuInicio.logged;
            bandoPerdedor = menuInicio.loggedType;
            ganador = menuInicio.selected;
            bandoGanador = menuInicio.selectedType;
        } else {
            perdedor = menuInicio.selected;
            bandoPerdedor = menuInicio.selectedType;
            ganador = menuInicio.logged;
            bandoGanador = menuInicio.loggedType;
        }

        //DAR 3 PUNTOS
        menuInicio.usuarios.puntos(ganador);
        
        //CREAR EL MENSAJE
        String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String mensaje = String.format("%s usando %s ha ganado ya que %s usando %s se ha retirado del juego – %s",
                ganador, bandoGanador, perdedor, bandoPerdedor, fecha);
        JOptionPane.showMessageDialog(this, mensaje, "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        
        //AGREGAR MENSAJE AL ARRAY DE LOGS
        menuInicio.logs.add(mensaje);
        
        MenuPrincipal menuPrincipal = new MenuPrincipal(menuInicio);
        menuPrincipal.setVisible(true);
        for (Frame f : JFrame.getFrames()) {
            if (f.isVisible()) {
                f.dispose();
                break;
            }
        }
    }
}
