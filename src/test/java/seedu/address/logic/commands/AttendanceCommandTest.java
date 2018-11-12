package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.AttendanceEnum;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AttendanceCommand.
 */

public class AttendanceCommandTest {

    private static final String ATTENDANCE_STUB = "Some attendance";

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Successfully update attendance of student with unfiltered list
     */
    @Test
    public void execute_addAttendanceUnfilteredList_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(editedStudent.getAttendance().value.toString()));

        String expectedMessage = "Successfully updated attendance of 1 student";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Calendar(model.getCalendar()), new UserPrefs());
        expectedModel.updateStudent(firstStudent, editedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Successfully update attendance of student with filtered list
     */
    @Test
    public void execute_filteredList_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(model.getFilteredStudentList()
                .get(INDEX_FIRST_STUDENT.getZeroBased()))
                .withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(editedStudent.getAttendance().value.toString()));

        String expectedMessage = "Successfully updated attendance of 1 student";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Calendar(model.getCalendar()), new UserPrefs());
        expectedModel.updateStudent(firstStudent, editedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Index is not valid
     */
    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        AttendanceCommand attendanceCommand = new AttendanceCommand(outOfBoundIndex,
                new Attendance(VALID_ATTENDANCE_BOB));

        assertCommandFailure(attendanceCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of conTAct list
     */
    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);
        Index outOfBoundIndex = INDEX_SECOND_STUDENT;
        // ensures that outOfBoundIndex is still in bounds of conTAct list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        AttendanceCommand attendanceCommand = new AttendanceCommand(outOfBoundIndex,
                new Attendance(VALID_ATTENDANCE_BOB));

        assertCommandFailure(attendanceCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Updates attendance of student and then undo and redo modification
     * @throws Exception
     */
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Student studentToModify = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student modifiedStudent = new StudentBuilder(studentToModify).withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(ATTENDANCE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.updateStudent(studentToModify, modifiedStudent);
        expectedModel.commitAddressBook();

        // first student attendance changed
        attendanceCommand.execute(model, commandHistory);

        // undo -> reverts conTAct back to previous state and filtered student list to show all students
        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student modified again
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Undo Redo execution fails
     */
    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        AttendanceCommand attendanceCommand = new AttendanceCommand(outOfBoundIndex, new Attendance(""));

        // execution failed -> address book state not added into model
        assertCommandFailure(attendanceCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Modifies {@code Student} from a filtered list. Undo the modification.
     * The unfiltered list should be shown now. Verify that the index of the previously modified student in the
     * unfiltered list is different from the index at the filtered list.
     * Redo the modification. This ensures {@code RedoCommand} modifies the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStudentDeleted() throws Exception {
        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(ATTENDANCE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());

        showStudentAtIndex(model, INDEX_SECOND_STUDENT);
        Student studentToModify = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student modifiedStudent = new StudentBuilder(studentToModify).withAttendance(ATTENDANCE_STUB).build();
        expectedModel.updateStudent(studentToModify, modifiedStudent);
        expectedModel.commitAddressBook();

        // modifies second student in unfiltered student list / first student in filtered student list
        attendanceCommand.execute(model, commandHistory);

        // undo -> reverts address book back to previous state and filtered student list to show all students
        expectedModel.undo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second student in unfiltered student list
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final AttendanceCommand standardIndexCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY));
        // same values -> returns true
        AttendanceCommand indexCommandWithSameValues = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY));
        assertTrue(standardIndexCommand.equals(indexCommandWithSameValues));

        // same object -> returns true
        assertTrue(standardIndexCommand.equals(standardIndexCommand));

        // null -> returns false
        assertFalse(standardIndexCommand.equals(null));

        // different types -> returns false
        assertFalse(standardIndexCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardIndexCommand.equals(new AttendanceCommand(INDEX_SECOND_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY))));

        assertEquals(new Attendance(AttendanceEnum.UNDEFINED), new Attendance(AttendanceEnum.UNDEFINED));
        assertEquals(new Attendance(AttendanceEnum.ABSENT), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance(AttendanceEnum.PRESENT), new Attendance(AttendanceEnum.PRESENT));

        assertEquals(new Attendance("0"), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance("1"), new Attendance(AttendanceEnum.PRESENT));
        assertEquals(new Attendance(" "), new Attendance(AttendanceEnum.UNDEFINED));

        // different attendance -> returns false
        assertFalse(standardIndexCommand.equals(new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance("1"))));

        String markName = "tut1";
        final AttendanceCommand standardMarkCommand = new AttendanceCommand(markName,
                new Attendance(VALID_ATTENDANCE_AMY));

        // same values -> returns true
        AttendanceCommand markCommandWithSameValues = new AttendanceCommand(markName,
                new Attendance(VALID_ATTENDANCE_AMY));
        assertTrue(standardMarkCommand.equals(markCommandWithSameValues));

        // same object -> returns true
        assertTrue(standardMarkCommand.equals(standardMarkCommand));

        // null -> returns false
        assertFalse(standardMarkCommand.equals(null));

        // different types -> returns false
        assertFalse(standardMarkCommand.equals(new ClearCommand()));

        // different markName -> return false
        String otherMarkName = "tut2";
        assertFalse(standardMarkCommand.equals(new AttendanceCommand(otherMarkName,
                new Attendance(VALID_ATTENDANCE_AMY))));

        // different attendance -> return false
        assertFalse(standardIndexCommand.equals(new AttendanceCommand(markName,
                new Attendance("1"))));
    }
}
