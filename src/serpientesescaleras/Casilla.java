package serpientesescaleras;

/**
 *
 * @author Carlos Contreras
 */
public class Casilla {

    public int noCasilla;       // 1-100
    public char tipoCasilla;    // N, E, S, T
    public int posiciones;      // con E, S valor aleatorio (5-20)

    public Casilla(int noCasilla, char tipoCasilla, int posiciones) {
        this.noCasilla = noCasilla;
        this.tipoCasilla = tipoCasilla;
        this.posiciones = posiciones;
    }

}
