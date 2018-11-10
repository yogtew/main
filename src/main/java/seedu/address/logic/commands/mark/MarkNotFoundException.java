package seedu.address.logic.commands.mark;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Thrown when a mark that doesn't exist is being accessed
 */
public class MarkNotFoundException extends CommandException {
    public MarkNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MarkNotFoundException} with the specified detail {@code message} and {@code cause}.
     */
    public MarkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
