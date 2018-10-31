package seedu.address.logic.commands.mark;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Interface for commands that support execution with marks
 */
public interface IMarkExecutable {
    public CommandResult executeMark(Model model, CommandHistory history);
}
