package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.GraphCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class GraphCommandParser implements Parser<GraphCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GraphCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // if no PREFIXES used -> search by name (default usage)
        if (argumentMultimap.countNonEmptyArgs() == 0) {
            String[] nameKeywords = trimmedArgs.split("\\s+");
            return new GraphCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        if (argumentMultimap.getAllValues(PREFIX_TAG).size() == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GraphCommand.MESSAGE_USAGE));
        }

        // tag prefixes found
        try {
            List<Tag> tags = argumentMultimap.getAllValues(PREFIX_TAG)
                    .stream().map(Tag::new).collect(Collectors.toList());
            return new GraphCommand(new IsTaggedPredicate(tags));
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
