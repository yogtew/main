package seedu.address.model.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the body of the email.
 */
public class Body {

    public static final String MESSAGE_BODY_CONSTRAINTS =
            "Body should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BODY_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Body}.
     *
     * @param body A valid body.
     */
    public Body(String body) {
        requireNonNull(body);
        checkArgument(isValidBody(body), MESSAGE_BODY_CONSTRAINTS);
        value = body;
    }


    /**
     * Returns true if a given string is a valid body.
     */
    public static boolean isValidBody(String test) {
        return test.matches(BODY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Body
                && value.equals(((Body) other).value));
    }

    @Override
    public String toString() {
        return value;
    }
}
