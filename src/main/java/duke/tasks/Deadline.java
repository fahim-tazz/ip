package duke.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    public final LocalDate by;

    public Deadline(String description, String by) throws DateTimeParseException {
        super(description);
        this.by = LocalDate.parse(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
