package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowGraphRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;

/**
 * Finds and lists all students in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class GraphCommand extends Command {

    public static final String COMMAND_WORD = "graph";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display a graph all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Predicate<Student> predicate;

    public GraphCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    public GraphCommand(IsTaggedPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);

        EventsCenter.getInstance().post(new ShowGraphRequestEvent(getAttendanceStats(model.getFilteredStudentList())));
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    public Predicate<Student> getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GraphCommand // instanceof handles nulls
                && predicate.equals(((GraphCommand) other).predicate)); // state check
    }

    private ArrayList<Integer> getAttendanceStats(ObservableList<Student> studentList){
        ArrayList<Integer> attendanceStats = new ArrayList<Integer>();
        Integer present = 0;
        Integer absent = 0;
        Integer undef = 0;
        for (Student s : studentList){
            String attendance = s.getAttendance().toString().toLowerCase();
            switch(attendance){
                case "present":
                    present++;
                    break;
                case "absent":
                    absent++;
                    break;
                case "undefined":
                    undef++;
                    break;
            }
        }
        attendanceStats.add(present);
        attendanceStats.add(absent);
        attendanceStats.add(undef);
        return attendanceStats;
    }
}
