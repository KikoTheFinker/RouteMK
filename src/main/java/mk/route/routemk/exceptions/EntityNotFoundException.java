package mk.route.routemk.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message, Object id) {
        super(message + " with criteria" + id);
    }
}
