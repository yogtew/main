package seedu.address.logic.commands.mark;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;
import seedu.address.model.person.Person;

/**
 * Mark "find" subcommand
 */
public class MarkFindCommand extends MarkCommand {
    private static final String MESSAGE_SUCCESS = "Successfully added %d people to %s";
    private Predicate<Person> p;
    public MarkFindCommand(Predicate<Person> p, String alias1) {
        this.p = p;
        this.alias1 = alias1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Person> marked = model.getAddressBook().getPersonList().filtered(p);
        Mark mark = new Mark(marked);
        model.setMark(alias1, mark);
        return new CommandResult(String.format(MESSAGE_SUCCESS, marked.size(), alias1));
    }
}
