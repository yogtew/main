package seedu.address.logic.commands.group;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Thrown when a group that doesn't exist is being accessed
 */
public class GroupNotFoundException extends CommandException {
    public GroupNotFoundException(String message) {
        super(message);
    }

}
