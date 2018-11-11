package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import seedu.address.commons.core.index.Index;
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

        // if both mark and index are absent -> throw exception
        if (!argMultimap.getValue(PREFIX_MARK).isPresent() && (argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_MARK).isPresent()) {
            // if mark is present, return new AttendanceCommand with the parameters
            String markName = checkMark(argMultimap.getValue(PREFIX_MARK).orElse(Mark.DEFAULT_NAME));
            String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).get();
            return new AttendanceCommand(markName, new Attendance(attendance));
        } else {
            try {
                // if index is present, return new AttendanceCommand with the parameters
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).get();
                return new AttendanceCommand(index, new Attendance(attendance));
            } catch (ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AttendanceCommand.MESSAGE_USAGE));
            }
        }
    }

    /**
     * checks if the mark name is valid otherwise throws an exception
     * @param name markName entered by the user
     * @return name
     * @throws ParseException with the mark name constraints
     */
    private String checkMark(String name) throws ParseException {
        if (!Mark.isValidMarkName(name)) {
            throw new ParseException(Mark.MARK_NAME_CONSTRAINTS);
        }
        return name;
    }
}
