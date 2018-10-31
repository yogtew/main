package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.mark.Mark;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final HashMap<String, Mark> marks = new HashMap<>();

    private final VersionedCalendar versionedCalendar;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyCalendar calendar, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        versionedCalendar = new VersionedCalendar(calendar);
        filteredEvents = new FilteredList<>(versionedCalendar.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new Calendar(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
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
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
        indicatePersonDeleted(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePersonUpdated(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
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


    /** Raises an event to indicate a person has been changed */
    private void indicatePersonDeleted(Person target) {
        raise(new PersonChangedEvent(target, null));
    }

    /** Raises an event to indicate a person has been changed */
    private void indicatePersonUpdated(Person target, Person newPerson) {
        raise(new PersonChangedEvent(target, newPerson));
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

    @Override
    public void undoCalendar() {
        versionedCalendar.undo();
        indicateCalendarChanged();
    }

    @Override
    public void redoCalendar() {
        versionedCalendar.redo();
        indicateCalendarChanged();
    }

    @Override
    public void commitCalendar() {
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
                && filteredPersons.equals(other.filteredPersons);
    }

    public Mark getMark(String markName) {
        return marks.getOrDefault(markName, Mark.EMPTY);
    }

    public void setMark(String markName, Mark mark) {
        marks.put(markName, mark);
    }

    @Subscribe
    public void personChangedEventHandler(PersonChangedEvent event) {
        marks.forEach((name, mark) -> {
            Set<Person> set = new HashSet<>(mark.getSet());
            if (set.contains(event.oldPerson)) {
                set.remove(event.oldPerson);
                if (event.newPerson != null) {
                    set.add(event.newPerson);
                }
                setMark(name, new Mark(set, name));
            }
        });
    }
}
