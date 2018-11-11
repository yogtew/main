package seedu.address.logic.commands.mark;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;

/**
 * Mark "and" subcommand
 */
public class MarkAndCommand extends MarkCommand {
    public static final String MESSAGE_SUCCESS = "%d students were added to %s from (%s intersect %s)";
    private final String alias1;
    private final String alias2;
    private final String alias3;

    public MarkAndCommand(String alias1, String alias2, String alias3) {
        this.alias1 = alias1;
        this.alias2 = alias2;
        this.alias3 = alias3;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Mark mark1 = model.getMark(alias2);
        Mark result = mark1.intersect(model.getMark(alias3));
        model.setMark(alias1, result);
        return new CommandResult(String.format(MESSAGE_SUCCESS, result.count(), alias1, alias2, alias3));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAndCommand)) {
            return false;
        }

        MarkAndCommand command = (MarkAndCommand) other;

        return alias1.equals(command.alias1)
                && alias2.equals(command.alias2)
                && alias3.equals(command.alias3);
    }
}
