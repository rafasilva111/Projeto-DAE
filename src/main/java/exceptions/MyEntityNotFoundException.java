package exceptions;

import javax.persistence.EntityNotFoundException;

public class MyEntityNotFoundException extends EntityNotFoundException {
    public MyEntityNotFoundException(String message) {
        super(message);
    }

}
