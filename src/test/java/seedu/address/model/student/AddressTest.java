package seedu.address.model.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Faculty(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Faculty(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Faculty.isValidAddress(null));

        // invalid addresses
        assertFalse(Faculty.isValidAddress("")); // empty string
        assertFalse(Faculty.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Faculty.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Faculty.isValidAddress("-")); // one character
        assertTrue(Faculty.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
