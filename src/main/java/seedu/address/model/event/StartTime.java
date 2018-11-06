package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the start time of an event in the calendar.
 */
public class StartTime {


    public static final String START_TIME_CONSTRAINTS =
            "Start times should only be given as hh:mm and it should only be in 24 hour format";

    /*
     * The start time should be hh:mm and in a 24 hour format (leading zeroes optional).
     */
    public static final String START_TIME_VALIDATION_REGEX = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
    public final String value;

    /**
     * Constructs a (@code StartTime).
     *
     * @param value a valid start time.
     */
    public StartTime(String value) {
        requireNonNull(value);
        checkArgument(isValidStartTime(value), START_TIME_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(START_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && value.equals(((StartTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
