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
    public final String startTime;

    /**
     * Constructs a (@code StartTime).
     *
     * @param startTime a valid start time.
     */
    public StartTime(String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidStartTime(startTime), START_TIME_CONSTRAINTS);
        this.startTime = startTime;
    }

    /**
     * Returns true if a given string is a valid start time.
     */
    public static boolean isValidStartTime(String test) {
        return test.matches(START_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
