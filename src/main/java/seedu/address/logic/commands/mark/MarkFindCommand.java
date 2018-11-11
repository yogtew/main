package seedu.address.logic.commands.mark;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Student;

/**
 * Mark "find" subcommand
 */
public class MarkFindCommand extends MarkCommand {
    public static final String MESSAGE_SUCCESS = "Successfully added %d students to %s";
    private Predicate<Student> p;
    public MarkFindCommand(Predicate<Student> p, String alias1) {
        this.p = p;
        this.alias1 = alias1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Student> marked = model.getAddressBook().getStudentList().filtered(p);
        Mark mark = new Mark(marked, alias1);
        model.setMark(alias1, mark);
        return new CommandResult(String.format(MESSAGE_SUCCESS, marked.size(), alias1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkFindCommand)) {
            return false;
        }

        MarkFindCommand command = (MarkFindCommand) other;

        return p.equals(command.p) && alias1.equals(command.alias1);
    }
}
