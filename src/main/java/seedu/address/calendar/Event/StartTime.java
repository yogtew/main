package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the start time of an event in the calendar.
 */
public class StartTime {

    public final String startTime;

    /**
     * Constructs a (@code StartTime).
     *
     * @param startTime a valid start time.
     */
    public StartTime(String startTime) {
        requireNonNull(startTime);
        this.startTime = startTime;
    }
}
