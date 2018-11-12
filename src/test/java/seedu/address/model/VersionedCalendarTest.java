package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.CalendarBuilder;

public class VersionedCalendarTest {

    private final ReadOnlyCalendar calendarWithTutorial = new CalendarBuilder().withEvent(TUTORIAL).build();
    private final ReadOnlyCalendar calendarWithConsultation = new CalendarBuilder().withEvent(CONSULTATION).build();
    private final ReadOnlyCalendar emptyCalendar = new CalendarBuilder().build();

    @Test
    public void commit_singleCalendar_noStatesRemovedCurrentStateSaved() {
        VersionedCalendar versionedCalendar = prepareCalendarList(emptyCalendar);

        versionedCalendar.commit();
        assertCalendarListStatus(versionedCalendar,
                Collections.singletonList(emptyCalendar),
                emptyCalendar,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleCalendarPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedCalendar versionedCalendar = prepareCalendarList(
                emptyCalendar, calendarWithTutorial, calendarWithConsultation);

        versionedCalendar.commit();
        assertCalendarListStatus(versionedCalendar,
                Arrays.asList(emptyCalendar, calendarWithTutorial, calendarWithConsultation),
                calendarWithConsultation,
                Collections.emptyList());
    }

    @Test
    public void canUndo_singleCalendar_returnsFalse() {
        VersionedCalendar versionedCalendar = prepareCalendarList(emptyCalendar);

        assertFalse(versionedCalendar.canUndo());
    }

    @Test
    public void canUndo_multipleCalendarPointerAtStartOfStateList_returnsFalse() {
        VersionedCalendar versionedCalendar = prepareCalendarList(
                emptyCalendar, calendarWithTutorial, calendarWithConsultation);
        shiftCurrentStatePointerLeftwards(versionedCalendar, 2);

        assertFalse(versionedCalendar.canUndo());
    }

    @Test
    public void undo_singleCalendar_throwsNoUndoableStateException() {
        VersionedCalendar versionedCalendar = prepareCalendarList(emptyCalendar);

        assertThrows(VersionedCalendar.NoUndoableStateException.class, versionedCalendar::undo);
    }

    @Test
    public void canRedo_singleCalendar_returnsFalse() {
        VersionedCalendar versionedCalendar = prepareCalendarList(emptyCalendar);

        assertFalse(versionedCalendar.canRedo());
    }

    @Test
    public void canRedo_calendarPointerAtEndOfStateList_returnsFalse() {
        VersionedCalendar versionedCalendar = prepareCalendarList(
                emptyCalendar, calendarWithTutorial, calendarWithConsultation);

        assertFalse(versionedCalendar.canRedo());
    }

    @Test
    public void redo_calendar_throwsNoRedoableStateException() {
        VersionedCalendar versionedCalendar = prepareCalendarList(emptyCalendar);

        assertThrows(VersionedCalendar.NoRedoableStateException.class, versionedCalendar::redo);
    }

    @Test
    public void equals() {
        VersionedCalendar versionedCalendar = prepareCalendarList(calendarWithTutorial, calendarWithConsultation);

        // same values -> returns true
        VersionedCalendar copy = prepareCalendarList(calendarWithTutorial, calendarWithConsultation);
        assertTrue(versionedCalendar.equals(copy));

        // same object -> returns true
        assertTrue(versionedCalendar.equals(versionedCalendar));

        // null -> returns false
        assertFalse(versionedCalendar.equals(null));

        // different types -> returns false
        assertFalse(versionedCalendar.equals(1));

        // different state list -> returns false
        VersionedCalendar differentCalendarList = prepareCalendarList(calendarWithConsultation, calendarWithTutorial);
        assertFalse(versionedCalendar.equals(differentCalendarList));

        // different current pointer index -> returns false
        VersionedCalendar differentCurrentStatePointer = prepareCalendarList(
                calendarWithTutorial, calendarWithConsultation);
        shiftCurrentStatePointerLeftwards(versionedCalendar, 1);
        assertFalse(versionedCalendar.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedCalendar} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedCalendar#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedCalendar#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertCalendarListStatus(VersionedCalendar versionedCalendar,
                                             List<ReadOnlyCalendar> expectedStatesBeforePointer,
                                             ReadOnlyCalendar expectedCurrentState,
                                             List<ReadOnlyCalendar> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Calendar(versionedCalendar), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedCalendar.canUndo()) {
            versionedCalendar.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyCalendar expectedCalendar : expectedStatesBeforePointer) {
            assertEquals(expectedCalendar, new Calendar(versionedCalendar));
            versionedCalendar.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyCalendar expectedCalendar : expectedStatesAfterPointer) {
            versionedCalendar.redo();
            assertEquals(expectedCalendar, new Calendar(versionedCalendar));
        }

        // check that there are no more states after pointer
        assertFalse(versionedCalendar.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedCalendar.undo());
    }

    /**
     * Creates and returns a {@code VersionedCalendar} with the {@code calendarStates} added into it, and the
     * {@code VersionedCalendar#currentStatePointer} at the end of list.
     */
    private VersionedCalendar prepareCalendarList(ReadOnlyCalendar... calendarStates) {
        assertFalse(calendarStates.length == 0);

        VersionedCalendar versionedCalendar = new VersionedCalendar(calendarStates[0]);
        for (int i = 1; i < calendarStates.length; i++) {
            versionedCalendar.resetData(calendarStates[i]);
            versionedCalendar.commit();
        }

        return versionedCalendar;
    }

    /**
     * Shifts the {@code versionedCalendar#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedCalendar versionedCalendar, int count) {
        for (int i = 0; i < count; i++) {
            versionedCalendar.undo();
        }
    }
}
