package serpientesescaleras;

import java.util.Random;

/**
 *
 * @author Carlos Contreras
 */
public class Main {

    public static void main(String[] args) {
        // Crear tablero original
        ListaDBL<Casilla> tablero = new ListaDBL<>();
        for (int i = 0; i < 100; tablero.InsertaFin(new Casilla(++i, 'N', 0)));

        // Crear 5 escaleras
        for (int i = 0; i < 5; generarCasilla(tablero, 'E', 15, 70), i++);

        // Crear 5 serpientes
        for (int i = 0; i < 5; generarCasilla(tablero, 'S', 30, 95), i++);

        // Imprimir tablero
        System.out.println("\t\t\t\t  TABLERO"
                + "\n---------------------------------------------------------------------------------");
        imprimirTablero(tablero);
        System.out.println("---------------------------------------------------------------------------------");

        // Generar jugadores
        NodoDBL<Casilla>[] jugadores = new NodoDBL[new Random().nextInt(10 - 2 + 1) + 2];

        // Simulación juego
        System.out.println("\n\n\tSIMULACIÓN DE JUEGO"
                + "\nNúmero de jugadores: " + jugadores.length);
        simularJuego(tablero, jugadores);
    }

    public static void imprimirTablero(ListaDBL<Casilla> T) {
        NodoDBL<Casilla> aux = T.getFrente();
        for (int i = 0; aux != null && i < 10; i++) {
            for (int j = 0; j < 10; System.out.print(aux.Info.noCasilla + " " + aux.Info.tipoCasilla + " " + aux.Info.posiciones + "\t"), aux = aux.getSig(), j++);
            System.out.println("");
        }
    }

    public static void generarCasilla(ListaDBL<Casilla> tablero, char tipoCasilla, int limiteInferior, int limiteSuperior) {
        int nodoValido = new Random().nextInt(limiteSuperior - limiteInferior + 1) + limiteInferior;
        NodoDBL<Casilla> aux = tablero.getFrente();
        
        for (int i = 0; i < nodoValido - 1; aux = aux.getSig(), i++); // Me posiciono. Condición -1 porque el getFrente "avanza 1 posición".
        while (aux.Info.tipoCasilla != 'N') {
            nodoValido = new Random().nextInt(limiteSuperior - limiteInferior + 1) + limiteInferior;
            aux = tablero.getFrente();
            for (int i = 0; i < nodoValido; aux = aux.getSig(), i++);
        }
        aux.Info.tipoCasilla = tipoCasilla;

        int posicionesPorAvanzar = new Random().nextInt(20 - 5 + 1) + 5;
        NodoDBL<Casilla> auxTermina = aux;
        
        // Con esto hago el mismo método funcionable para generar ya sea Escaleras o Serpientes
        if (tipoCasilla == 'E') {
            for (int i = 0; i < posicionesPorAvanzar; auxTermina = auxTermina.getSig(), i++);
            while (auxTermina.Info.tipoCasilla != 'N') {
                posicionesPorAvanzar = new Random().nextInt(20 - 5 + 1) + 5;
                auxTermina = aux;
                for (int i = 0; i < posicionesPorAvanzar; auxTermina = auxTermina.getSig(), i++);
            }
            aux.Info.tipoCasilla = 'E';
        } else if (tipoCasilla == 'S') {
            for (int i = 0; i < posicionesPorAvanzar; auxTermina = auxTermina.getAnt(), i++);
            while (auxTermina.Info.tipoCasilla != 'N') {
                posicionesPorAvanzar = new Random().nextInt(20 - 5 + 1) + 5;
                auxTermina = aux;
                for (int i = 0; i < posicionesPorAvanzar; auxTermina = auxTermina.getAnt(), i++);
            }
            aux.Info.tipoCasilla = 'S';
        }
        aux.Info.posiciones = posicionesPorAvanzar;
        auxTermina.Info.tipoCasilla = 'T';
    }

