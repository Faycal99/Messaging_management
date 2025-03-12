package dgb.Mp.generalAdvice;

import dgb.Mp.generalAdvice.customException.DepartementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestAdvice {

    @ExceptionHandler(DepartementNotFoundException.class)
    public ResponseEntity<String> handleDepartementNotFound(DepartementNotFoundException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
