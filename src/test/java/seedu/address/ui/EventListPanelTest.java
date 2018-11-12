package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.event.Date;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;
import seedu.address.storage.XmlSerializableCalendar;

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<Event> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private EventListPanelHandle eventListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_EVENTS);

        /* Verified the initial selected card to be the nearest upcoming event */
        Event expectedInitialSelected = getUpcomingEvent(TYPICAL_EVENTS);
        assertCardDisplaysEvent(expectedInitialSelected, eventListPanelHandle.getHandleToSelectedCard());

        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            Event expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
        }
    }

    /**
     * Verifies that creating and deleting large number of events in {@code EventListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Event> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of event cards exceeded time limit");
    }

    /**
     * Returns a list of events containing {@code eventCount} events that is used to populate the
     * {@code EventListPanel}.
     */
    private ObservableList<Event> createBackingList(int eventCount) throws Exception {
        Path xmlFile = createXmlFileWithEvents(eventCount);
        XmlSerializableCalendar xmlCalendar =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableCalendar.class);
        return FXCollections.observableArrayList(xmlCalendar.toModelType().getEventList());
    }

    /**
     * Returns a .xml file containing {@code eventCount} events. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithEvents(int eventCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<calendar>\n");
        for (int i = 0; i < eventCount; i++) {
            builder.append("<events>\n");
            builder.append("<eventName>").append(i).append("a</eventName>\n");
            builder.append("<date>01-01-2000</date>\n");
            builder.append("<startTime>00:00</startTime>\n");
            builder.append("<endTime>23:59</endTime>\n");
            builder.append("<description>nil</description>\n");
            builder.append("</events>\n");
        }
        builder.append("</calendar>\n");

        Path manyEventsFile = Paths.get(TEST_DATA_FOLDER + "manyEvents.xml");
        FileUtil.createFile(manyEventsFile);
        FileUtil.writeToFile(manyEventsFile, builder.toString());
        manyEventsFile.toFile().deleteOnExit();
        return manyEventsFile;
    }

    /**
     * Initializes {@code eventListPanelHandle} with a {@code EventListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code EventListPanel}.
     */
    private void initUi(ObservableList<Event> backingList) {
        EventListPanel eventListPanel = new EventListPanel(backingList);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }

    /**
     * Gets the nearest upcoming event from the current date and time in the event list and returns it.
     */
    private Event getUpcomingEvent(ObservableList<Event> eventList) {
        LocalDateTime time = LocalDateTime.now();
        String date = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String startTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        int index = 0;
        // Creating a dummy event to compare to
        Event event = new Event(new EventName("a"), new Date(date), new StartTime(startTime), new EndTime("00:00"));
        Comparator<Event> comparator = Event.COMPARATOR;
        Event resultingEvent = eventList.get(index);
        while (index < eventList.size() && comparator.compare(event, eventList.get(index)) > 0) {
            index++;
            resultingEvent = eventList.get(index);
        }
        return resultingEvent;
    }

}
