package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.storage.XmlAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalEvents.TUTORIAL_NO_DESCRIPTION;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.Assert;

public class XmlAdapterEventTest {
    private static final String INVALID_EVENT_NAME = " ";
    private static final String INVALID_DATE = "29-02-2013";
    private static final String INVALID_START_TIME = "24:00";
    private static final String INVALID_END_TIME = "00:60";
    private static final String INVALID_DESCRIPTION = " ";

    private static final String VALID_EVENT_NAME = TUTORIAL.getEventName().toString();
    private static final String VALID_DATE = TUTORIAL.getDate().toString();
    private static final String VALID_START_TIME = TUTORIAL.getStartTime().toString();
    private static final String VALID_END_TIME = TUTORIAL.getEndTime().toString();
    private static final String VALID_DESCRIPTION = TUTORIAL.getDescription().get().toString();

    @Test
    public void equals_sameValidEventDetails() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(TUTORIAL);
        XmlAdaptedEvent otherEvent = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        assertEquals(event, otherEvent);
    }

    @Test
    public void equals_sameEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(TUTORIAL);
        assertEquals(event, event);
    }

    @Test
    public void equals_differentEvent_assertNotEquals() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(TUTORIAL);
        assertNotEquals(event, null);
    }

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(TUTORIAL);
        assertEquals(TUTORIAL, event.toModelType());
    }

    @Test
    public void toModelType_validEventWithoutDescription_returnsEvent() throws Exception {
        XmlAdaptedEvent event = new XmlAdaptedEvent(TUTORIAL_NO_DESCRIPTION);
        assertEquals(TUTORIAL_NO_DESCRIPTION, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(INVALID_EVENT_NAME,
                VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = EventName.EVENT_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventName_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(null,
                VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                INVALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = Date.DATE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                null, VALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, INVALID_START_TIME, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = StartTime.START_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, null, VALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StartTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, VALID_START_TIME, INVALID_END_TIME, VALID_DESCRIPTION);
        String expectedMessage = EndTime.END_TIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, VALID_START_TIME, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EndTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedEvent event = new XmlAdaptedEvent(VALID_EVENT_NAME,
                VALID_DATE, VALID_START_TIME, VALID_END_TIME, INVALID_DESCRIPTION);
        String expectedMessage = Description.DESCRIPTION_STRING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

}
