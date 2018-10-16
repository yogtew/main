package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
            + PREFIX_DETAIL + "PREFIX_DETAILS\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103 Tutorial W13 "
            + PREFIX_DATE + "22-3-2018 "
            + PREFIX_START + "16:00 "
            + PREFIX_END + "18:00 "
            + PREFIX_DETAIL + "Week 3 CS2103 tutorial";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";

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
        try {
            // Simply writes to a text file in data for now.
            BufferedWriter writer = new BufferedWriter(new FileWriter("data\\calendar.txt", true));
            writer.append(toSchedule.toString() + "\n");
            writer.close();
        } catch (IOException exception) {
            LogsCenter.getLogger("ScheduleCommand").log(Level.WARNING, "File Not Found");
        }

        // for now just write to a simple output file
        // model.addEvent(toAdd);
        // model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && toSchedule.equals(((ScheduleCommand) other).toSchedule));
    }
}
