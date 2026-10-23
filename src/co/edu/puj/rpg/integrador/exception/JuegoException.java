package co.edu.puj.rpg.integrador.exception;

public abstract class JuegoException extends Exception {

    public JuegoException(String mensaje) {
        super(mensaje);
    }

    // Conserva la causa raiz para no perder el rastro de la pila
    public JuegoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}