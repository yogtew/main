package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents an event in the calendar.
 */
public class Event {

    // Comparator for events
    public static final Comparator<Event> COMPARATOR = (e1, e2) -> {
        try {
            // compare start times
            java.util.Date start1 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
                    .parse(e1.date.date + " " + e1.startTime.startTime);
            java.util.Date start2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
                    .parse(e2.date.date + " " + e2.startTime.startTime);

            // if start times are the same, compare end times
            java.util.Date end1 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
                    .parse(e1.date.date + " " + e1.endTime.endTime);
            java.util.Date end2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH)
                    .parse(e2.date.date + " " + e2.endTime.endTime);

            if (start1.compareTo(start2) != 0) {
                return start1.compareTo(start2);
            } else if (end1.compareTo(end2) != 0) {
                return end1.compareTo(end2);
            } else {
                return e1.eventName.eventName.compareTo(e2.eventName.eventName);
            }

        } catch (ParseException e) {
            return 0; // Ideally the parsers should be doing their job. If not then simply leave it be
        }
    };

    // Identity fields
    private final EventName eventName;
    private final Date date;
    private final StartTime startTime;
    private final EndTime endTime;

    // Data fields
    private final Optional<Description> description;

    /**
     * Constructs a (@code Event).
     *
     * @param eventName a valid event name.
     * @param date a valid date in dd-mm-yyyy format.
     * @param startTime a valid start time in 24 hour format.
     * @param endTime a valid end time in 24 hour format.
     * @param description valid details of the event.
     */
    public Event(EventName eventName, Date date, StartTime startTime,
                 EndTime endTime, Optional<Description> description) {
        requireAllNonNull(eventName, date, startTime, endTime, description);
        this.eventName = eventName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    /**
     * Constructs a (@code Event) without a description.
     *
     * @param eventName a valid event name.
     * @param date a valid date in dd-mm-yyyy format.
     * @param startTime a valid start time in 24 hour format.
     * @param endTime a valid end time in 24 hour format.
     */
    public Event(EventName eventName, Date date, StartTime startTime, EndTime endTime) {
        requireAllNonNull(eventName, date, startTime, endTime);
        this.eventName = eventName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = Optional.empty();
    }

    public EventName getEventName() {
        return eventName;
    }

    public Date getDate() {
        return date;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Optional<Description> getDescription() {
        return description;
    }

    /**
     * Returns true if both events have the same event name, date, start time, and end time.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getDate().equals(getDate())
                && otherEvent.getStartTime().equals(getStartTime())
                && otherEvent.getEndTime().equals(getEndTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(getEventName(), event.getEventName())
                && Objects.equals(getDate(), event.getDate())
                && Objects.equals(getStartTime(), event.getStartTime())
                && Objects.equals(getEndTime(), event.getEndTime())
                && Objects.equals(getDescription().orElse(null), event.getDescription().orElse(null));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventName(), getDate(), getStartTime(), getEndTime(), getDescription());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Date: ")
                .append(getDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime());
        description.ifPresent(desc ->
                builder.append(" Description: ")
                       .append(desc.description));
        return builder.toString();
    }

}
