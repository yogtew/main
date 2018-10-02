package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the name of the event.
 */
public class EventName {

    public final String eventName;

    /**
     * Constructs a (@code EventName).
     *
     * @param eventName a valid event name.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        this.eventName = eventName;
    }
}
