package buscaminassergioblanco;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Ventana principal del Buscaminas
 *
 * @author SergioBlancoPrieto
 */
public class VentanaPrincipal {

    //La ventana principal, en este caso, guarda todos los componentes:
    JFrame ventana;
    JPanel panelImagen;
    JPanel panelEmpezar;
    JPanel panelPuntuacion;
    JPanel panelJuego;

    //Todos los botones se meten en un panel independiente.
    //Hacemos esto para que podamos cambiar después los componentes por otros
    JPanel[][] panelesJuego;
    JButton[][] botonesJuego;

    //Correspondencia de colores para las minas:
    Color correspondenciaColores[] = {Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED};

    JButton botonEmpezar;
    JTextField pantallaPuntuacion;
    JTextField tiempo;
    Reloj reloj;

    //LA VENTANA GUARDA UN CONTROL DE JUEGO:
    ControlJuego juego;

    //Constructor, marca el tamaño y el cierre del frame
    public VentanaPrincipal() {
        ventana = new JFrame();
        ventana.setBounds(100, 100, 700, 500);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        juego = new ControlJuego();
    }

    //Inicializa todos los componentes del frame
    public void inicializarComponentes() {

        //Definimos el layout:
        ventana.setLayout(new GridBagLayout());

        //Inicializamos componentes
        panelImagen = new JPanel();
        panelEmpezar = new JPanel();
        panelEmpezar.setLayout(new GridLayout(1, 1));
        panelPuntuacion = new JPanel();
        panelPuntuacion.setLayout(new GridLayout(1, 1));
        panelJuego = new JPanel();
        panelJuego.setLayout(new GridLayout(10, 10));

        botonEmpezar = new JButton("Go!");
        pantallaPuntuacion = new JTextField("0");
        pantallaPuntuacion.setEditable(false);
        pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
        tiempo = new JTextField("0:0:0");
        tiempo.setEditable(false);
        reloj = new Reloj(this);

        //Bordes y colores:
        panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
        panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

        //Colocamos los componentes:
        //AZUL
        GridBagConstraints settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelImagen, settings);
        //VERDE
        settings = new GridBagConstraints();
        settings.gridx = 1;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelEmpezar, settings);
        //AMARILLO
        settings = new GridBagConstraints();
        settings.gridx = 2;
        settings.gridy = 0;
        settings.weightx = 1;
        settings.weighty = 1;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelPuntuacion, settings);
        //ROJO
        settings = new GridBagConstraints();
        settings.gridx = 0;
        settings.gridy = 1;
        settings.weightx = 1;
        settings.weighty = 10;
        settings.gridwidth = 3;
        settings.fill = GridBagConstraints.BOTH;
        ventana.add(panelJuego, settings);
        
        //Cronómetro
        panelImagen.add(tiempo);
        reloj.start();

        //Paneles
        panelesJuego = new JPanel[10][10];
        for (int i = 0; i < panelesJuego.length; i++) {
            for (int j = 0; j < panelesJuego[i].length; j++) {
                panelesJuego[i][j] = new JPanel();
                panelesJuego[i][j].setLayout(new GridLayout(1, 1));
                panelJuego.add(panelesJuego[i][j]);
            }
        }

        //Botones
        botonesJuego = new JButton[10][10];
        for (int i = 0; i < botonesJuego.length; i++) {
            for (int j = 0; j < botonesJuego[i].length; j++) {
                botonesJuego[i][j] = new JButton("-");
                panelesJuego[i][j].add(botonesJuego[i][j]);
            }
        }

        //BotónEmpezar:
        panelEmpezar.add(botonEmpezar);
        panelPuntuacion.add(pantallaPuntuacion);

    }

    /**
     * Método que inicializa todos los lísteners que necesita inicialmente el
     * programa
     */
    public void inicializarListeners() {
        //Se establece la acción de los botones del tablero:
        for (int i = 0; i < botonesJuego.length; i++) {
            for (int j = 0; j < botonesJuego[i].length; j++) {
                botonesJuego[i][j].addActionListener(new ActionBoton(this, i, j));
                botonesJuego[i][j].addMouseListener(new ActionBoton(this, i, j));
            }
        }

        //Se establece la acción del botón que reinicia el juego:
        botonEmpezar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Se para el tiempo y se resetea a 0:
                reloj.setTerminar(true);
                reloj.reiniciar();
                GridBagConstraints settings = new GridBagConstraints();
                getJuego().inicializarPartida();
                //Se elimina el panel actual ya que algunos botones fueron sustituidos por JLabels y se crea todo de nuevo:
                ventana.remove(panelJuego);
                panelJuego = new JPanel();
                panelJuego.setLayout(new GridLayout(10, 10));
                panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

                settings = new GridBagConstraints();
                settings.gridx = 0;
                settings.gridy = 1;
                settings.weightx = 1;
                settings.weighty = 10;
                settings.gridwidth = 3;
                settings.fill = GridBagConstraints.BOTH;
                ventana.add(panelJuego, settings);

                // Paneles
                panelesJuego = new JPanel[10][10];
                for (int i = 0; i < panelesJuego.length; i++) {
                    for (int j = 0; j < panelesJuego[i].length; j++) {
                        panelesJuego[i][j] = new JPanel();
                        panelesJuego[i][j].setLayout(new GridLayout(1, 1));
                        panelJuego.add(panelesJuego[i][j]);
                    }
                }

                // Botones
                botonesJuego = new JButton[10][10];
                for (int i = 0; i < botonesJuego.length; i++) {
                    for (int j = 0; j < botonesJuego[i].length; j++) {
                        botonesJuego[i][j] = new JButton("-");
                        panelesJuego[i][j].add(botonesJuego[i][j]);
                    }
                }
                inicializarListeners();
                pantallaPuntuacion.setText("0");
                juego.setPuntuación(0);
                //Se reactiva el tiempo:
                reloj.setTerminar(false);
                refrescarPantalla();
            }
        });
    }

    /**
     * Pinta en la pantalla el número de minas que hay alrededor de la celda
     * Saca el botón que haya en la celda determinada y añade un JLabel centrado
     * y no editable con el número de minas alrededor. Se pinta el color del
     * texto según la siguiente correspondecia (consultar la variable
     * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja -
     * 4 ó más : rojo
     *
     * @param i: posición vertical de la celda.
     * @param j: posición horizontal de la celda.
     */
    public void mostrarNumMinasAlrededor(int i, int j) {
        //Se comprueban las minas alrededor y se elige un color según el número de ellas.
        int num = getJuego().getMinasAlrededor(i, j);
        JLabel label = new JLabel(Integer.toString(num));
        switch (num) {
            case 0: {
                label.setForeground(correspondenciaColores[0]);
            }
            break;
            case 1: {
                label.setForeground(correspondenciaColores[1]);
            }
            break;
            case 2: {
                label.setForeground(correspondenciaColores[2]);
            }
            break;
            case 3: {
                label.setForeground(correspondenciaColores[3]);
            }
            break;
            default: {
                label.setForeground(correspondenciaColores[4]);
            }
            break;
        }
        //Se elimina el botón y se sustituye por un JLabel con el número de minas adyacentes:
        panelesJuego[i][j].remove(botonesJuego[i][j]);
        panelesJuego[i][j].add(label);
        //Si el número de minas es 0 se abren todas las de alrededor también:
        if (getJuego().getMinasAlrededor(i, j) == 0) {
            abrirAdyacentes(i, j);
        }
        refrescarPantalla();
    }

    /**
     * Comprueba las casillas de alrededor y si no tienen minas las abre
     *
     * @param i : posición x de la casilla.
     * @param j : posición y de la casilla.
     */
    public void abrirAdyacentes(int i, int j) {
        //Se recorren las casillas de alrededor:
        for (int iAux = i - 1; iAux <= i + 1; iAux++) {
            for (int jAux = j - 1; jAux <= j + 1; jAux++) {
                try {
                    //Aquí se comprueba si hay un botón en esa casilla para poder pulsarlo.
                    if (panelesJuego[iAux][jAux].getComponent(0).getClass() == JButton.class) {
                        botonesJuego[iAux][jAux].doClick();
                    }
                } catch (ArrayIndexOutOfBoundsException ae) {
                    //En caso de salirnos del tablero no hacemos nada.
                }
            }
        }
    }

    /**
     * Muestra una ventana que indica el fin del juego
     *
     * @param porExplosion : Un booleano que indica si es final del juego porque
     * ha explotado una mina (true) o bien porque hemos desactivado todas
     * (false)
     * @post : Todos los botones se desactivan excepto el de volver a iniciar el
     * juego.
     */
    public void mostrarFinJuego(boolean porExplosion) {
        //Primero se para el tiempo:
        reloj.setTerminar(true);
        //En caso de que demos click en una mina:
        if (porExplosion) {
            //Se abre una ventana con el texto de fin de juego:
            int opcion = JOptionPane.showConfirmDialog(ventana, "¡¡HA EXPLOTADO UNA MINA!!\nPuntuación: " + getJuego().getPuntuacion() + "\n¿Quieres jugar de nuevo?", "Fin del juego", JOptionPane.YES_NO_OPTION);
            //Si volvemos a jugar se crea un nuevo tablero, si no se termina el programa:
            if (opcion == JOptionPane.YES_OPTION) {
                //Se eliminan los paneles existents para que no se dupliquen.
                ventana.remove(panelJuego);
                ventana.remove(panelImagen);
                ventana.remove(panelEmpezar);
                ventana.remove(panelPuntuacion);
                juego.inicializarPartida();
                inicializar();
                refrescarPantalla();
            } else {
                System.exit(0);
            }
        } else {
            //En caso de que se acabe el juego por encontrar todas las casillas sin minas se hace lo mismo con un distinto mensaje.
            int opcion = JOptionPane.showConfirmDialog(ventana, "¡¡TABLERO COMPLETADO!!\nPuntuación: " + getJuego().getPuntuacion() + "\n¿Quieres jugar de nuevo?", "Fin del juego", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                ventana.remove(panelJuego);
                ventana.remove(panelImagen);
                ventana.remove(panelEmpezar);
                ventana.remove(panelPuntuacion);
                juego.inicializarPartida();
                inicializar();
                refrescarPantalla();
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Método que actualiza y muestra la puntuación por pantalla.
     */
    public void actualizarPuntuacion() {
        juego.setPuntuación(juego.getPuntuacion() + 1);
        pantallaPuntuacion.setText(String.valueOf(juego.getPuntuacion()));
    }

    /**
     * Método para refrescar la pantalla
     */
    public void refrescarPantalla() {
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Método que devuelve el control del juego de una ventana
     *
     * @return un ControlJuego con el control del juego de la ventana
     */
    public ControlJuego getJuego() {
        return juego;
    }

    /**
     * Método para inicializar el programa
     */
    public void inicializar() {
        //IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS COMPONENTES.
        ventana.setVisible(true);
        inicializarComponentes();
        inicializarListeners();
    }

}
