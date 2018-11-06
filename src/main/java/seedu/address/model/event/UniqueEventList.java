package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;

/**
 * A sorted list of events that enforces uniqueness between its elements and does not allow nulls.
 * The list uses the {@code Event#COMPARATOR} to compare events based on value, start time, end time, and name,
 * in that order.
 *
 * The order of the backing list is enforced by the methods themselves, with the use of
 * {@code binarySearch(List<? extends T> list, T key, Comparator<? super T> c)} supporting the operations.
 *
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 * @see Event#COMPARATOR
 */
public class UniqueEventList implements Iterable<Event> {

    private final ObservableList<Event> internalSortedList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return (Collections.binarySearch(internalSortedList, toCheck, Event.COMPARATOR) >= 0);
    }

    /**
     * Adds an event to the list in its sorted position.
     * The event must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        int index = Collections.binarySearch(internalSortedList, toAdd, Event.COMPARATOR);
        if (index >= 0) {

            throw new DuplicateEventException();
        } else {
            internalSortedList.add(-index - 1, toAdd);
        }
    }

    /**
     * Removes the equivalent student from the list.
     * The student must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);

        int index = Collections.binarySearch(internalSortedList, toRemove, Event.COMPARATOR);
        if (index < 0) {
            throw new EventNotFoundException();
        } else {
            internalSortedList.remove(index);
        }
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalSortedList.setAll(replacement.internalSortedList);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     * (@code events) must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalSortedList.setAll(events);
        internalSortedList.sort(Event.COMPARATOR);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalSortedList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalSortedList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalSortedList.equals(((UniqueEventList) other).internalSortedList));
    }

    @Override
    public int hashCode() {
        return internalSortedList.hashCode();
    }

    /**
     * Returns true if {@code events} contains only unique students.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
