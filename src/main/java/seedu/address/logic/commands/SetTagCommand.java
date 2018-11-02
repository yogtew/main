package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static final String MESSAGE_USAGE = COMMAND_WORD + "[index|mark] tags...";
    private final Set<Tag> tags;
    private String markName = "";
    private Index index = Index.fromZeroBased(0);
    private boolean useMark = false;

    public SetTagCommand(String markName, Set<Tag> tags) {
        this.markName = markName;
        this.tags = tags;
        useMark = true;
    }

    public SetTagCommand(Index index, Set<Tag> tags) {
        this.index = index;
        this.tags = tags;
        useMark = false;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (useMark) {
            return executeMark(model, history);
        }
        EditCommand.EditStudentDescriptor descriptor = new EditCommand.EditStudentDescriptor();
        descriptor.setTags(tags);
        return new EditCommand(index, descriptor).execute(model, history);
    }

    @Override
    public CommandResult executeMark(Model model, CommandHistory history) {
        Mark m = model.getMark(markName);
        List<Student> updatedList = m.getList().stream().map(p -> {
            Set<Tag> updatedTags = new HashSet<>(p.getTags());
            updatedTags.addAll(tags);
            Student newStudent = new Student(p.getName(), p.getStudentNumber(), p.getEmail(), p.getFaculty(), updatedTags);
            model.updateStudent(p, newStudent);
            return newStudent;
        }).collect(Collectors.toList());
        model.setMark(m.getName(), new Mark(updatedList, m.getName()));
        model.commitAddressBook();
        return new CommandResult(String.format("Successfully tagged %d people", updatedList.size()));
    }
}
