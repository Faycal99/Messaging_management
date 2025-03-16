package dgb.Mp.Advices.CustomException;

public class DirectionGeneraleNotFoundException extends RuntimeException {
    public DirectionGeneraleNotFoundException(long id) { super("DirectionGenerale " + id + " not found");
    }
}
