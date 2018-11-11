package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowGraphRequestEvent;

/**
 * The Graph Panel of the App.
 */
public class GraphPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "https://cyrusguo.github.io/web/index.html?";
    public static final String SEARCH_PAGE_URL =
            "https://cyrusguo.github.io/web/index.html?";
    public static final String DATA_1_PARSE = "&data1=";
    public static final String DATA_2_PARSE = "&data2=";
    public static final String DATA_3_PARSE = "&data3=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public GraphPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads graph using attendance data from event object.
     */
    private void loadGraph(ShowGraphRequestEvent event) {
        String url = SEARCH_PAGE_URL
                + DATA_1_PARSE + event.getAttendanceList().get(0)
                + DATA_2_PARSE + event.getAttendanceList().get(1)
                + DATA_3_PARSE + event.getAttendanceList().get(2);
        loadPage(url);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        // URL defaultPage = MainApp.class.getResource(DEFAULT_PAGE);
        loadPage(DEFAULT_PAGE);
    }


    @Subscribe
    private void showGraphRequestEvent(ShowGraphRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadGraph(event);
    }
}
