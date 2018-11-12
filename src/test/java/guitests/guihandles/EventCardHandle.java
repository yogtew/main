package guitests.guihandles;

import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.event.Event;

/**
 * Provides a handle to an event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String EVENT_NAME_FIELD_ID = "#eventName";
    private static final String DATE_FIELD_ID = "#date";
    private static final String START_TIME_FIELD_ID = "#startTime";
    private static final String END_TIME_FIELD_ID = "#endTime";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private final Label eventNameLabel;
    private final Label dateLabel;
    private final Label startTimeLabel;
    private final Label endTimeLabel;
    private final Optional<Label> descriptionLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        eventNameLabel = getChildNode(EVENT_NAME_FIELD_ID);
        dateLabel = getChildNode(DATE_FIELD_ID);
        startTimeLabel = getChildNode(START_TIME_FIELD_ID);
        endTimeLabel = getChildNode(END_TIME_FIELD_ID);
        descriptionLabel = getOptionalChildNode(DESCRIPTION_FIELD_ID);
    }

    public String getEventName() {
        return eventNameLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText().replaceFirst("^Date: ", "");
    }

    public String getStartTime() {
        return startTimeLabel.getText().replaceFirst("^Start: ", "");
    }

    public String getEndTime() {
        return endTimeLabel.getText().replaceFirst("End: ", "");
    }

    public Optional<String> getDescription() {
        return descriptionLabel.map(desc -> desc.getText());
    }

    /**
     * Returns true if this handle contains {@code event}.
     */
    public boolean equals(Event event) {
        return getEventName().equals(event.getEventName().eventName)
                && getDate().equals(event.getDate().date)
                && getStartTime().equals(event.getStartTime().startTime)
                && getEndTime().equals(event.getEndTime().endTime);
    }
}
