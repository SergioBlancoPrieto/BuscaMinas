package buscaminassergioblanco;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 *
 * @author jesusredondogarcia & SergioBlancoPrieto
 */
public class ActionBoton extends MouseAdapter implements ActionListener {
    private final static int MINA = -1;
    VentanaPrincipal ventana;
    int x, y;

    public ActionBoton(VentanaPrincipal v, int x, int y) {
        ventana = v;
        this.x = x;
        this.y = y;
    }

    /**
     * Acción que ocurrirá cuando pulsamos uno de los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (ventana.getJuego().getTablero()[x][y] == MINA) {
            ventana.mostrarFinJuego(true);
        } else {
            ventana.actualizarPuntuacion();
            if (ventana.juego.esFinJuego()) {
                ventana.mostrarFinJuego(false);
            } else {
                ventana.mostrarNumMinasAlrededor(x, y);
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            if (ventana.botonesJuego[x][y].getText().equals("-")) {
                ventana.botonesJuego[x][y].setBackground(Color.red);
                ventana.botonesJuego[x][y].setText("MINA");
            } else {
                ventana.botonesJuego[x][y].setBackground(null);
                ventana.botonesJuego[x][y].setText("-");
            }
        }
    }

}
