package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;

/**
 * Mark "show" subcommand
 */
public class MarkShowCommand extends MarkCommand {
    public static final String MESSAGE_SUCCESS = "Listed %d students in %s";

    public MarkShowCommand(String alias1) {
        this.alias1 = alias1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws MarkNotFoundException {
        requireNonNull(model);
        try {
            Mark mark = model.getMark(alias1);
            model.updateFilteredStudentList(mark.getPredicate());
            return new CommandResult(String.format(MESSAGE_SUCCESS, mark.getList().size(), alias1));
        } catch (IllegalArgumentException e) {
            throw new MarkNotFoundException(e.getMessage());
        }
    }
}
