package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCalendar;

/** Indicates the AddressBook in the model has changed*/
public class CalendarChangedEvent extends BaseEvent {

    public final ReadOnlyCalendar data;

    public CalendarChangedEvent(ReadOnlyCalendar data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getEventList().size();
    }
}

