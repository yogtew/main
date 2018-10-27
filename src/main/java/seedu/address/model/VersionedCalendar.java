package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * (@code Calendar) that keeps track of its own history
 */
public class VersionedCalendar extends Calendar {

    private final List<ReadOnlyCalendar> calendarStateList;
    private int currentStatePointer;

    public VersionedCalendar(ReadOnlyCalendar initialState) {
        super(initialState);

        calendarStateList = new ArrayList<>();
        calendarStateList.add(new Calendar(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code Calendar} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        calendarStateList.add(new Calendar(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        calendarStateList.subList(currentStatePointer + 1, calendarStateList.size()).clear();
    }

    /**
     * Restores the calendar to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(calendarStateList.get(currentStatePointer));
    }

    /**
     * Restores the calendar to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(calendarStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has calendar states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has calendar states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < calendarStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedCalendar otherVersionedCalendar = (VersionedCalendar) other;

        // state check
        return super.equals(otherVersionedCalendar)
                && calendarStateList.equals(otherVersionedCalendar.calendarStateList)
                && currentStatePointer == otherVersionedCalendar.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of calendarStateList, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of calendarStateList, unable to redo.");
        }
    }
}
