package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;

/**
 * Represents the detail of an event in the calendar.
 */
public class Detail {

    public final String detail;

    /**
     * Constructs a (@code Detail).
     *
     * @param detail a valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        this.detail = detail;
    }
}
