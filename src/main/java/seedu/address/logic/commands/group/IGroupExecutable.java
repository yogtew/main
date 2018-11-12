package seedu.address.logic.commands.group;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Interface for commands that support execution with groups
 */
public interface IGroupExecutable {
    CommandResult executeGroup(Model model, CommandHistory history) throws CommandException;
}
