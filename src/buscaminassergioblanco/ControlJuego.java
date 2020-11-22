package buscaminassergioblanco;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay una
 * mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de la
 * partida
 *
 * @author jesusredondogarcia & SergioBlancoPrieto
 *
 */
public class ControlJuego {

    private final static int MINA = -1;
    final int MINAS_INICIALES = 20;
    final int LADO_TABLERO = 10;

    private int[][] tablero;
    private int puntuacion;

    private boolean primeraPartida;

    public ControlJuego() {
        primeraPartida = true;
        //Creamos el tablero:
        tablero = new int[LADO_TABLERO][LADO_TABLERO];

        //Inicializamos una nueva partida
        inicializarPartida();
    }

    /**
     * Método para generar un nuevo tablero de partida:
     *
     * @pre: La estructura tablero debe existir.
     * @post: Al final el tablero se habrá inicializado con tantas minas como
     * marque la variable MINAS_INICIALES. El resto de posiciones que no son
     * minas guardan en el entero cuántas minas hay alrededor de la celda
     */
    public void inicializarPartida() {
        //Primero ponemos la puntuación a 0
        puntuacion = 0;
        //Primero se comprueba si es la primera partida:
        if (primeraPartida) {
            //Si lo es se indicará para la siguiente y no hay que poner todas las casillas a 0 al ya estarlo.
            primeraPartida = false;
        } else {
            //Si no lo es se ponen todas las posiciones a 0, reiniciando el tablero y eliminando las minas.
            for (int i = 0; i < LADO_TABLERO; i++) {
                for (int j = 0; j < LADO_TABLERO; j++) {
                    tablero[i][j] = 0;
                }
            }
        }
        //Después repartimos las minas de forma aleatoria:
        Random rd = new Random();
        int x, y;
        int contador = 0;
        //Hecemos el proceso hasta que se repartan todas las minas:
        while (contador < MINAS_INICIALES) {
            //Primero se genera una posición aleatoria en el tablero:
            x = rd.nextInt(LADO_TABLERO);
            y = rd.nextInt(LADO_TABLERO);
            //Si la posición no tiene ya una mina se coloca una:
            if (tablero[x][y] != MINA) {
                tablero[x][y] = MINA;
                contador++;
            }
        }
        //Por último se ponen los números en todas las casillas que no son minas:
        for (int i = 0; i < LADO_TABLERO; i++) {
            for (int j = 0; j < LADO_TABLERO; j++) {
                if (tablero[i][j] != MINA) {
                    tablero[i][j] = calculoMinasAdjuntas(i, j);
                }
            }
        }
    }

    /**
     * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos
     * que tener en cuenta que no nos salimos nunca del tablero. Por lo tanto,
     * como mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco la
     * i y la j valdrán 0.
     *
     * @param i: posición vertical de la casilla a rellenar
     * @param j: posición horizontal de la casilla a rellenar
     * @return : El número de minas que hay alrededor de la casilla [i][j]
     *
     */
    private int calculoMinasAdjuntas(int i, int j) {
        int numMinas = 0;
        //Recorremos las casillas de alrededor para ver si hay alguna mina. También se comprueba la casilla central pero al ser imposible que sea una mina no hay problema.
        for (int iAux = i - 1; iAux <= i + 1; iAux++) {
            for (int jAux = j - 1; jAux <= j + 1; jAux++) {
                try {
                    if (tablero[iAux][jAux] == MINA) {
                        numMinas++;
                    }
                } catch (ArrayIndexOutOfBoundsException ae) {
                    //Si se sale del tablero no hacemos nada
                }
            }
        }
        return numMinas;
    }

    /**
     * Método que nos permite abrir una casilla y comprobar si tiene o no una
     * mina.
     *
     * @pre : La casilla nunca debe haber sido abierta antes, no es controlado
     * por el ControlJuego. Por lo tanto siempre sumaremos puntos
     * @param i: posición verticalmente de la casilla a abrir
     * @param j: posición horizontalmente de la casilla a abrir
     * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
     */
    public boolean abrirCasilla(int i, int j) {
        if (tablero[i][j] == MINA) {
            return false;
        } else {
            puntuacion++;
            tablero[i][j] = calculoMinasAdjuntas(i, j);
            return true;
        }
    }

    /**
     * Método que checkea si se ha terminado el juego porque se han abierto
     * todas las casillas.
     *
     * @return Devuelve verdadero si se han abierto todas las celdas que no son
     * minas.
     *
     */
    public boolean esFinJuego() {
        return puntuacion == (LADO_TABLERO * LADO_TABLERO - MINAS_INICIALES);
    }

    /**
     * Método que pinta por pantalla toda la información del tablero, se utiliza
     * para depurar
     */
    public void depurarTablero() {
        System.out.println("---------TABLERO--------------");
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("\nPuntuación: " + puntuacion);
    }

    /**
     * Método que se utiliza para obtener las minas que hay alrededor de una
     * celda
     *
     * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace
     * falta calcularlo, símplemente consultarlo
     * @param i : posición vertical de la celda.
     * @param j : posición horizontal de la cela.
     * @return Un entero que representa el número de minas alrededor de la celda
     */
    public int getMinasAlrededor(int i, int j) {
        return tablero[i][j];
    }

    /**
     * Método que devuelve la puntuación actual
     *
     * @return Un entero con la puntuación actual
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Método que actualiza la puntuación actual
     *
     * @param p : la nueva puntuación.
     */
    public void setPuntuación(int p) {
        puntuacion = p;
    }

    /**
     * Método que devuelve la el tablero
     *
     * @return Un array con el tablero actual
     */
    public int[][] getTablero() {
        return tablero;
    }

}
