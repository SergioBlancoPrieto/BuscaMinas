package buscaminassergioblanco;

/**
 * Esta clase contiene un hilo que funcionará como cronómetro
 *
 * @author SergioBlancoPrieto
 */
public class Reloj extends Thread {

    private int h;
    private int m;
    private int s;
    private boolean terminar;
    private VentanaPrincipal ventana;

    public Reloj(VentanaPrincipal v) {
        ventana = v;
        terminar = false;
    }

    /**
     * Crea un hilo que va contand los segundos, minutos y horas desde que
     * comenzó
     */
    @Override
    public void run() {
        h = 0;
        m = 0;
        s = 0;
        while (!terminar) {
            try {
                sleep(1000);
                s++;
                if (s == 60) {
                    s = 0;
                    m++;
                }
                if (m == 60) {
                    m = 0;
                    h++;
                }
                ventana.tiempo.setText(h + ":" + m + ":" + s);
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    /**
     * Establece el valor de terminar
     * 
     * @param t : nuevo valor a establecer.
     */
    public void setTerminar (boolean t) {
        terminar = t;
    }

    public void reiniciar() {
        h = 0;
        m = 0;
        s = 0;
    }
}
