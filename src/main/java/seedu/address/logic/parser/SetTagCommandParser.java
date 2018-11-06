package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetTagCommand;
import seedu.address.logic.commands.TagCommandMode;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mark.Mark;
import seedu.address.model.tag.Tag;

/**
 * Parser for SetTagCommand
 */
public class SetTagCommandParser implements Parser<SetTagCommand> {
    /**
     * Parses user input and returns a SetTagCommand
     * @param args user arguments
     * @return SetTagCommand
     * @throws ParseException
     */
    @Override
    public SetTagCommand parse(String args) throws ParseException {
        /*
        Usage: tag add|set|del index|m/mark [t/tagName...]
        */
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_MARK, PREFIX_TAG);
        Set<Tag> tags = argMultiMap.getAllValues(PREFIX_TAG).stream().map(Tag::new).collect(Collectors.toSet());
        TagCommandMode mode;

        String firstKeyword = args.split(" ")[1].trim();
        if (firstKeyword.equals("add")) {
            mode = TagCommandMode.ADD;
        } else if (firstKeyword.equals("set")) {
            mode = TagCommandMode.SET;
        } else if (firstKeyword.equals("del")) {
            mode = TagCommandMode.DEL;
        } else {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetTagCommand.MESSAGE_USAGE));
        }

        if (argMultiMap.getAllValues(PREFIX_MARK).size() == 1) {
            String markName = argMultiMap.getValue(PREFIX_MARK).orElse(Mark.DEFAULT_NAME);
            return new SetTagCommand(markName, tags, mode);
        } else {
            try {
                Index index = ParserUtil.parseIndex(argMultiMap.getPreamble().split(" ")[0]);
                return new SetTagCommand(index, tags, mode);
            } catch (NumberFormatException e) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        SetTagCommand.MESSAGE_USAGE));
            }
        }

    }
}
