package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommandMode;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

/**
 * Parser for TagCommand
 */
public class TagCommandParser implements Parser<TagCommand> {
    /**
     * Parses user input and returns a TagCommand
     * @param args user arguments
     * @return TagCommand
     * @throws ParseException
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        /*
        Usage: tag add|set|del index|g/group [t/tagName...]
        */
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_TAG);
        Set<Tag> tags;
        try {
            tags = argMultiMap.getAllValues(PREFIX_TAG).stream().map(Tag::new).collect(Collectors.toSet());
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        TagCommandMode mode;

        String[] splitArgs = argMultiMap.getPreamble().split(" ");
        if (splitArgs.length == 0 || splitArgs.length > 2) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        String firstKeyword = splitArgs[0].trim();
        if (firstKeyword.equals("add")) {
            mode = TagCommandMode.ADD;
        } else if (firstKeyword.equals("set")) {
            mode = TagCommandMode.SET;
        } else if (firstKeyword.equals("del")) {
            mode = TagCommandMode.DEL;
        } else {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        if (argMultiMap.getAllValues(PREFIX_GROUP).size() == 1) {
            if (splitArgs.length == 2) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        TagCommand.MESSAGE_USAGE));
            }
            String groupName = checkAlias(argMultiMap.getValue(PREFIX_GROUP).orElse(Group.DEFAULT_NAME));
            return new TagCommand(groupName, tags, mode);
        } else if (splitArgs.length == 1) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        } else {
            try {
                Index index = ParserUtil.parseIndex(splitArgs[1]);
                return new TagCommand(index, tags, mode);
            } catch (ParseException e) {
                throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        TagCommand.MESSAGE_USAGE));
            }
        }

    }

    /**
     * checks if the group name is valid otherwise throws an exception
     * @param name
     * @return
     * @throws ParseException
     */
    private String checkAlias(String name) throws ParseException {
        if (!Group.isValidGroupName(name)) {
            throw new ParseException(Group.GROUP_NAME_CONSTRAINTS);
        }
        return name;
    }
}
