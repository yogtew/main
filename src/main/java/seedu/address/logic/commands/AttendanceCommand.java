package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.student.Attendance;

/**
 * UPDATES ATTENDANCE OF A STUDENT IN THE ADDRESS BOOK.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the attendance of a student "
            + "by the index number used in the displayed student list or by using a group name\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Attendance can be 1 (Present) or 0 (Absent).\n"
            + "Parameters: INDEX (must be a positive integer)|"
            + PREFIX_GROUP + " GROUP "
            + PREFIX_ATTENDANCE + "[ATTENDANCE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "1 OR "
            + COMMAND_WORD + " groupname "
            + PREFIX_ATTENDANCE + "1";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Added attendance for student: %1$s";
    public static final String MESSAGE_REMOVE_ATTENDANCE_SUCCESS = "Removed attendance for student: %1$s";

    private Index index;
    private final Attendance attendance;
    private String groupName = "";
    private boolean useGroup;

    /**
     * @param index of the student to group the attendance of
     * @param attendance of the student
     */
    public AttendanceCommand(Index index, Attendance attendance) {
        requireAllNonNull(index, attendance);
        this.index = index;
        this.attendance = attendance;
        useGroup = false;
    }

    /**
     *
     * @param groupName
     * @param attendance
     */
    public AttendanceCommand(String groupName, Attendance attendance) {
        requireAllNonNull(groupName, attendance);
        this.groupName = groupName;
        this.attendance = attendance;
        useGroup = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (useGroup) {
            return executeGroup(model, history);
        }

        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());

        processStudent(model, history, studentToEdit);
        model.commitAddressBook();
        return formatCommandResult(1);
    }

    /**
     * Performs the command on one person
     * @param model
     * @param history
     * @param target
     */
    private void processStudent(Model model, CommandHistory history, Student target) {
        Student editedStudent = new Student(target.getName(),
                target.getStudentNumber(), target.getEmail(),
                target.getFaculty(), attendance, target.getTags());

        model.updateStudent(target, editedStudent);
    }

    /**
     * executeGroup method which processes students in the group
     * @param model
     * @param history
     * @return
     */
    public CommandResult executeGroup(Model model, CommandHistory history) throws CommandException {
        Group m = model.getGroup(groupName);
        m.getList().forEach(p -> {
            processStudent(model, history, p);
        });
        model.commitAddressBook();
        return formatCommandResult(m.count());
    }

    /**
     * Formats a CommandResult to be returned
     * @param n
     * @return
     */
    private CommandResult formatCommandResult(int n) {
        String pluralName = n == 1 ? "student" : "students";

        return new CommandResult(String.format("Successfully updated attendance of %d %s", n, pluralName));
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof AttendanceCommand)) {
            return false;
        }

        AttendanceCommand a = (AttendanceCommand) object;
        if (index == null) {
            return groupName.equals(a.groupName) && attendance.equals(a.attendance);
        } else {
            return index.equals(a.index) && attendance.equals(a.attendance);
        }
    }

}
