package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of the event.
 */
public class EventName {

    public static final String EVENT_NAME_CONSTRAINTS =
            "Event name can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String EVENT_NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String eventName;

    /**
     * Constructs a (@code EventName).
     *
     * @param eventName a valid event name.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEventName(eventName), EVENT_NAME_CONSTRAINTS);
        this.eventName = eventName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && eventName.equals(((EventName) other).eventName)); // state check
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }
}
