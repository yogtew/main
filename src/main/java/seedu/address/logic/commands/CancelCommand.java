package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Cancels an event in the Calendar.
 */
public class CancelCommand extends Command {

    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Cancels the event identified by the name, date, start time, and end time in the list of events.\n"
            + "Parameters: event/EVENT_NAME date/DATE start/START_TIME end/END_TIME\n"
            + "Example: " + COMMAND_WORD + " event/CS2103 finals date/5-12-2018 start/17:00 end/18:00";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Cancelled Event: %1$s";

    private final Event targetEvent;

    public CancelCommand(Event targetEvent) {
        this.targetEvent = targetEvent;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(targetEvent)) {
            model.deleteEvent(targetEvent);
            model.commitCalendar();
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, targetEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelCommand // instanceof handles nulls
                && targetEvent.equals(((CancelCommand) other).targetEvent)); // state check
    }
}

