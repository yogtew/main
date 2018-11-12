package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.group.GroupNotFoundException;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENT = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyCalendar newCalendar);

    /** Commits the entire model. */
    void commitModel();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be
     * the same as another existing student in the address book.
     */
    void updateStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    Group getGroup(String groupName) throws GroupNotFoundException;

    void setGroup(String groupName, Group group);

    ObservableList<Group> getFilteredGroupList();

    void setGroupPredicate(String groupName) throws GroupNotFoundException;

    void resetView ();

    /** Returns the Calendar */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a event with the same identity as {@code event} exists in the Event.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event target);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous calendar states to restore.
     */
    boolean canUndoCalendar();

    /**
     * Returns true if the model has undone calendar states to restore.
     */
    boolean canRedoCalendar();

    /**
     * Controls undoing of the calendar and address book.
     */
    void undo();

    /**
     * Controls redoing of the calendar and address book.
     */
    void redo();

    /**
     * Returns true if the model has an previous state to restore
     */
    boolean canUndo();

    /**
     * Returns true if the model has an undone state to restore.
     */
    boolean canRedo();

    /**
     * Saves the current calendar state for undo/redo.
     */
    void commitCalendar();
}
