package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIMES_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_AND_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_TUTORIAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;

import org.junit.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.EventBuilder;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(CONSULTATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_CONSULTATION + DATE_DESC_CONSULTATION
                + START_TIME_DESC_CONSULTATION + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));

        // multiple event names - last event name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_TUTORIAL + EVENT_NAME_DESC_CONSULTATION
                        + DATE_DESC_CONSULTATION + START_TIME_DESC_CONSULTATION
                        + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));

        // multiple dates - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_CONSULTATION
                        + DATE_DESC_TUTORIAL + DATE_DESC_CONSULTATION + START_TIME_DESC_CONSULTATION
                        + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));

        // multiple start times - last start time accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_CONSULTATION
                        + DATE_DESC_CONSULTATION + START_TIME_DESC_TUTORIAL + START_TIME_DESC_CONSULTATION
                        + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));

        // multiple end times - last end time accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_CONSULTATION
                        + DATE_DESC_CONSULTATION + START_TIME_DESC_CONSULTATION
                        + END_TIME_DESC_TUTORIAL + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_CONSULTATION
                        + DATE_DESC_CONSULTATION + START_TIME_DESC_CONSULTATION
                        + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_TUTORIAL + DESCRIPTION_DESC_CONSULTATION,
                new ScheduleCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

        // missing event name prefix
        assertParseFailure(parser,
                VALID_EVENT_NAME_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + VALID_DATE_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                expectedMessage);

        // missing start time prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + VALID_START_TIME_CONSULTATION
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                expectedMessage);

        // missing end time prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + VALID_END_TIME_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + VALID_DESCRIPTION_TUTORIAL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_EVENT_NAME_TUTORIAL
                        + VALID_DATE_TUTORIAL
                        + VALID_START_TIME_TUTORIAL
                        + VALID_END_TIME_CONSULTATION
                        + VALID_DESCRIPTION_TUTORIAL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser,
                INVALID_EVENT_NAME_DESC
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                EventName.EVENT_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + INVALID_DATE_DESC
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                Date.DATE_NAME_CONSTRAINTS);

        // invalid start time
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + INVALID_START_TIME_DESC
                        + END_TIME_DESC_TUTORIAL
                        + DESCRIPTION_DESC_TUTORIAL,
                StartTime.START_TIME_CONSTRAINTS);

        // invalid end time
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + INVALID_END_TIME_DESC
                        + DESCRIPTION_DESC_TUTORIAL,
                EndTime.END_TIME_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + INVALID_DESCRIPTION_DESC,
                Description.DESCRIPTION_STRING_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_EVENT_NAME_DESC
                        + DATE_DESC_TUTORIAL
                        + START_TIME_DESC_TUTORIAL
                        + END_TIME_DESC_TUTORIAL
                        + INVALID_DESCRIPTION_DESC,
                EventName.EVENT_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_CONSULTATION + DATE_DESC_CONSULTATION
                        + START_TIME_DESC_CONSULTATION + END_TIME_DESC_CONSULTATION + DESCRIPTION_DESC_CONSULTATION,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStartEndTimes_failure() {
        // start time after the end time
        assertParseFailure(parser,
                EVENT_NAME_DESC_TUTORIAL
                + DATE_DESC_TUTORIAL
                + INVALID_START_AND_END_DESC
                + DESCRIPTION_DESC_TUTORIAL, MESSAGE_INVALID_TIMES_FORMAT);
    }
}
