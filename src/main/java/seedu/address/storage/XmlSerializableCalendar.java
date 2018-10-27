package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Event;

/**
 * An Immutable Calendar that is serializable to XML format
 */
@XmlRootElement(name = "calendar")
public class XmlSerializableCalendar {

    public static final String MESSAGE_DUPLICATE_EVENT = "Events list contains duplicate event(s).";

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableCalendar.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCalendar() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCalendar(ReadOnlyCalendar src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this calendar into the model's {@code Calendar} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedEvent}.
     */
    public Calendar toModelType() throws IllegalValueException {
        Calendar addressBook = new Calendar();
        for (XmlAdaptedEvent e : events) {
            Event event = e.toModelType();
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            addressBook.addEvent(event);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCalendar)) {
            return false;
        }
        return events.equals(((XmlSerializableCalendar) other).events);
    }
}
