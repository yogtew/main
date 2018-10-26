package seedu.address.testutil;

import seedu.address.model.Calendar;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Calendar objects.
 * Example usage: <br>
 *     {@code Calendar cal = new CalendarBuilder().withPerson("Consultation", "Tutorial").build();}
 */
public class CalendarBuilder {

    private Calendar calendar;

    public CalendarBuilder() {
        calendar = new Calendar();
    }

    public CalendarBuilder(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Adds a new {@code Event} to the {@code Calendar} that we are building.
     */
    public CalendarBuilder withEvent(Event event) {
        calendar.addEvent(event);
        return this;
    }

    public Calendar build() {
        return calendar;
    }
}
