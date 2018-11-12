package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.model.group.Group;
import seedu.address.model.student.Attendance;

public class AttendanceCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE);
    private static final String VALID_GROUP = "g/tut1 ";
    private static final String VALID_GROUP_NAME = "tut1";
    private static final String INVALID_GROUP = "g/tut@ ";
    private static final String VALID_ATTENDANCE_VALUE = "1";
    private static final String VALID_ATTENDANCE = "at/1 ";
    private static final String INVALID_ATTENDANCE = "234";

    private AttendanceCommandParser parser = new AttendanceCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index
        assertParseFailure(parser, "attendance", MESSAGE_INVALID_FORMAT);

        // no parameters
        assertParseFailure(parser, AttendanceCommand.COMMAND_WORD, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as index
        assertParseFailure(parser, "some string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as index
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

        // invalid group
        assertParseFailure(parser, VALID_ATTENDANCE + INVALID_GROUP, Group.GROUP_NAME_CONSTRAINTS);

        // valid group but random stuff in preamble
        assertParseFailure(parser, PREFIX_ATTENDANCE + "some string" + VALID_GROUP, MESSAGE_INVALID_FORMAT);

        // negative attendance
        assertParseFailure(parser, PREFIX_ATTENDANCE + "-1", MESSAGE_INVALID_FORMAT);

        // invalid attendance
        assertParseFailure(parser, PREFIX_ATTENDANCE + INVALID_ATTENDANCE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // valid command but invalid index
        assertParseFailure(parser, "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allSpecified_success() {
        // with index
        assertParseSuccess(parser, "1 " + VALID_ATTENDANCE,
                new AttendanceCommand(Index.fromOneBased(1), new Attendance(VALID_ATTENDANCE_VALUE)));

        // with group
        assertParseSuccess(parser, " " + VALID_GROUP + VALID_ATTENDANCE,
                new AttendanceCommand(VALID_GROUP_NAME, new Attendance(VALID_ATTENDANCE_VALUE)));
    }
}
