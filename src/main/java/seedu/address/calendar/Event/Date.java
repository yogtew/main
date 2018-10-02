package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the start time of an event in the calendar.
 */
public class Date {

    public final String date;

    /**
     * Constructs a (@code Date).
     *
     * @param date a valid start time.
     */
    public Date(String date) {
        requireNonNull(date);
        this.date = date;
    }
}
