package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.model.group.Group.DEFAULT_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.group.GroupAndCommand;
import seedu.address.logic.commands.group.GroupCommand;
import seedu.address.logic.commands.group.GroupFindCommand;
import seedu.address.logic.commands.group.GroupJoinCommand;
import seedu.address.logic.commands.group.GroupShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {
    public static final String MESSAGE_INVALID_SUBCOMMAND = "Invalid subcommand: %s\n"
            + "Valid subcommands include: show, find, join, add";
    public static final String MESSAGE_INVALID_GROUP_NAME = "Invalid group name: %s\n%s";
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        /*
            Expected format: group [alias1] <command> [alias2] [alias3]
            alias1 defaults to DEFAULT_NAME
            alias2, alias3 are required for certain commands

            command list:
            show - shows the students in that group in the GUI
            find - adds the matched students to a group
            join - union of two groups
            and - intersection of two groups
         */



        // arguments as a list of keywords
        ArrayList<String> splitArgs = new ArrayList<>(Arrays.asList(args.trim().split(" ")));
        String alias1 = DEFAULT_NAME;
        String alias2;
        String alias3 = DEFAULT_NAME;

        if (StringUtil.isPrefixedArg(splitArgs.get(0), PREFIX_GROUP)) {
            alias1 = checkAlias(StringUtil.extractArgument(splitArgs.get(0), PREFIX_GROUP));
            splitArgs.remove(0);
        }

        //<command>
        String subCommand = splitArgs.get(0);
        switch (subCommand) {
        case GroupCommand.SHOW:
            // show
            return new GroupShowCommand(alias1);
        case GroupCommand.FIND:
            // find
            String findArgs;
            splitArgs.remove(0);
            if (splitArgs.size() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
            }

            // because parser expects " t/tags" but not "t/tags"
            findArgs = " " + String.join(" ", splitArgs);
            FindCommand findCommand = new FindCommandParser().parse(findArgs);
            Predicate<Student> p = findCommand.getPredicate();
            return new GroupFindCommand(p, alias1);
        case GroupCommand.JOIN:
        case GroupCommand.AND:
            // group [alias1] join alias2 [alias3]
            splitArgs.remove(0); // removes "join"

            // neither alias2 nor alias3 was provided -> invalid command format
            if (splitArgs.size() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupJoinCommand.MESSAGE_USAGE));
            }

            alias2 = checkAlias(StringUtil.extractArgument(splitArgs.get(0), PREFIX_GROUP));

            // alias3 is optional, can default to the default group
            if (splitArgs.size() == 2) {
                alias3 = checkAlias(StringUtil.extractArgument(splitArgs.get(1), PREFIX_GROUP));
            }

            if (subCommand.equals(GroupCommand.JOIN)) {
                return new GroupJoinCommand(alias1, alias2, alias3);
            }
            return new GroupAndCommand(alias1, alias2, alias3);
        default:
            // invalid command
            throw new ParseException(String.format(MESSAGE_INVALID_SUBCOMMAND, subCommand));
        }
    }

    /**
     * checks alias and throws an exception if its not valid
     * @param name
     * @return
     * @throws ParseException
     */
    private String checkAlias(String name) throws ParseException {
        if (!Group.isValidGroupName(name)) {
            throw new ParseException(String.format(MESSAGE_INVALID_GROUP_NAME, name, Group.GROUP_NAME_CONSTRAINTS));
        }
        return name;
    }
}
