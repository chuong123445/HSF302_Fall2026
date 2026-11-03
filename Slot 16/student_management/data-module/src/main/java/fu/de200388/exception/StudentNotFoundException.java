package fu.de200388.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) { super("Student not found: " + id); }
}
