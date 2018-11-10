package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.model.StudentChangedEvent;
import seedu.address.commons.events.ui.ResetStudentViewEvent;
import seedu.address.logic.commands.mark.MarkNotFoundException;
import seedu.address.model.event.Event;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    protected final VersionedAddressBook versionedAddressBook;
    protected final FilteredList<Student> filteredStudents;
    private final ObservableList<Mark> marks;
    private Mark watchedMark;
    private boolean isWatchingMark;

    protected final VersionedCalendar versionedCalendar;
    protected final FilteredList<Event> filteredEvents;


    // maintain an internal undo/redo stack to keep track of which model to undo/redo
    private final Stack<ModelType> undoStack = new Stack<>();
    private final Stack<ModelType> redoStack = new Stack<>();

    /**
     * Denotes the operations carried out on the model.
     */
    private enum ModelType {
        ADDRESSBOOK, CALENDAR, ALL
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyCalendar calendar, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredStudents = new FilteredList<>(versionedAddressBook.getStudentList());
        marks = FXCollections.observableArrayList();

        versionedCalendar = new VersionedCalendar(calendar);
        filteredEvents = new FilteredList<>(versionedCalendar.getEventList());

        setMark(Mark.DEFAULT_NAME, Mark.getEmpty());
    }

    public ModelManager() {
        this(new AddressBook(), new Calendar(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyCalendar newCalendar) {
        versionedAddressBook.resetData(newAddressBook);
        versionedCalendar.resetData(newCalendar);
        commitModel();
        indicateAddressBookChanged();
        indicateCalendarChanged();
    }

    @Override
    public void commitModel() {
        undoStack.push(ModelType.ALL);
        redoStack.clear();
        versionedAddressBook.commit();
        versionedCalendar.commit();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return versionedAddressBook.hasStudent(student);
    }

    @Override
    public void deleteStudent(Student target) {
        versionedAddressBook.removeStudent(target);
        indicateAddressBookChanged();
        indicateStudentDeleted(target);
    }

    @Override
    public void addStudent(Student student) {
        versionedAddressBook.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);
        indicateAddressBookChanged();
    }

    @Override
    public void updateStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        versionedAddressBook.updateStudent(target, editedStudent);
        indicateAddressBookChanged();
        indicateStudentUpdated(target, editedStudent);
        if (!isWatchingMark) {
            updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);
        }
    }

    //=========== Filtered Student List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return FXCollections.unmodifiableObservableList(filteredStudents);
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
        isWatchingMark = false;
    }

    //=========== Undo/Redo Address Book =====================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    /**
     * Restores the model's calendar to its previous state.
     */
    private void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    /**
     * Restores the model's address book to its previously undone state.
     */
    private void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        undoStack.push(ModelType.ADDRESSBOOK);
        redoStack.clear();
        versionedAddressBook.commit();
    }

    //=========== Event and Scheduling ======================================================================

    @Override
    public ReadOnlyCalendar getCalendar() {
        return versionedCalendar;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCalendarChanged() {
        raise(new CalendarChangedEvent(versionedCalendar));
    }


    /** Raises an event to indicate a student has been changed */
    private void indicateStudentDeleted(Student target) {
        raise(new StudentChangedEvent(target, null));
    }

    /** Raises an event to indicate a student has been changed */
    private void indicateStudentUpdated(Student target, Student newStudent) {
        // raise(new StudentChangedEvent(target, newStudent));
        updateMarks(target, newStudent);
    }


    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedCalendar.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event event) {
        versionedCalendar.removeEvent(event);
        indicateCalendarChanged();
    }

    @Override
    public void addEvent(Event event) {
        versionedCalendar.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateCalendarChanged();
    }

    //=========== Undo/Redo Calendar =======================================================================

    @Override
    public boolean canUndoCalendar() {
        return versionedCalendar.canUndo();
    }

    @Override
    public boolean canRedoCalendar() {
        return versionedCalendar.canRedo();
    }

    /**
     * Restores the model's calendar to its previous state.
     */
    private void undoCalendar() {
        versionedCalendar.undo();
        indicateCalendarChanged();
    }

    /**
     * Restores the model's calendar to its previously undone state.
     */
    private void redoCalendar() {
        versionedCalendar.redo();
        indicateCalendarChanged();
    }

    @Override
    public void commitCalendar() {
        undoStack.push(ModelType.CALENDAR);
        redoStack.clear();
        versionedCalendar.commit();
    }

    //=========== Filtered Event List Accessors ==============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedCalendar}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    //=========== Undo/Redo Controllers ==============================================================

    @Override
    public void undo() {
        switch (undoStack.pop()) {
        case ADDRESSBOOK:
            undoAddressBook();
            redoStack.push(ModelType.ADDRESSBOOK);
            break;
        case CALENDAR:
            undoCalendar();
            redoStack.push(ModelType.CALENDAR);
            break;
        case ALL:
            undoAddressBook();
            undoCalendar();
            redoStack.push(ModelType.ALL);
            break;
        default:
            break;
        }
    }

    @Override
    public void redo() {
        switch (redoStack.pop()) {
        case ADDRESSBOOK:
            redoAddressBook();
            undoStack.push(ModelType.ADDRESSBOOK);
            break;
        case CALENDAR:
            redoCalendar();
            undoStack.push(ModelType.CALENDAR);
            break;
        case ALL:
            redoAddressBook();
            redoCalendar();
            undoStack.push(ModelType.ALL);
            break;
        default:
            break;
        }
    }

    @Override
    public boolean canUndo() {
        return !undoStack.empty();
    }

    @Override
    public boolean canRedo() {
        return !redoStack.empty();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredStudents.equals(other.filteredStudents)
                && versionedCalendar.equals(other.versionedCalendar)
                && filteredEvents.equals(other.filteredEvents);
    }

    /**
     *
     * @param markName name of mark to get
     * @return mark if found
     * @throws MarkNotFoundException if mark not found in model
     */
    public Mark getMark(String markName) throws MarkNotFoundException {
        Mark.checkValidMarkName(markName);
        return marks.stream().filter(m -> m.getName().equals(markName)).findFirst()
                .orElseThrow(() -> new MarkNotFoundException(Mark.MESSAGE_MARK_NOT_FOUND));
    }

    public void setMark(String markName, Mark mark) {
        try {
            Mark old = getMark(mark.getName());
            marks.remove(old);
        } catch (MarkNotFoundException ignored) {

        }
        mark.setName(markName);
        marks.add(mark);
        if (isWatchingMark && mark.getName().equals(watchedMark.getName())) {
            watchedMark = mark;
            refreshMarkPredicate();
        }
    }

    @Override
    public ObservableList<Mark> getFilteredMarkList() {
        return marks;
    }

    @Override
    public void setMarkPredicate(String markName) throws MarkNotFoundException {
        // should only be called from MarkShowCommand
        watchedMark = getMark(markName);
        refreshMarkPredicate();
    }

    @Override
    public void resetView() {
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);
        raise(new ResetStudentViewEvent(""));
    }

    /**
     * Updates the predicate when a student is edited
     */
    private void refreshMarkPredicate() {
        updateFilteredStudentList(watchedMark.getPredicate());
        isWatchingMark = true;
    }

    /**
     * updates the marks whenever a student is updated
     * @param oldStudent
     * @param newStudent
     */
    public void updateMarks(Student oldStudent, Student newStudent) {
        marks.forEach((mark) -> {
            Set<Student> set = new HashSet<>(mark.getSet());
            if (set.contains(oldStudent)) {
                set.remove(oldStudent);
                if (newStudent != null) {
                    set.add(newStudent);
                }
                Mark newMark = new Mark(set, mark.getName());
                setMark(mark.getName(), newMark);
            }
        });
    }
}
