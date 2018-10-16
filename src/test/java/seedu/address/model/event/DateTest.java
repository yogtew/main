package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null Date number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid Date numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("12")); // not a date
        assertFalse(Date.isValidDate("Date")); // non-numeric
        assertFalse(Date.isValidDate("23/MM/2013")); // alphabets used
        assertFalse(Date.isValidDate("22 02 2000")); // spaces as delimiter
        assertFalse(Date.isValidDate("29-02-2011")); // impossible date

        // valid Date numbers
        assertTrue(Date.isValidDate("21/05/1990")); // valid date, "/" delimiter
        assertTrue(Date.isValidDate("1/12/2018")); // valid date, single digit date
        assertTrue(Date.isValidDate("31-12-2021")); // valid date, "-" delimiter
        assertTrue(Date.isValidDate("01.1.2000")); // valid date, "." delimiter
    }
}
