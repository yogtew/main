package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;
import seedu.address.model.person.Person;

/**
 * Marks Persons based on supplied Predicates
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [m/m1] [show|join|find|and] [m/m2] [m/m3]";

    private Predicate<Person> predicate;

    public MarkCommand(Predicate<Person> p) {
        /*
        TODO: Add different constructors for the different ways to use mark
         */
        predicate = p;
    }

    public MarkCommand() {
        predicate = person -> true;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        List<Person> people = model.getAddressBook().getPersonList();
        ArrayList<Person> marked = new ArrayList<>();
        people.forEach(person -> {
            if (predicate.test(person)) {
                marked.add(person);
            }
        });
        model.setMark(Mark.DEFAULT_NAME, new Mark(marked));
        return new CommandResult(String.format(Messages.MESSAGE_WIP_COMMAND, COMMAND_WORD));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand); // instanceof handles nulls
    }
}
