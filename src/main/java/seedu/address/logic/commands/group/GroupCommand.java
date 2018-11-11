package seedu.address.logic.commands.group;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import seedu.address.logic.commands.Command;

/**
 * Groups Students based on supplied Predicates
 */
public abstract class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String SHOW = "show";
    public static final String FIND = "find";
    public static final String JOIN = "join";
    public static final String AND = "and";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a Group.\n"
            + "Parameters: "
            + "[" + PREFIX_GROUP + "GROUP1] "
            + SHOW + "|" + FIND + "|" + JOIN + "|" + AND
            + " " + PREFIX_GROUP + "GROUP2 "
            + "[" + PREFIX_GROUP + "GROUP3]\n"
            + "Example: group "
            + PREFIX_GROUP + "group1 "
            + JOIN + " "
            + PREFIX_GROUP + "group2 "
            + PREFIX_GROUP + "group3\n"
            + "Note: different subcommands might require different arguments";

    protected String alias1;
    protected String alias2;
    protected String alias3;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand); // instanceof handles nulls
    }
}
