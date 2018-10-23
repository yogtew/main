package seedu.address.testutil;

import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "CS2103 Tutorial";
    public static final String DEFAULT_DATE = "24-10-2018";
    public static final String DEFAULT_START_TIME = "13:00";
    public static final String DEFAULT_END_TIME = "14:00";
    public static final String DEFAULT_DESCRIPTION = "Mid v1.3";

    private EventName eventName;
    private Date date;
    private StartTime startTime;
    private EndTime endTime;
    private Description description;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        date = new Date(DEFAULT_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        date = eventToCopy.getDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
        description = eventToCopy.getDescription();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Event} that we are building
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Event} that we are building
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Event build() {
        return new Event(eventName, date, startTime, endTime, description);
    }

}
