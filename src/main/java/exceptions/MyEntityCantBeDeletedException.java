package exceptions;

import javax.persistence.EntityNotFoundException;

public class MyEntityCantBeDeletedException extends MyEntityNotFoundException {
    public MyEntityCantBeDeletedException(String message) {
        super(message);
    }
}
