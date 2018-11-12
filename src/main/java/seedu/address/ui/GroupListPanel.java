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
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetStudentViewEvent;
import seedu.address.model.group.Group;

/**
 * Panel containing the list of students.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GenericListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<Group> genericListView;

    public GroupListPanel(ObservableList<Group> groupObservableList) {
        super(FXML);
        setConnections(groupObservableList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Group> groupObservableList) {
        genericListView.setItems(groupObservableList);
        genericListView.setCellFactory(listView -> new GroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        genericListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in group list panel changed to : '" + newValue + "'");
                        raise(new GroupPanelSelectionChangedEvent(newValue));
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
    class GroupListViewCell extends ListCell<Group> {
        @Override
        protected void updateItem(Group group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new GroupCard(group, getIndex() + 1).getRoot());
            }
        }
    }

}
