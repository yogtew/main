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

    public final String value;

    /**
     * Constructs a (@code EventName).
     *
     * @param value a valid event name.
     */
    public EventName(String value) {
        requireNonNull(value);
        checkArgument(isValidEventName(value), EVENT_NAME_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENT_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && value.equals(((EventName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
