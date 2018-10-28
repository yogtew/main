package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's attendance
 */
public class Attendance {

    public static final String MESSAGE_ATTENDANCE_CONSTRAINTS =
            "Attendance can take be either 1 (Present) or 0 (Absent)";

    /*
     * The first character of the attendance must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ATTENDANCE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public Attendance(String attendance) {
        requireNonNull(attendance);
        checkArgument(isValidAttendance(attendance), MESSAGE_ATTENDANCE_CONSTRAINTS);
        value = attendance;
    }

    /**
     * Returns true if a given string is a valid attendance.
     */
    public static boolean isValidAttendance(String test) {
        return test.matches(ATTENDANCE_VALIDATION_REGEX);
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
