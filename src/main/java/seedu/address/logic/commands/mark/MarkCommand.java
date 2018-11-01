package seedu.address.logic.commands.mark;

import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;

import seedu.address.logic.commands.Command;

/**
 * Marks Persons based on supplied Predicates
 */
public abstract class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String SHOW = "show";
    public static final String FIND = "find";
    public static final String JOIN = "join";
    public static final String AND = "and";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a Mark.\n"
            + "Parameters: "
            + "[" + PREFIX_MARK + "MARK1] "
            + "[" + SHOW + "|" + FIND + "|" + JOIN + "|" + AND + "] "
            + "[" + PREFIX_MARK + "MARK2] "
            + "[" + PREFIX_MARK + "MARK3]\n"
            + "Example: mark "
            + PREFIX_MARK + "mark1 "
            + JOIN + " "
            + PREFIX_MARK + "mark2 "
            + PREFIX_MARK + "mark3";

    protected String alias1;
    protected String alias2;
    protected String alias3;

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand); // instanceof handles nulls
    }
}
