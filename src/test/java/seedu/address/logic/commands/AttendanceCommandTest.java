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

    @Test
    public void execute_addAttendanceUnfilteredList_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(firstStudent).withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(editedStudent.getAttendance().value.toString()));

        String expectedMessage = String.format(AttendanceCommand.MESSAGE_ADD_ATTENDANCE_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Calendar(), new UserPrefs());
        expectedModel.updateStudent(firstStudent, editedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        Student firstStudent = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student editedStudent = new StudentBuilder(model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased()))
                .withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(editedStudent.getAttendance().value.toString()));

        String expectedMessage = String.format(AttendanceCommand.MESSAGE_ADD_ATTENDANCE_SUCCESS, editedStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new Calendar(), new UserPrefs());
        expectedModel.updateStudent(firstStudent, editedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(attendanceCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        AttendanceCommand attendanceCommand = new AttendanceCommand(outOfBoundIndex,
                new Attendance(VALID_ATTENDANCE_BOB));

        assertCommandFailure(attendanceCommand, model, commandHistory, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
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

        assertCommandFailure(attendanceCommand, model, commandHistory, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Student studentToModify = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student modifiedStudent = new StudentBuilder(studentToModify).withAttendance(ATTENDANCE_STUB).build();

        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(ATTENDANCE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new Calendar(), new UserPrefs());
        expectedModel.updateStudent(studentToModify, modifiedStudent);
        expectedModel.commitAddressBook();

        // first student attendance changed
        attendanceCommand.execute(model, commandHistory);

        // undo -> reverts conTAct back to previous state and filtered student list to show all students
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first student modified again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        AttendanceCommand attendanceCommand = new AttendanceCommand(outOfBoundIndex, new Attendance(""));

        // execution failed -> address book state not added into model
        assertCommandFailure(attendanceCommand, model, commandHistory, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Student#remark} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified student in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the student object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameStudentDeleted() throws Exception {
        AttendanceCommand attendanceCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(ATTENDANCE_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new Calendar(), new UserPrefs());

        showStudentAtIndex(model, INDEX_SECOND_STUDENT);
        Student studentToModify = model.getFilteredStudentList().get(INDEX_FIRST_STUDENT.getZeroBased());
        Student modifiedStudent = new StudentBuilder(studentToModify).withAttendance(ATTENDANCE_STUB).build();
        expectedModel.updateStudent(studentToModify, modifiedStudent);
        expectedModel.commitAddressBook();

        // modifies second student in unfiltered student list / first student in filtered student list
        attendanceCommand.execute(model, commandHistory);

        // undo -> reverts address book back to previous state and filtered student list to show all students
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second student in unfiltered student list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final AttendanceCommand standardCommand = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY));
        // same values -> returns true
        AttendanceCommand commandWithSameValues = new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_SECOND_STUDENT,
                new Attendance(VALID_ATTENDANCE_AMY))));

        assertEquals(new Attendance(AttendanceEnum.UNDEFINED), new Attendance(AttendanceEnum.UNDEFINED));
        assertEquals(new Attendance(AttendanceEnum.ABSENT), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance(AttendanceEnum.PRESENT), new Attendance(AttendanceEnum.PRESENT));

        assertEquals(new Attendance("0"), new Attendance(AttendanceEnum.ABSENT));
        assertEquals(new Attendance("1"), new Attendance(AttendanceEnum.PRESENT));
        assertEquals(new Attendance(" "), new Attendance(AttendanceEnum.UNDEFINED));

        // different attendance -> returns false
        assertFalse(standardCommand.equals(new AttendanceCommand(INDEX_FIRST_STUDENT,
                new Attendance("1"))));
    }
}
