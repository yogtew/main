package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.mark.IMarkExecutable;
import seedu.address.model.Model;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Command that adds tags to marked Students
 */
public class SetTagCommand extends Command implements IMarkExecutable {

    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " add|set|del index|mark tags...";
    private final Set<Tag> tags;
    private String markName = "";
    private Index index = Index.fromZeroBased(0);
    private boolean useMark = false;
    private TagCommandMode mode;


    public SetTagCommand(String markName, Set<Tag> tags, TagCommandMode mode) {
        this.markName = markName;
        this.tags = tags;
        this.mode = mode;
        useMark = true;
    }

    public SetTagCommand(Index index, Set<Tag> tags, TagCommandMode mode) {
        this.index = index;
        this.tags = tags;
        this.mode = mode;
        useMark = false;
    }

    private void processStudent(Model model, CommandHistory history, Student target) {
        Set<Tag> updatedTags = new HashSet<>(target.getTags());
        switch(mode) {
        case ADD:
            updatedTags.addAll(tags);
            break;
        case DEL:
            updatedTags.removeAll(tags);
            break;
        case SET:
            updatedTags = new HashSet<>(tags);
            break;
        default:
            break;
        }

        Student newStudent = new Student(target.getName(), target.getStudentNumber(),
                target.getEmail(), target.getFaculty(), updatedTags);
        model.updateStudent(target, newStudent);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (useMark) {
            return executeMark(model, history);
        }

        List<Student> lastShownList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(index.getZeroBased());

        processStudent(model, history, studentToEdit);
        return formatCommandResult(1);
    }

    @Override
    public CommandResult executeMark(Model model, CommandHistory history) {
        Mark m = model.getMark(markName);
        m.getList().forEach(p -> {
                processStudent(model, history, p);
        });
        model.commitAddressBook();
        return formatCommandResult(m.count());
    }

    private CommandResult formatCommandResult(int n) {
        String verb;
        switch(mode) {
        case ADD:
            verb = "added tags to";
            break;
        case DEL:
            verb = "deleted tags from";
            break;
        case SET:
            verb = "updated tags of";
            break;
        default:
            verb = "edited";
            break;
        }

        String pluralName = n == 1 ? "student":"students";

        return new CommandResult(String.format("Successfully %s %d %s", verb, n, pluralName));
    }
}