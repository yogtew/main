package seedu.address.logic.commands;

import seedu.address.model.mark.Mark;

public interface IMarkExecutable {
    public CommandResult executeMark(Mark m);
}
