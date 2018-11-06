package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the end time of an event in the calendar.
 */
public class EndTime {


    public static final String END_TIME_CONSTRAINTS =
            "End times should only be given as hh:mm and it should only be in 24 hour format";

    /*
     * The end time should be hh:mm and in a 24 hour format (leading zeroes optional).
     */
    public static final String END_TIME_VALIDATION_REGEX = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    public final String value;

    /**
     * Constructs a (@code EndTime).
     *
     * @param value a valid end time.
     */
    public EndTime(String value) {
        requireNonNull(value);
        checkArgument(isValidEndTime(value), END_TIME_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid end time.
     */
    public static boolean isValidEndTime(String test) {
        return test.matches(END_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && value.equals(((EndTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
