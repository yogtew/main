package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommandMode;
import seedu.address.model.mark.Mark;

public class TagCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);
    private static final String VALID_ADD_COMMAND = "add ";
    private static final String VALID_SET_COMMAND = "set ";
    private static final String VALID_DEL_COMMAND = "del ";
    private static final String INVALID_COMMAND = "asd ";
    private static final String VALID_MARK = "m/tut1 ";
    private static final String VALID_MARK_NAME = "tut1";
    private static final String INVALID_MARK = "m/tut@ ";

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // literally nothing
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // command but no index
        assertParseFailure(parser, "add", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "del", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "set", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, VALID_ADD_COMMAND + "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, VALID_ADD_COMMAND + "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, VALID_ADD_COMMAND + "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_ADD_COMMAND + "1 i/ string", MESSAGE_INVALID_FORMAT);

        // ======= Mark ========

        // invalid mark
        assertParseFailure(parser, VALID_ADD_COMMAND + INVALID_MARK, Mark.MARK_NAME_CONSTRAINTS);

        // valid mark but random stuff in preamble
        assertParseFailure(parser, VALID_ADD_COMMAND + "asd " + VALID_MARK, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid command
        assertParseFailure(parser, INVALID_COMMAND + "1", MESSAGE_INVALID_FORMAT);

        // valid command but invalid index
        assertParseFailure(parser, VALID_ADD_COMMAND + "-1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allSpecified_success() {
        assertParseSuccess(parser, VALID_ADD_COMMAND + "1",
                new TagCommand(Index.fromOneBased(1), new HashSet<>(), TagCommandMode.ADD));

        assertParseSuccess(parser, VALID_ADD_COMMAND + VALID_MARK,
                new TagCommand(VALID_MARK_NAME, new HashSet<>(), TagCommandMode.ADD));

        assertParseSuccess(parser, VALID_SET_COMMAND + "1",
                new TagCommand(Index.fromOneBased(1), new HashSet<>(), TagCommandMode.SET));

        assertParseSuccess(parser, VALID_SET_COMMAND + VALID_MARK,
                new TagCommand(VALID_MARK_NAME, new HashSet<>(), TagCommandMode.SET));

        assertParseSuccess(parser, VALID_DEL_COMMAND + "1",
                new TagCommand(Index.fromOneBased(1), new HashSet<>(), TagCommandMode.DEL));

        assertParseSuccess(parser, VALID_DEL_COMMAND + VALID_MARK,
                new TagCommand(VALID_MARK_NAME, new HashSet<>(), TagCommandMode.DEL));
    }


}
