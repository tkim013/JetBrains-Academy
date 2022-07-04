package cinema.exceptionhandling;

public class SeatUnavailableException extends RuntimeException{
    public SeatUnavailableException(String msg) {
        super(msg);
    }
}
