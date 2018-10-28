package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.model.person.Attendance;

public class AttendanceCommandParserTest {
    private AttendanceCommandParser parser = new AttendanceCommandParser();
    private final String nonEmptyAttendance = "Some attendance.";

    @Test
    public void parse_indexSpecified_success() {
        // have attendance
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_ATTENDANCE + nonEmptyAttendance;
        AttendanceCommand expectedCommand = new AttendanceCommand(INDEX_FIRST_PERSON,
                new Attendance(nonEmptyAttendance));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no attendance
        userInput = targetIndex.getOneBased() + " " + PREFIX_ATTENDANCE;
        expectedCommand = new AttendanceCommand(INDEX_FIRST_PERSON, new Attendance(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, AttendanceCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, AttendanceCommand.COMMAND_WORD + " " + nonEmptyAttendance, expectedMessage);
    }
}
