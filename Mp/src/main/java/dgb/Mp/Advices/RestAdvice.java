package dgb.Mp.Advices;

import dgb.Mp.Advices.CustomException.DepartementNotFoundException;
import dgb.Mp.Advices.CustomException.DirectionGeneraleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {


    @ExceptionHandler(DepartementNotFoundException.class)
    public ResponseEntity<String> handleDepartementNotFound(DepartementNotFoundException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DirectionGeneraleNotFoundException.class)
    public ResponseEntity<String> handleDirectionGeneraleNotFoundException(DepartementNotFoundException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
