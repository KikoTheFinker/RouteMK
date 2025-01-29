package mk.route.routemk.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName, Object id){
        super("Entity " + entityName + " not found with id " + id);
    }
}
