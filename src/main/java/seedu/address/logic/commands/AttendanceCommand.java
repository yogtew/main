package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_WIP_COMMAND;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.mark.IMarkExecutable;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;
import seedu.address.model.person.Attendance;

/**
 * UPDATES ATTENDANCE OF A STUDENT IN THE ADDRESS BOOK.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the attendance of a student. "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ATTENDANCE + "[ATTENDANCE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "Present.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Attendance: %2$s";

    private final Index index;
    private final Attendance attendance;

    /**
     * @param index of the student to mark the attendance of
     * @param attendance of the student
     */
    public AttendanceCommand(Index index, Attendance attendance) {
        requireAllNonNull(index, attendance);

        this.index = index;
        this.attendance = attendance;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), attendance));
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
        return index.equals(a.index)
                && attendance.equals(a.attendance);
    }

}
