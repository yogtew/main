package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_tag_valid() {
        FindCommand expectedFindCommand =
                new FindCommand(new IsTaggedPredicate(SampleDataUtil.getTagSet("friends", "colleagues")));
        // multiple tags
        assertParseSuccess(parser, " t/friends t/colleagues", expectedFindCommand);
    }

    @Test
    public void parse_tag_invalid() {
        // empty tag name
        assertParseFailure(parser, " t/", Tag.MESSAGE_TAG_CONSTRAINTS);

        // no arguments
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
