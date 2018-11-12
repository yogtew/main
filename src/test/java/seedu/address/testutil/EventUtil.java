package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns a schedule command string for adding the {@code event}.
     */
    public static String getScheduleCommand(Event event) {
        return ScheduleCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_NAME + event.getEventName().eventName + " ");
        sb.append(PREFIX_DATE + event.getDate().date + " ");
        sb.append(PREFIX_START + event.getStartTime().startTime + " ");
        sb.append(PREFIX_END + event.getEndTime().endTime + " ");
        event.getDescription().ifPresent(desc ->
            sb.append(PREFIX_DESCRIPTION + desc.description)
        );
        return sb.toString();
    }
}
