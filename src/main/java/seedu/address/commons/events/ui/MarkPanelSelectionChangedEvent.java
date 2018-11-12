package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.mark.Mark;

/**
 * Represents a selection change in the Student List Panel
 */
public class MarkPanelSelectionChangedEvent extends BaseEvent {


    private final Mark newSelection;

    public MarkPanelSelectionChangedEvent(Mark newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Mark getNewSelection() {
        return newSelection;
    }
}
