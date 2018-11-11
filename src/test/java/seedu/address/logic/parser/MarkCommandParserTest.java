package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.MarkCommandParser.MESSAGE_INVALID_MARK_NAME;
import static seedu.address.logic.parser.MarkCommandParser.MESSAGE_INVALID_SUBCOMMAND;

import org.junit.Test;

import seedu.address.logic.commands.mark.MarkAndCommand;
import seedu.address.logic.commands.mark.MarkCommand;
import seedu.address.logic.commands.mark.MarkFindCommand;
import seedu.address.logic.commands.mark.MarkJoinCommand;
import seedu.address.logic.commands.mark.MarkShowCommand;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.tag.Tag;

public class MarkCommandParserTest {
    private MarkCommandParser parser = new MarkCommandParser();
    private String showSubCommand = "show";
    private String findSubCommand = "find";
    private String joinSubCommand = "join";
    private String andSubCommand = "and";
    private String validMarkName = "validMarkName";
    private String anotherValidMarkName = "anotherValidMarkName";
    private String alsoAValidMarkName = "alsoAValidMarkName";
    private String invalidMarkName = "invalidMarkName!";
    private String validTagName = "validTagName";
    private String invalidTagName = "invalidTagName!";

    @Test
    public void empty() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_SUBCOMMAND, ""));
    }

    @Test
    public void find() {
        // show without any arguments -> fail
        assertParseFailure(parser, findSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));

        // find with a valid tag
        assertParseSuccess(parser, findSubCommand + " t/" + validTagName,
                new MarkFindCommand(new IsTaggedPredicate(validTagName), Mark.DEFAULT_NAME));

        // find with a invalid tag
        assertParseFailure(parser, findSubCommand + " t/" + invalidTagName,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // find with a valid tag and valid mark
        assertParseSuccess(parser, PREFIX_MARK + validMarkName + " " + findSubCommand + " t/" + validTagName,
                new MarkFindCommand(new IsTaggedPredicate(validTagName), validMarkName));
    }

    @Test
    public void show() {
        // find without any arguments
        assertParseSuccess(parser, showSubCommand, new MarkShowCommand(Mark.DEFAULT_NAME));

        // show with an alias
        assertParseSuccess(parser, PREFIX_MARK + validMarkName + " "
                + showSubCommand, new MarkShowCommand(validMarkName));

        // show with an invalid alias
        assertParseFailure(parser, PREFIX_MARK + invalidMarkName + " " + showSubCommand,
                String.format(MESSAGE_INVALID_MARK_NAME, invalidMarkName, Mark.MARK_NAME_CONSTRAINTS));
    }

    @Test
    public void join() {
        // mark [m1] join m2 [m3]
        // no arguments - fail
        assertParseFailure(parser, joinSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkJoinCommand.MESSAGE_USAGE));

        // m1 - fail
        assertParseFailure(parser, " " + PREFIX_MARK + validMarkName + " " + joinSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkJoinCommand.MESSAGE_USAGE));

        // join with m2
        assertParseSuccess(parser, joinSubCommand + " " + PREFIX_MARK + validMarkName,
                new MarkJoinCommand(Mark.DEFAULT_NAME, validMarkName, Mark.DEFAULT_NAME));

        // join with m2, m3
        assertParseSuccess(parser, joinSubCommand + " " + PREFIX_MARK
                        + validMarkName + " " + PREFIX_MARK + anotherValidMarkName,
                new MarkJoinCommand(Mark.DEFAULT_NAME, validMarkName, anotherValidMarkName));

        // join with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_MARK + validMarkName + " " + joinSubCommand
                        + " " + PREFIX_MARK + anotherValidMarkName
                        + " " + PREFIX_MARK + alsoAValidMarkName,
                new MarkJoinCommand(validMarkName, anotherValidMarkName, alsoAValidMarkName));

        // join with m1, m2
        // join with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_MARK + validMarkName + " " + joinSubCommand
                        + " " + PREFIX_MARK + anotherValidMarkName,
                new MarkJoinCommand(validMarkName, anotherValidMarkName, Mark.DEFAULT_NAME));
    }

    @Test
    public void and() {
        // mark [m1] and m2 [m3]

        // no arguments - fail
        assertParseFailure(parser, andSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAndCommand.MESSAGE_USAGE));

        // m1 - fail
        assertParseFailure(parser, " " + PREFIX_MARK + validMarkName + " " + andSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAndCommand.MESSAGE_USAGE));

        // and with m2
        assertParseSuccess(parser, andSubCommand + " " + PREFIX_MARK + validMarkName,
                new MarkAndCommand(Mark.DEFAULT_NAME, validMarkName, Mark.DEFAULT_NAME));

        // and with m2, m3
        assertParseSuccess(parser, andSubCommand + " " + PREFIX_MARK
                        + validMarkName + " " + PREFIX_MARK + anotherValidMarkName,
                new MarkAndCommand(Mark.DEFAULT_NAME, validMarkName, anotherValidMarkName));

        // and with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_MARK + validMarkName + " " + andSubCommand
                        + " " + PREFIX_MARK + anotherValidMarkName
                        + " " + PREFIX_MARK + alsoAValidMarkName,
                new MarkAndCommand(validMarkName, anotherValidMarkName, alsoAValidMarkName));

        // and with m1, m2
        assertParseSuccess(parser, " " + PREFIX_MARK + validMarkName + " " + andSubCommand
                        + " " + PREFIX_MARK + anotherValidMarkName,
                new MarkAndCommand(validMarkName, anotherValidMarkName, Mark.DEFAULT_NAME));
    }

}
