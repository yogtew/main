package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.TypicalEvents;

/*
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CancelCommand}.
 */
public class CancelCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validEventList_success() {
        Event eventToDelete = model.getFilteredEventList().get(0);
        CancelCommand cancelCommand = new CancelCommand(eventToDelete);

        String expectedMessage = String.format(CancelCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitCalendar();

        assertCommandSuccess(cancelCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventList_throwsCommandException() {
        Event eventNotInModel = TypicalEvents.EVENT_NOT_PRESENT;
        CancelCommand cancelCommand = new CancelCommand(eventNotInModel);

        assertCommandFailure(cancelCommand, model, commandHistory, Messages.MESSAGE_INVALID_EVENT);
    }

    /**
     * 1. Deletes a {@code Event} from the filtered event list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the event list is the same as before deletion.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validEventList_sameEventDeleted() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());

        Event eventToDelete = model.getFilteredEventList().get(0);
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitCalendar();

        // cancel -> deletes the first event in the event list
        CancelCommand cancelCommand = new CancelCommand(eventToDelete);
        cancelCommand.execute(model, commandHistory);

        // undo -> reverts calendar back to previous state
        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> deletes same event in unfiltered event list
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        CancelCommand cancelFirstCommand = new CancelCommand(TypicalEvents.CONSULTATION);
        CancelCommand cancelSecondCommand = new CancelCommand(TypicalEvents.TUTORIAL);

        // same object -> returns true
        assertTrue(cancelFirstCommand.equals(cancelFirstCommand));

        // same values -> returns true
        CancelCommand cancelFirstCommandCopy = new CancelCommand(TypicalEvents.CONSULTATION);
        assertTrue(cancelFirstCommand.equals(cancelFirstCommandCopy));

        // different types -> returns false
        assertFalse(cancelFirstCommand.equals(1));

        // null -> returns false
        assertFalse(cancelFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(cancelFirstCommand.equals(cancelSecondCommand));
    }
}
