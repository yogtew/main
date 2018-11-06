package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

/*
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code CancelCommand}.
 */
public class CancelCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validEventUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(0);
        CancelCommand cancelCommand = new CancelCommand(eventToDelete);

        String expectedMessage = String.format(CancelCommand.MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.commitCalendar();

        assertCommandSuccess(cancelCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
