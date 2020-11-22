package buscaminassergioblanco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa el listener de los botones del Buscaminas. De alguna
 * manera tendrá que poder acceder a la ventana principal. Se puede lograr
 * pasando en el constructor la referencia a la ventana. Recuerda que desde la
 * ventana, se puede acceder a la variable de tipo ControlJuego
 *
 * @author jesusredondogarcia & SergioBlancoPrieto
 */
public class ActionBoton implements ActionListener {
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

}
