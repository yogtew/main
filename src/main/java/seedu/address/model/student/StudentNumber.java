package seedu.address.model.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's student number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentNumber(String)}
 */
public class StudentNumber {


    public static final String MESSAGE_STUDENT_NUMBER_CONSTRAINTS =
            "Student Number numbers should only contain letters or numbers and no spaces.";
    public static final String STUDENT_NUMBER_VALIDATION_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9]*$";
    public final String value;

    /**
     * Constructs a {@code StudentNumber}.
     *
     * @param studentNumber A valid student number.
     */
    public StudentNumber(String studentNumber) {
        requireNonNull(studentNumber);
        checkArgument(isValidStudentNumber(studentNumber), MESSAGE_STUDENT_NUMBER_CONSTRAINTS);
        value = studentNumber;
    }

    /**
     * Returns true if a given string is a valid student number.
     */
    public static boolean isValidStudentNumber(String test) {
        return test.matches(STUDENT_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentNumber // instanceof handles nulls
                && value.equals(((StudentNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
