package home.core.protocol;

public class ProtocolException extends Exception {

    public ProtocolException(String msg) {
        super(msg);
    }

    public ProtocolException(Throwable ex) {
        super(ex);
    }
}
