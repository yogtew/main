package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.group.GroupNotFoundException;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.testutil.EventBuilder;

public class ScheduleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ScheduleCommand(null);
    }

    @Test
    public void execute_eventAcceptedByModel_scheduleSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        Event validEvent = new EventBuilder().build();

        CommandResult commandResult = new ScheduleCommand(validEvent).execute(modelStub, commandHistory);

        assertEquals(String.format(ScheduleCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEvent), modelStub.eventsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() throws Exception {
        Event validEvent = new EventBuilder().build();
        ScheduleCommand scheduleCommand = new ScheduleCommand(validEvent);
        ModelStub modelStub = new ModelStubWithEvent(validEvent);

        thrown.expect(CommandException.class);
        thrown.expectMessage(ScheduleCommand.MESSAGE_DUPLICATE_EVENT);
        scheduleCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Event consultation = new EventBuilder().withEventName("Consultation").build();
        Event tutorial = new EventBuilder().withEventName("Tutorial").build();
        ScheduleCommand scheduleConsultationCommand = new ScheduleCommand(consultation);
        ScheduleCommand scheduleTutorialCommand = new ScheduleCommand(tutorial);

        // same object -> returns true
        assertTrue(scheduleConsultationCommand.equals(scheduleConsultationCommand));

        // same values -> returns true
        ScheduleCommand scheduleConsultationCommandCopy = new ScheduleCommand(consultation);
        assertTrue(scheduleConsultationCommand.equals(scheduleConsultationCommandCopy));

        // different types -> returns false
        assertFalse(scheduleConsultationCommand.equals(0));

        // null -> returns false
        assertFalse(scheduleConsultationCommand.equals(null));

        // different event -> returns false
        assertFalse(scheduleConsultationCommand.equals(scheduleTutorialCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyCalendar newCalendar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        private void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        private void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCalendar getCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        private void undoCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        private void redoCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Group getGroup(String groupName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroup(String groupName, Group group) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Group> getFilteredGroupList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGroupPredicate(String groupName) throws GroupNotFoundException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetView() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single event.
     */
    private class ModelStubWithEvent extends ModelStub {
        private final Event event;

        ModelStubWithEvent(Event event) {
            requireNonNull(event);
            this.event = event;
        }

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return this.event.isSameEvent(event);
        }
    }

    /**
     * A Model stub that always accepts the event being added.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            eventsAdded.add(event);
        }

        @Override
        public void commitCalendar() {
            // called by {@code ScheduleCommand#execute()}
        }

        @Override
        public ReadOnlyCalendar getCalendar() {
            return new Calendar();
        }
    }

}
