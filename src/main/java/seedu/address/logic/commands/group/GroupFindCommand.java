package seedu.address.logic.commands.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;

/**
 * Group "find" subcommand
 */
public class GroupFindCommand extends GroupCommand {
    public static final String MESSAGE_SUCCESS = "Successfully added %d students to %s";
    private Predicate<Student> p;
    public GroupFindCommand(Predicate<Student> p, String alias1) {
        this.p = p;
        this.alias1 = alias1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Student> matched = model.getAddressBook().getStudentList().filtered(p);
        Group group = new Group(matched, alias1);
        model.setGroup(alias1, group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, matched.size(), alias1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupFindCommand)) {
            return false;
        }

        GroupFindCommand command = (GroupFindCommand) other;

        return p.equals(command.p) && alias1.equals(command.alias1);
    }
}
