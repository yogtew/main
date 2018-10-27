package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's attendance
 */
public class Attendance {
    public final String value;

    public Attendance(String attendance) {
        requireNonNull(attendance);
        value = attendance;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Attendance)
                && value.equals(((Attendance) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
