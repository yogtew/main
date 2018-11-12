package seedu.address.logic.commands.group;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Group "join" subcommand
 * group [alias1] join {alias2} [alias3]
 */
public class GroupJoinCommand extends GroupCommand {
    public static final String MESSAGE_SUCCESS = "%d students were added to %s from (%s union %s)";
    private final String alias2;
    private final String alias3;

    public GroupJoinCommand(String alias1, String alias2, String alias3) {
        this.alias1 = alias1;
        this.alias2 = alias2;
        this.alias3 = alias3;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        Group group1 = model.getGroup(alias2);
        Group result = group1.join(model.getGroup(alias3));
        model.setGroup(alias1, result);
        return new CommandResult(String.format(MESSAGE_SUCCESS, result.count(), alias1, alias2, alias3));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupJoinCommand)) {
            return false;
        }

        GroupJoinCommand command = (GroupJoinCommand) other;

        return alias1.equals(command.alias1)
                && alias2.equals(command.alias2)
                && alias3.equals(command.alias3);
    }
}
