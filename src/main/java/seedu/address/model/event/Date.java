package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the start time of an event in the calendar.
 */
public class Date {

    public static final String DATE_NAME_CONSTRAINTS =
            "Dates should only be a valid value between the years 1600 and 9999, formatted as dd-mm-yyyy";

    /**
     * This regex checks if the value is valid and is formatted as dd-mm-yyyy
     */
    public static final String DATE_VALIDATION_REGEX =
            "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)"
            + "(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)"
            + "0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)"
            + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    public final String value;

    /**
     * Constructs a (@code Date).
     *
     * @param value a valid start time.
     */
    public Date(String value) {
        requireNonNull(value);
        checkArgument(isValidDate(value), DATE_NAME_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid value.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles null
            && value.equals(((Date) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
