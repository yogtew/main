package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;

/**
 * Wraps all data at the calendar level
 * Duplicates are not allowed (by .isSameEvent comparison)
 */
public class Calendar implements ReadOnlyCalendar {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public Calendar() {}

    /**
     * Creates a Calendar using the events in the {@code toBeCopied}
     */
    public Calendar(ReadOnlyCalendar toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code Calendar} with {@code newData}.
     */
    public void resetData(ReadOnlyCalendar newData) {
        requireNonNull(newData);

        setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if an event identical to {@code event} exists in the calendar.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the calendar.
     * The event must not already exist in the calendar.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Removes {@code key} from this {@code Calendar}.
     * {@code key} must exist in the calendar.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Calendar // instanceof handles nulls
                && events.equals(((Calendar) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
