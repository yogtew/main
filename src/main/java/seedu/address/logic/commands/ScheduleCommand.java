package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Schedules an event to the calendar.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an event in the calendar. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "EVENT_NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_START + "TIME_START "
            + PREFIX_END + "TIME_END "
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "CS2103 Tutorial W13 "
            + PREFIX_DATE + "22-3-2018 "
            + PREFIX_START + "16:00 "
            + PREFIX_END + "18:00 "
            + PREFIX_DESCRIPTION + "Week 3 CS2103 tutorial";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the calendar";

    private final Event toSchedule;

    /**
     * Creates a ScheduleCommand to add the specified (@code Event)
     */
    public ScheduleCommand(Event event) {
        requireNonNull(event);
        toSchedule = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toSchedule)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toSchedule);
        model.commitCalendar();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && toSchedule.equals(((ScheduleCommand) other).toSchedule));
    }
}
