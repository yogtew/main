package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_BOB;
import static seedu.address.logic.commands.AttendanceCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AttendanceCommand.
 */

public class AttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String attendance = "Some attendance";
        assertCommandFailure(new AttendanceCommand(INDEX_FIRST_PERSON, attendance), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), attendance));
    }

    @Test
    public void equals() {
        final AttendanceCommand standardCommand = new AttendanceCommand(INDEX_FIRST_PERSON, VALID_ATTENDANCE_AMY);
        // same values -> returns true
         AttendanceCommand commandWithSameValues = new AttendanceCommand(INDEX_FIRST_PERSON, VALID_ATTENDANCE_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_SECOND_PERSON, VALID_ATTENDANCE_AMY)));

        // different attendance -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_FIRST_PERSON, VALID_ATTENDANCE_BOB)));
    }
}
