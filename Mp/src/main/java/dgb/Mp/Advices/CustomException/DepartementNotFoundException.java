package dgb.Mp.Advices.CustomException;

public class DepartementNotFoundException extends RuntimeException {
    public DepartementNotFoundException(Long id) {
        super("Departement " + id + " not found");
    }
}
