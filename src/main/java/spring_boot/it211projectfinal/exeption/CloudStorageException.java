package spring_boot.it211projectfinal.exeption;

public class CloudStorageException extends RuntimeException {
    public CloudStorageException(String message) {
        super(message);
    }
}
