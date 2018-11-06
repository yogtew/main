package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the value of an event in the calendar.
 */
public class Description {

    public static final String DESCRIPTION_STRING_CONSTRAINTS =
            "Description can take any values, and it should not be blank";

    /*
     * The first character of the value must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_STRING_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a (@code Description).
     *
     * @param value a valid value.
     */
    public Description(String value) {
        requireNonNull(value);
        checkArgument(isValidDescription(value), DESCRIPTION_STRING_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_STRING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
