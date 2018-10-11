package seedu.address.calendar.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the detail of an event in the calendar.
 */
public class Detail {

    public static final String DETAIL_STRING_CONSTRAINTS =
            "Details should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DETAIL_STRING_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String detail;

    /**
     * Constructs a (@code Detail).
     *
     * @param detail a valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidDetail(detail), DETAIL_STRING_CONSTRAINTS);
        this.detail = detail;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDetail(String test) {
        return test.matches(DETAIL_STRING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return detail;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                && detail.equals(((Detail) other).detail)); // state check
    }

    @Override
    public int hashCode() {
        return detail.hashCode();
    }
}
