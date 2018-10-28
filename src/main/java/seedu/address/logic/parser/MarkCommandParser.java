package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
import seedu.address.logic.commands.mark.MarkSubCommands;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class MarkCommandParser implements Parser<MarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        /*
            Expected format: mark [alias1] <command> [alias2] [alias3]
            alias1 defaults to DEFAULT_NAME
            alias2, alias3 are required for certain commands

            command list:
            show - shows the marked Persons in the GUI
            find - marks Persons matching find
            join - union of two Marks
            and - intersection of two Marks
         */
        ArrayList<String> splitArgs = new ArrayList<>(Arrays.asList(args.trim().split(" ")));
        String alias1, alias2, alias3;
        alias1 = alias3 = DEFAULT_NAME;

        // [alias1] todo: make this unaliased
        if (StringUtil.isPrefixedArg(splitArgs.get(0), PREFIX_ALIAS)) {
            alias1 = StringUtil.extractArgument(splitArgs.get(0), PREFIX_ALIAS);
            splitArgs.remove(0);
        }

        //<command>
        String subCommand = splitArgs.get(0);
        switch (subCommand) {
            case MarkSubCommands.SHOW:
                // show
                return new MarkShowCommand(alias1);
            case MarkSubCommands.FIND:
                // find
                String findArgs;
                int index = splitArgs.indexOf("f/");
                if (index != -1) {
                    findArgs = String.join(" ", splitArgs.subList(0, index));
                } else {
                    findArgs = String.join(" ", splitArgs);
                }
                FindCommand findCommand =  new FindCommandParser().parse(findArgs);
                Predicate<Person> p = findCommand.getPredicate();
                return new MarkFindCommand(p, alias1);
            case MarkSubCommands.JOIN:
                // mark [alias1] join [alias2] [alias3]
                splitArgs.remove(0); // removes "join"
                if (splitArgs.size() == 0) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
                }
                alias2 = splitArgs.get(0);
                if (splitArgs.size() == 2) {
                    alias3 = splitArgs.get(1);
                }
                return new MarkJoinCommand(alias1, alias2, alias3);
            case MarkSubCommands.AND:
                // mark [alias1] join [alias2] [alias3]
                splitArgs.remove(0); // removes "join"
                if (splitArgs.size() == 0) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
                }
                alias2 = splitArgs.get(0);
                if (splitArgs.size() == 2) {
                    alias3 = splitArgs.get(1);
                }
                return new MarkAndCommand(alias1, alias2, alias3);
            default:
                // invalid command
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }




}