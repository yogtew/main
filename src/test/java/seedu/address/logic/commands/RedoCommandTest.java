package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.addTypicalEvent;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstEvent;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstStudent;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(),
            getTypicalCalendar(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstStudent(model);
        deleteFirstEvent(model);
        model.undo();
        model.undo();

        deleteFirstStudent(expectedModel);
        deleteFirstEvent(expectedModel);
        expectedModel.undo();
        expectedModel.undo();
    }

    @Test
    public void execute_redoAllStates_success() {
        // multiple redoable states in model
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoThenCommit() {
        // multiple redoable states in model
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        addTypicalEvent(model);
        addTypicalEvent(expectedModel);

        // no redoable state in model as the model has been committed.
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
