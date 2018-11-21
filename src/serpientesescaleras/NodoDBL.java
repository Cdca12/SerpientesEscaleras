package serpientesescaleras;

public class NodoDBL<T> {

    private NodoDBL<T> Ant;
    public T Info;
    private NodoDBL<T> Sig;

    public NodoDBL(T D) {
        Info = D;
        Sig = null;
        Ant = null;
    }

    public NodoDBL<T> getSig() {
        return Sig;
    }

    public void setSig(NodoDBL<T> Ap) {
        Sig = Ap;
    }

    public NodoDBL<T> getAnt() {
        return Ant;
    }

    public void setAnt(NodoDBL<T> Ap) {
        Ant = Ap;
    }
}
