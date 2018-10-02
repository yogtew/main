package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the start time of an event in the calendar.
 */
public class EndTime {

    public final String endTime;

    /**
     * Constructs a (@code EndTime).
     *
     * @param endTime a valid end time.
     */
    public EndTime(String endTime) {
        requireNonNull(endTime);
        this.endTime = endTime;
    }
}
