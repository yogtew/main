package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AttendanceCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.AttendanceEnum;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AttendanceCommand.
 */

public class AttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());

    @Test
    public void execute() {
        final Attendance attendance = new Attendance("Some attendance");
        assertCommandFailure(new AttendanceCommand(INDEX_FIRST_PERSON, attendance), model, new CommandHistory(),
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), attendance));
    }

    @Test
    public void equals() {
        final AttendanceCommand standardCommand = new AttendanceCommand(INDEX_FIRST_PERSON,
                new Attendance(VALID_ATTENDANCE_AMY));
        // same values -> returns true
        AttendanceCommand commandWithSameValues = new AttendanceCommand(INDEX_FIRST_PERSON,
                new Attendance(VALID_ATTENDANCE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_SECOND_PERSON,
                new Attendance(VALID_ATTENDANCE_AMY))));

        assertEquals(new Attendance(AttendanceEnum.UNDEFINED), new Attendance(AttendanceEnum.UNDEFINED));
        assertEquals(new Attendance(AttendanceEnum.ABSENT), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance(AttendanceEnum.PRESENT), new Attendance(AttendanceEnum.PRESENT));

        assertEquals(new Attendance("0"), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance("1"), new Attendance(AttendanceEnum.PRESENT));
        assertEquals(new Attendance(" "), new Attendance(AttendanceEnum.UNDEFINED));

        // different attendance -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_FIRST_PERSON,
                new Attendance("1"))));
    }
}
