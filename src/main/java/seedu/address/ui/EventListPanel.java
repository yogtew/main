package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.model.event.Date;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<Event> eventListView;

    public EventListPanel(ObservableList<Event> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
        scrollTo(LocalDateTime.now(), eventList);
    }

    private void setConnections(ObservableList<Event> eventList) {
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code EventCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Scrolls to the {@code EventCard} just after the given {@code date} and selects it.
     */
    private void scrollTo(LocalDateTime time, ObservableList<Event> eventList) {
        String date = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String startTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        int index = 0;
        // Creating a dummy event to compare to
        Event event = new Event(new EventName("a"), new Date(date), new StartTime(startTime), new EndTime("00:00"));
        Comparator<Event> comparator = Event.COMPARATOR;
        // Finding the index of the nearest upcoming event to scroll to
        while (index < eventList.size() && comparator.compare(event, eventList.get(index)) > 0) {
            index++;
        }
        scrollTo(index);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);

            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

}
