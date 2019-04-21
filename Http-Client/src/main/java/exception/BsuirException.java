package exception;

public class BsuirException extends Exception {

    private static final long serialVersionUID = -772489653447538870L;

    public BsuirException(String message) {
        super(message);
    }

    public BsuirException(Throwable cause) {
        super(cause);
    }

    public BsuirException(String message, Throwable cause) {
        super(message, cause);
    }
}
