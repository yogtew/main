package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Attendance;

/**
 * Parses input arguments and creates a new {@code AttendanceCommand} object
 */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AttendanceCommand}
     * and returns a {@code AttendanceCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MARK, PREFIX_ATTENDANCE);
        Index index;

        String[] splitArgs = argMultimap.getPreamble().split(" ");
        if (splitArgs.length == 0 || splitArgs.length > 2) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getAllValues(PREFIX_MARK).size() == 1) {
            if (splitArgs.length == 2) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AttendanceCommand.MESSAGE_USAGE));
            }
            String markName = checkAlias(argMultimap.getValue(PREFIX_MARK).orElse(Mark.DEFAULT_NAME));
            String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).orElse("");
            return new AttendanceCommand(markName, new Attendance(attendance));
        } else if (splitArgs.length == 1) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceCommand.MESSAGE_USAGE));
        } else {
            try {
                index = ParserUtil.parseIndex(splitArgs[1]);
                String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).orElse("");
                return new AttendanceCommand(index, new Attendance(attendance));
            } catch (ParseException e) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AttendanceCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * checks if the mark name is valid otherwise throws an exception
     * @param name
     * @return
     * @throws ParseException
     */
    private String checkAlias(String name) throws ParseException {
        if (!Mark.isValidMarkName(name)) {
            throw new ParseException(Mark.MARK_NAME_CONSTRAINTS);
        }
        return name;
    }
}
