package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EndTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndTime(null));
    }

    @Test
    public void constructor_invalidEndTime_throwsIllegalArgumentException() {
        String invalidEndTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndTime(invalidEndTime));
    }

    @Test
    public void isValidEndTime() {
        // null end time
        Assert.assertThrows(NullPointerException.class, () -> EndTime.isValidEndTime(null));

        // invalid end time
        assertFalse(EndTime.isValidEndTime("25:00")); // invalid time
        assertFalse(EndTime.isValidEndTime("1111")); // no colon

        // valid end time
        assertTrue(EndTime.isValidEndTime("23:59"));
        assertTrue(EndTime.isValidEndTime("00:00"));
        assertTrue(EndTime.isValidEndTime("12:30"));
    }
}
