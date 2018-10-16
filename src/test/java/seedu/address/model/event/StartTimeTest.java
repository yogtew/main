package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StartTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartTime(null));
    }

    @Test
    public void constructor_invalidStartTime_throwsIllegalArgumentException() {
        String invalidStartTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartTime(invalidStartTime));
    }

    @Test
    public void isValidStartTime() {
        // null start time
        Assert.assertThrows(NullPointerException.class, () -> StartTime.isValidStartTime(null));

        // invalid start time
        assertFalse(StartTime.isValidStartTime("25:00")); // invalid time
        assertFalse(StartTime.isValidStartTime("1111")); // no colon

        // valid start time
        assertTrue(StartTime.isValidStartTime("23:59"));
        assertTrue(StartTime.isValidStartTime("00:00"));
        assertTrue(StartTime.isValidStartTime("12:30"));
    }
}
