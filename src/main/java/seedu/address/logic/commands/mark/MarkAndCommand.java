package seedu.address.logic.commands.mark;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;

/**
 * Mark "and" subcommand
 */
public class MarkAndCommand extends MarkCommand {
    private static final String MESSAGE_SUCCESS = "Successfully added %d people from %s and %s to %s";
    private final String alias1;
    private final String alias2;
    private final String alias3;

    public MarkAndCommand(String alias1, String alias2, String alias3) {
        this.alias1 = alias1;
        this.alias2 = alias2;
        this.alias3 = alias3;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        Mark mark1 = model.getMark(alias2);
        model.setMark(alias1, mark1.intersect(model.getMark(alias3)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, mark1.getList().size(), alias2, alias3, alias1));
    }
}
