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
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StudentNumber(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> StudentNumber.isValidPhone(null));

        // invalid phone numbers
        assertFalse(StudentNumber.isValidPhone("")); // empty string
        assertFalse(StudentNumber.isValidPhone(" ")); // spaces only
        assertFalse(StudentNumber.isValidPhone("91")); // less than 3 numbers
        assertFalse(StudentNumber.isValidPhone("phone")); // non-numeric
        assertFalse(StudentNumber.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(StudentNumber.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(StudentNumber.isValidPhone("911")); // exactly 3 numbers
        assertTrue(StudentNumber.isValidPhone("93121534"));
        assertTrue(StudentNumber.isValidPhone("124293842033123")); // long phone numbers
    }
}
