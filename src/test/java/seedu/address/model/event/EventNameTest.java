package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EventNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void constructor_invalidEventName_throwsIllegalArgumentException() {
        String invalidEventName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EventName(invalidEventName));
    }

    @Test
    public void isValidEventName() {
        // null event name
        Assert.assertThrows(NullPointerException.class, () -> EventName.isValidEventName(null));

        // invalid event name
        assertFalse(EventName.isValidEventName(""));
        assertFalse(EventName.isValidEventName(" "));

        // valid event name
        assertTrue(EventName.isValidEventName("Bob's Birthday Party"));
        assertTrue(EventName.isValidEventName("Office Dinner Function"));
        assertTrue(EventName.isValidEventName("-"));
    }
}
