package seedu.address.model.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StudentNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StudentNumber(null));
    }

    @Test
    public void constructor_invalidStudentNumber_throwsIllegalArgumentException() {
        String invalidStudentNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidStudentNumber));
    }

    @Test
    public void isValidStudentNumber() {
        // null student number
        Assert.assertThrows(NullPointerException.class, () -> StudentNumber.isValidStudentNumber(null));

        // invalid student numbers
        assertFalse(StudentNumber.isValidStudentNumber("")); // empty string
        assertFalse(StudentNumber.isValidStudentNumber(" ")); // spaces only
        assertFalse(StudentNumber.isValidStudentNumber("student number")); // non-numeric
        assertFalse(StudentNumber.isValidStudentNumber("9312 1534")); // spaces within digits
        assertFalse(StudentNumber.isValidStudentNumber("$AA")); // non alphanumeric
        assertFalse(StudentNumber.isValidStudentNumber("$**AA")); // non alphanumeric

        // valid student numbers
        assertTrue(StudentNumber.isValidStudentNumber("9")); // only one digit
        assertTrue(StudentNumber.isValidStudentNumber("A")); // only one letter
        assertTrue(StudentNumber.isValidStudentNumber("93121FD534"));
        assertTrue(StudentNumber.isValidStudentNumber("A124293842033123")); // long student numbers
    }
}
