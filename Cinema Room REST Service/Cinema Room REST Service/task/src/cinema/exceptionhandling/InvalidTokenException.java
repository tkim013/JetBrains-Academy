package cinema.exceptionhandling;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
