package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.GroupCommandParser.MESSAGE_INVALID_GROUP_NAME;
import static seedu.address.logic.parser.GroupCommandParser.MESSAGE_INVALID_SUBCOMMAND;

import org.junit.Test;

import seedu.address.logic.commands.group.GroupAndCommand;
import seedu.address.logic.commands.group.GroupCommand;
import seedu.address.logic.commands.group.GroupFindCommand;
import seedu.address.logic.commands.group.GroupJoinCommand;
import seedu.address.logic.commands.group.GroupShowCommand;
import seedu.address.model.group.Group;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.tag.Tag;

public class GroupCommandParserTest {
    private GroupCommandParser parser = new GroupCommandParser();
    private String showSubCommand = "show";
    private String findSubCommand = "find";
    private String joinSubCommand = "join";
    private String andSubCommand = "and";
    private String validGroupName = "validGroupName";
    private String anotherValidGroupName = "anotherValidGroupName";
    private String alsoAValidGroupName = "alsoAValidGroupName";
    private String invalidGroupName = "invalidGroupName!";
    private String validTagName = "validTagName";
    private String invalidTagName = "invalidTagName!";

    @Test
    public void empty() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupJoinCommand.MESSAGE_USAGE));
    }

    @Test
    public void find() {
        // show without any arguments -> fail
        assertParseFailure(parser, findSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));

        // find with a valid tag
        assertParseSuccess(parser, findSubCommand + " t/" + validTagName,
                new GroupFindCommand(new IsTaggedPredicate(validTagName), Group.DEFAULT_NAME));

        // find with a invalid tag
        assertParseFailure(parser, findSubCommand + " t/" + invalidTagName,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // find with a valid tag and valid group
        assertParseSuccess(parser, PREFIX_GROUP + validGroupName + " " + findSubCommand + " t/" + validTagName,
                new GroupFindCommand(new IsTaggedPredicate(validTagName), validGroupName));
    }

    @Test
    public void show() {
        // find without any arguments
        assertParseSuccess(parser, showSubCommand, new GroupShowCommand(Group.DEFAULT_NAME));

        // show with an alias
        assertParseSuccess(parser, PREFIX_GROUP + validGroupName + " "
                + showSubCommand, new GroupShowCommand(validGroupName));

        // show with an invalid alias
        assertParseFailure(parser, PREFIX_GROUP + invalidGroupName + " " + showSubCommand,

                String.format(MESSAGE_INVALID_GROUP_NAME, invalidGroupName, Group.GROUP_NAME_CONSTRAINTS));
    }

    @Test
    public void join() {
        // group [m1] join m2 [m3]
        // no arguments - fail
        assertParseFailure(parser, joinSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupJoinCommand.MESSAGE_USAGE));

        // m1 - fail
        assertParseFailure(parser, " " + PREFIX_GROUP + validGroupName + " " + joinSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupJoinCommand.MESSAGE_USAGE));

        // join with m2
        assertParseSuccess(parser, joinSubCommand + " " + PREFIX_GROUP + validGroupName,
                new GroupJoinCommand(Group.DEFAULT_NAME, validGroupName, Group.DEFAULT_NAME));

        // join with m2, m3
        assertParseSuccess(parser, joinSubCommand + " " + PREFIX_GROUP
                        + validGroupName + " " + PREFIX_GROUP + anotherValidGroupName,
                new GroupJoinCommand(Group.DEFAULT_NAME, validGroupName, anotherValidGroupName));

        // join with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_GROUP + validGroupName + " " + joinSubCommand
                        + " " + PREFIX_GROUP + anotherValidGroupName
                        + " " + PREFIX_GROUP + alsoAValidGroupName,
                new GroupJoinCommand(validGroupName, anotherValidGroupName, alsoAValidGroupName));

        // join with m1, m2
        // join with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_GROUP + validGroupName + " " + joinSubCommand
                        + " " + PREFIX_GROUP + anotherValidGroupName,
                new GroupJoinCommand(validGroupName, anotherValidGroupName, Group.DEFAULT_NAME));
    }

    @Test
    public void and() {
        // group [m1] and m2 [m3]

        // no arguments - fail
        assertParseFailure(parser, andSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAndCommand.MESSAGE_USAGE));

        // m1 - fail
        assertParseFailure(parser, " " + PREFIX_GROUP + validGroupName + " " + andSubCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupAndCommand.MESSAGE_USAGE));

        // and with m2
        assertParseSuccess(parser, andSubCommand + " " + PREFIX_GROUP + validGroupName,
                new GroupAndCommand(Group.DEFAULT_NAME, validGroupName, Group.DEFAULT_NAME));

        // and with m2, m3
        assertParseSuccess(parser, andSubCommand + " " + PREFIX_GROUP
                        + validGroupName + " " + PREFIX_GROUP + anotherValidGroupName,
                new GroupAndCommand(Group.DEFAULT_NAME, validGroupName, anotherValidGroupName));

        // and with m1, m2, m3
        assertParseSuccess(parser, " " + PREFIX_GROUP + validGroupName + " " + andSubCommand
                        + " " + PREFIX_GROUP + anotherValidGroupName
                        + " " + PREFIX_GROUP + alsoAValidGroupName,
                new GroupAndCommand(validGroupName, anotherValidGroupName, alsoAValidGroupName));

        // and with m1, m2
        assertParseSuccess(parser, " " + PREFIX_GROUP + validGroupName + " " + andSubCommand
                        + " " + PREFIX_GROUP + anotherValidGroupName,
                new GroupAndCommand(validGroupName, anotherValidGroupName, Group.DEFAULT_NAME));
    }

}
