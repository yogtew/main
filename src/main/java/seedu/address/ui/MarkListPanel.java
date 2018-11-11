package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.MarkPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetStudentViewEvent;
import seedu.address.model.mark.Mark;

/**
 * Panel containing the list of students.
 */
public class MarkListPanel extends UiPart<Region> {
    private static final String FXML = "GenericListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MarkListPanel.class);

    @FXML
    private ListView<Mark> genericListView;

    public MarkListPanel(ObservableList<Mark> markObservableList) {
        super(FXML);
        setConnections(markObservableList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Mark> markObservableList) {
        genericListView.setItems(markObservableList);
        genericListView.setCellFactory(listView -> new MarkListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        genericListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in mark list panel changed to : '" + newValue + "'");
                        raise(new MarkPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code StudentCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            genericListView.scrollTo(index);
            genericListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleResetStudentViewEvent(ResetStudentViewEvent event) {
        System.out.println("Clear selection");
        Platform.runLater(() -> {
            genericListView.getSelectionModel().clearSelection();
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Student} using a {@code StudentCard}.
     */
    class MarkListViewCell extends ListCell<Mark> {
        @Override
        protected void updateItem(Mark mark, boolean empty) {
            super.updateItem(mark, empty);

            if (empty || mark == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MarkCard(mark, getIndex() + 1).getRoot());
            }
        }
    }

}
