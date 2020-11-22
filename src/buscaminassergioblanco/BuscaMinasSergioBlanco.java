package buscaminassergioblanco;
import java.awt.EventQueue;

/**
 *
 * @author SergioBlancoPrieto
 */
public class BuscaMinasSergioBlanco {

    /**
     * Método main
     *
     * @param args : Cadenas de parámetros del main
     */
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPrincipal ventana = new VentanaPrincipal();
                    ventana.inicializar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