    public static void simularJuego(ListaDBL<Casilla> tablero, NodoDBL<Casilla>[] jugadores) {
        int dados, turno = 0;
        boolean juegoCompletado = false;
        NodoDBL<Casilla> posicionActual = null;
        NodoDBL<Casilla> casillaLlegada = null;

        while (!juegoCompletado) {
            for (int i = 0; i < jugadores.length; i++) {
                turno++;
                dados = new Random().nextInt(12 - 2 + 1) + 2;

                posicionActual = jugadores[i]; // Guardar posicion actual antes de moverse

                // Primer tiro de ese jugador
                if (posicionActual == null) {
                    jugadores[i] = tablero.getFrente();
                    for (int j = 0; j < dados - 1; jugadores[i] = jugadores[i].getSig(), j++);
                    imprimirCasilla(jugadores, posicionActual, casillaLlegada, turno, dados, i); // Método void

                    continue;
                }

                // Me posiciono donde va a llegar el jugador
                for (int j = 0; j < dados; j++) {
                    if (jugadores[i].getSig() == null) { // Se llega al final del tablero
                        for (int k = 0; k < dados - j; jugadores[i] = jugadores[i].getAnt(), k++); // Se regresa
                        break;
                    }
                    jugadores[i] = jugadores[i].getSig();
                }

                casillaLlegada = jugadores[i]; // Guardar donde llegó inicialmente, antes de aplicar avance o retroceso

                // Dependiendo si cayó en escalera o serpiente, moverlo.
                if (casillaLlegada.Info.tipoCasilla == 'E') {
                    for (int j = 0; j < casillaLlegada.Info.posiciones; jugadores[i] = jugadores[i].getSig(), j++);
                } else if (casillaLlegada.Info.tipoCasilla == 'S') {
                    for (int j = 0; j < casillaLlegada.Info.posiciones; jugadores[i] = jugadores[i].getAnt(), j++);
                }

                // Imprimir casilla
                imprimirCasilla(jugadores, posicionActual, casillaLlegada, turno, dados, i); // Método void
                System.out.println("----------------------------------\n");

                // Ganar el juego
                if (jugadores[i].Info.noCasilla == 100) {
                    System.out.println("----------------------------------"
                            + "\n\t¡JUEGO TERMINADO!"
                            + "\n     El ganador es: Jugador " + (i + 1)
                            + "\n----------------------------------");
                    juegoCompletado = true;
                    break;
                }
            }
        }
    }

    public static void imprimirCasilla(NodoDBL<Casilla>[] jugadores, NodoDBL<Casilla> posicionActual, NodoDBL<Casilla> casillaLlegada, int turno, int dados, int iterador) {
        System.out.println("----------------------------------"
                + "\nTurno " + turno
                + "\nJugador: " + (iterador + 1)
                + "\nDados: " + dados);

        // Sin valor de posicion actual, entonces es la primera jugada
        if (posicionActual == null) {
            System.out.println("Casilla inicio: Primera jugada"
                    + "\nCasilla final: " + jugadores[iterador].Info.noCasilla
                    + "\nTipo de nodo: N"
                    + "\n----------------------------------\n");
            return;
        }

        System.out.println("Casilla inicio: " + posicionActual.Info.noCasilla
                + "\nCasilla final: " + jugadores[iterador].Info.noCasilla);
//                + "\nTipo de nodo: " + casillaLlegada.Info.tipoCasilla); // No usar esto, porque si llego a una T, la imprime

        if (casillaLlegada.Info.tipoCasilla == 'N') {
            System.out.println("Tipo de nodo: N");
        } else if (casillaLlegada.Info.tipoCasilla == 'E') {
            System.out.println("Tipo de nodo: E"
                    + "\nEscaleras - Avanzaste " + casillaLlegada.Info.posiciones + " posiciones");
        } else if (casillaLlegada.Info.tipoCasilla == 'S') {
            System.out.println("Tipo de nodo: E"
                    + "\nSerpientes - Retrocediste " + casillaLlegada.Info.posiciones + " posiciones");
        }
    }

}
