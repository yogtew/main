package seedu.address.logic.commands.group;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Group "show" subcommand
 */
public class GroupShowCommand extends GroupCommand {
    public static final String MESSAGE_SUCCESS = "Listed %d students in %s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group %s not found";

    public GroupShowCommand(String alias1) {
        this.alias1 = alias1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws GroupNotFoundException {
        requireNonNull(model);
        try {
            Group group = model.getGroup(alias1);
            model.setGroupPredicate(alias1);
            return new CommandResult(String.format(MESSAGE_SUCCESS, group.getList().size(), alias1));
        } catch (GroupNotFoundException e) {
            throw new GroupNotFoundException(String.format(MESSAGE_GROUP_NOT_FOUND, alias1));
        }
    }
}
