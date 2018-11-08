package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.student.Student;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Student> PREDICATE_MATCHING_NO_STUDENTS = unused -> false;
    private static final Predicate<Event> PREDICATE_MATCHING_NO_EVENTS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Student> studentsToDisplay, List<Event> eventsToDisplay) {
        Optional<Predicate<Student>> studentPredicate =
                studentsToDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredStudentList(studentPredicate.orElse(PREDICATE_MATCHING_NO_STUDENTS));

        Optional<Predicate<Event>> eventPredicate =
                eventsToDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredEventList(eventPredicate.orElse(PREDICATE_MATCHING_NO_EVENTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List, List)
     */
    public static void setFilteredList(Model model, Student[] studentsToDisplay, Event[] eventsToDisplay) {
        setFilteredList(model, Arrays.asList(studentsToDisplay), Arrays.asList(eventsToDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Student} equals to {@code other}.
     */
    private static Predicate<Student> getPredicateMatching(Student other) {
        return student -> student.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Event} equals to {@code other}.
     */
    private static Predicate<Event> getPredicateMatching(Event other) {
        return event -> event.equals(other);
    }
}
