package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_TUTORIAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Calendar;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final Event BIRTHDAY = new EventBuilder().withEventName("Birthday")
            .withDate("12-12-2018").withStartTime("12:00").withEndTime("15:00")
            .withDescription("Bring candles").build();
    public static final Event INTERVIEW = new EventBuilder().withEventName("Interview")
            .withDate("15-10-2018").withStartTime("15:00").withEndTime("15:30")
            .withDescription("At Google HQ").build();
    public static final Event EXAM = new EventBuilder().withEventName("Exam")
            .withDate("12-12-2018").withStartTime("14:00").withEndTime("16:00")
            .withDescription("MPSH1").build();
    public static final Event MEETING = new EventBuilder().withEventName("Meeting")
            .withDate("11-11-2018").withStartTime("08:00").withEndTime("12:30")
            .withDescription("At COM1").build();

    // Events not present in the Calendar
    public static final Event EVENT_NOT_PRESENT = new EventBuilder().withEventName("EventNotPresent")
            .withDate("01-01-2018").withStartTime("00:00").withEndTime("23:59")
            .withDescription("nil").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event CONSULTATION = new EventBuilder()
            .withEventName(VALID_EVENT_NAME_CONSULTATION)
            .withDate(VALID_DATE_CONSULTATION)
            .withStartTime(VALID_START_TIME_CONSULTATION)
            .withEndTime(VALID_END_TIME_CONSULTATION)
            .withDescription(VALID_DESCRIPTION_CONSULTATION)
            .build();
    public static final Event TUTORIAL = new EventBuilder()
            .withEventName(VALID_EVENT_NAME_TUTORIAL)
            .withDate(VALID_DATE_TUTORIAL)
            .withStartTime(VALID_START_TIME_TUTORIAL)
            .withEndTime(VALID_END_TIME_TUTORIAL)
            .withDescription(VALID_DESCRIPTION_TUTORIAL)
            .build();
    public static final Event TUTORIAL_NO_DESCRIPTION = new EventBuilder()
            .withEventName(VALID_EVENT_NAME_TUTORIAL)
            .withDate(VALID_DATE_TUTORIAL)
            .withStartTime(VALID_START_TIME_TUTORIAL)
            .withEndTime(VALID_END_TIME_TUTORIAL)
            .build();
    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns a {@code Calendar} with all the typical events.
     */
    public static Calendar getTypicalCalendar() {
        Calendar cal = new Calendar();
        for (Event event : getTypicalEvents()) {
            cal.addEvent(event);
        }
        return cal;
    }

    public static List<Event> getTypicalEvents() {
        ArrayList<Event> list = new ArrayList<>(Arrays.asList(BIRTHDAY, MEETING, INTERVIEW, EXAM));
        list.sort(Event.COMPARATOR);
        return list;
    }
}
