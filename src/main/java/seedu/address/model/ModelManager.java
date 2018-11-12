package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.ui.ResetStudentViewEvent;
import seedu.address.logic.commands.group.GroupNotFoundException;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Student> filteredStudents;
    private final VersionedCalendar versionedCalendar;
    private final FilteredList<Event> filteredEvents;

    private final ObservableList<Group> groups;
    private Group watchedGroup;
    private boolean isWatchingGroup;



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
        groups = FXCollections.observableArrayList();

        versionedCalendar = new VersionedCalendar(calendar);
        filteredEvents = new FilteredList<>(versionedCalendar.getEventList());

        setGroup(Group.DEFAULT_NAME, Group.getEmpty());
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
        resetView();
        indicateAddressBookChanged();
    }

    @Override
    public void updateStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        versionedAddressBook.updateStudent(target, editedStudent);
        indicateAddressBookChanged();
        indicateStudentUpdated(target, editedStudent);
        if (!isWatchingGroup) {
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
        isWatchingGroup = false;
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
        removeExpiredGroups();
        indicateAddressBookChanged();
    }

    /**
     * Restores the model's address book to its previously undone state.
     */
    private void redoAddressBook() {
        versionedAddressBook.redo();
        removeExpiredGroups();
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
        // raise(new StudentChangedEvent(target, null));
        updateGroups(target, null);
    }

    /** Raises an event to indicate a student has been changed */
    private void indicateStudentUpdated(Student target, Student newStudent) {
        // raise(new StudentChangedEvent(target, newStudent));
        updateGroups(target, newStudent);
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
     * @param groupName name of group to get
     * @return group if found
     * @throws GroupNotFoundException if group not found in model
     */
    public Group getGroup(String groupName) throws GroupNotFoundException {
        Group.checkArguments(groupName);
        return groups.stream().filter(m -> m.getName().equals(groupName)).findFirst()
                .orElseThrow(() -> new GroupNotFoundException(Group.MESSAGE_GROUP_NOT_FOUND));
    }

    public void setGroup(String groupName, Group group) {
        try {
            Group old = getGroup(group.getName());
            groups.remove(old);
        } catch (GroupNotFoundException e) {
            logger.info("Setting groups, but not found");
        }
        group.setName(groupName);
        groups.add(group);
        if (isWatchingGroup && group.getName().equals(watchedGroup.getName())) {
            watchedGroup = group;
            refreshGroupPredicate();
        }
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return groups;
    }

    @Override
    public void setGroupPredicate(String groupName) throws GroupNotFoundException {
        // should only be called from GroupShowCommand
        watchedGroup = getGroup(groupName);
        refreshGroupPredicate();
    }

    @Override
    public void resetView() {
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);
        raise(new ResetStudentViewEvent(""));
    }

    /**
     * Updates the predicate when a student is edited
     */
    private void refreshGroupPredicate() {
        updateFilteredStudentList(watchedGroup.getPredicate());
        isWatchingGroup = true;
    }

    /**
     * updates the groups whenever a student is updated
     * @param oldStudent
     * @param newStudent
     */
    private void updateGroups(Student oldStudent, Student newStudent) {
        groups.forEach((group) -> {
            Set<Student> set = new HashSet<>(group.getSet());
            if (set.contains(oldStudent)) {
                set.remove(oldStudent);
                if (newStudent != null) {
                    set.add(newStudent);
                }
                Group newGroup = new Group(set, group.getName());
                setGroup(group.getName(), newGroup);
            }
        });
    }

    /**
     * Removes expired groups when undo or redo is executed
     */
    private void removeExpiredGroups() {
        groups.forEach((group) -> {
            Set<Student> set = group.getSet().stream().filter(student ->
                versionedAddressBook.getStudentList().contains(student)
            ).collect(Collectors.toSet());
            Group newGroup = new Group(set, group.getName());
            setGroup(group.getName(), newGroup);
        });
    }
}
