package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.model.mark.Mark.DEFAULT_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.mark.MarkAndCommand;
import seedu.address.logic.commands.mark.MarkCommand;
import seedu.address.logic.commands.mark.MarkFindCommand;
import seedu.address.logic.commands.mark.MarkJoinCommand;
import seedu.address.logic.commands.mark.MarkShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Student;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {
    public static final String MESSAGE_INVALID_SUBCOMMAND = "Invalid subcommand: %s\n" +
            "Valid subcommands include: show, find, join, add";
    public static final String MESSAGE_INVALID_MARK_NAME = "Invalid mark name: %s\n%s";
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        /*
            Expected format: mark [alias1] <command> [alias2] [alias3]
            alias1 defaults to DEFAULT_NAME
            alias2, alias3 are required for certain commands

            command list:
            show - shows the marked Students in the GUI
            find - marks Students matching find
            join - union of two Marks
            and - intersection of two Marks
         */



        // arguments as a list of keywords
        ArrayList<String> splitArgs = new ArrayList<>(Arrays.asList(args.trim().split(" ")));
        String alias1 = DEFAULT_NAME;
        String alias2;
        String alias3 = DEFAULT_NAME;

        if (StringUtil.isPrefixedArg(splitArgs.get(0), PREFIX_MARK)) {
            alias1 = checkAlias(StringUtil.extractArgument(splitArgs.get(0), PREFIX_MARK));
            splitArgs.remove(0);
        }

        //<command>
        String subCommand = splitArgs.get(0);
        switch (subCommand) {
        case MarkCommand.SHOW:
            // show
            return new MarkShowCommand(alias1);
        case MarkCommand.FIND:
            // find
            String findArgs;
            splitArgs.remove(0);
            if (splitArgs.size() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
            }

            // because parser expects " t/tags" but not "t/tags"
            findArgs = " " + String.join(" ", splitArgs);
            FindCommand findCommand = new FindCommandParser().parse(findArgs);
            Predicate<Student> p = findCommand.getPredicate();
            return new MarkFindCommand(p, alias1);
        case MarkCommand.JOIN:
        case MarkCommand.AND:
            // mark [alias1] join alias2 [alias3]
            splitArgs.remove(0); // removes "join"

            // neither alias2 nor alias3 was provided -> invalid command format
            if (splitArgs.size() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkJoinCommand.MESSAGE_USAGE));
            }

            alias2 = checkAlias(StringUtil.extractArgument(splitArgs.get(0), PREFIX_MARK));

            // alias3 is optional, can default to the default mark
            if (splitArgs.size() == 2) {
                alias3 = checkAlias(StringUtil.extractArgument(splitArgs.get(1), PREFIX_MARK));
            }

            if (subCommand.equals(MarkCommand.JOIN)) {
                return new MarkJoinCommand(alias1, alias2, alias3);
            }
            return new MarkAndCommand(alias1, alias2, alias3);
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
        if (!Mark.isValidMarkName(name)) {
            throw new ParseException(String.format(MESSAGE_INVALID_MARK_NAME, name, Mark.MARK_NAME_CONSTRAINTS));
        }
        return name;
    }
}
