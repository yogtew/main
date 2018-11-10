package seedu.address.model.event;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalEvents.TUTORIAL_NO_DESCRIPTION;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(TUTORIAL.isSameEvent(TUTORIAL));

        // null -> returns false
        assertFalse(TUTORIAL.isSameEvent(null));

        // different event name -> returns false
        Event editedTutorial = new EventBuilder(TUTORIAL).withEventName(VALID_EVENT_NAME_CONSULTATION).build();
        assertFalse(TUTORIAL.isSameEvent(editedTutorial));

        // different date -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withDate(VALID_DATE_CONSULTATION).build();
        assertFalse(TUTORIAL.isSameEvent(editedTutorial));

        // different start time -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withStartTime(VALID_START_TIME_CONSULTATION).build();
        assertFalse(TUTORIAL.isSameEvent(editedTutorial));

        // different end time -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withEndTime(VALID_END_TIME_CONSULTATION).build();
        assertFalse(TUTORIAL.isSameEvent(editedTutorial));

        // same event name, date, start time, and end time, but different description -> returns true
        editedTutorial = new EventBuilder(TUTORIAL).withDescription(VALID_DESCRIPTION_CONSULTATION).build();
        assertTrue(TUTORIAL.isSameEvent(editedTutorial));

        // same event name, date, start time, and end time, but no description -> returns true
        assertTrue(TUTORIAL.isSameEvent(TUTORIAL_NO_DESCRIPTION));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event consultationCopy = new EventBuilder(CONSULTATION).build();
        assertTrue(CONSULTATION.equals(consultationCopy));

        // same object -> returns true
        assertTrue(CONSULTATION.equals(CONSULTATION));

        // null -> returns false
        assertFalse(CONSULTATION.equals(null));

        // different type -> returns false
        assertFalse(CONSULTATION.equals(5));

        // different student -> returns false
        assertFalse(TUTORIAL.equals(CONSULTATION));

        // different event name -> returns false
        Event editedTutorial = new EventBuilder(TUTORIAL).withEventName(VALID_EVENT_NAME_CONSULTATION).build();
        assertFalse(TUTORIAL.equals(editedTutorial));

        // different date -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withDate(VALID_DATE_CONSULTATION).build();
        assertFalse(TUTORIAL.equals(editedTutorial));

        // different start time -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withStartTime(VALID_START_TIME_CONSULTATION).build();
        assertFalse(TUTORIAL.equals(editedTutorial));

        // different end time -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withEndTime(VALID_END_TIME_CONSULTATION).build();
        assertFalse(TUTORIAL.equals(editedTutorial));

        // different description -> returns false
        editedTutorial = new EventBuilder(TUTORIAL).withDescription(VALID_DESCRIPTION_CONSULTATION).build();
        assertFalse(TUTORIAL.equals(editedTutorial));
    }
}
