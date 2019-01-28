package exceptions;

/**
 *
 * @author Nick
 */
public class ConfigException extends RuntimeException{

    public ConfigException() {
    }

    public ConfigException(String message) {
        super(message);
    }

}
