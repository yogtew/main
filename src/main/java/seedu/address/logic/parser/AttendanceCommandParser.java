package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_ATTENDANCE);
        Index index;

        if (!argMultimap.getValue(PREFIX_GROUP).isPresent() && (argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_GROUP).isPresent()) {
            String groupName = checkGroup(argMultimap.getValue(PREFIX_GROUP).orElse(Group.DEFAULT_NAME));
            String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).get();
            return new AttendanceCommand(groupName, new Attendance(attendance));
        } else {
            try {
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
     * checks if the group name is valid otherwise throws an exception
     * @param name
     * @return
     * @throws ParseException
     */
    private String checkGroup(String name) throws ParseException {
        if (!Group.isValidGroupName(name)) {
            throw new ParseException(Group.GROUP_NAME_CONSTRAINTS);
        }
        return name;
    }
}
