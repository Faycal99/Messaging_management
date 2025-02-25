package dgb.Mp.generalAdvice.customException;

public class InvalidRefreshTokenException extends RuntimeException{
    public InvalidRefreshTokenException(String msg) {super(msg);}
}
