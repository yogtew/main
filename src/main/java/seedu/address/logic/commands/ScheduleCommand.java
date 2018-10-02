package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.calendar.event.Event;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Schedules an event to the calendar.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "Schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Schedules an event in the calendar."
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT_NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "TIME_START"
            + PREFIX_END + "TIME_END"
            + PREFIX_DETAIL + "PREFIX_DETAILS\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103-Tutorial-W13 "
            + PREFIX_DATE + "22-3-2018 "
            + PREFIX_START + "1600"
            + PREFIX_END + "1800"
            + PREFIX_DETAIL + "Week 3 CS2103 tutorial";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

    private final Event toAdd;

    /**
     * Creates a ScheduleCommand to add the specified (@code Event)
     */
    public ScheduleCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // for now just executes the command without adding
        // model.addEvent(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
