package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.ui.GraphPanel.DEFAULT_PAGE;

import java.net.URL;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GraphPanelHandle;
import seedu.address.commons.events.ui.ShowGraphRequestEvent;

public class GraphPanelTest extends GuiUnitTest {
    private ShowGraphRequestEvent graphRequestEventStub;

    private GraphPanel graphPanel;
    private GraphPanelHandle graphPanelHandle;

    @Before
    public void setUp() {
        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(3);
        graphRequestEventStub = new ShowGraphRequestEvent(integerList);

        guiRobot.interact(() -> graphPanel = new GraphPanel());
        uiPartRule.setUiPart(graphPanel);

        graphPanelHandle = new GraphPanelHandle(graphPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, graphPanelHandle.getLoadedUrl());

        // associated web page of a person
        URL expectedPersonUrl = new URL(GraphPanel.SEARCH_PAGE_URL);

        waitUntilBrowserLoaded(graphPanelHandle);
        assertEquals(expectedPersonUrl, graphPanelHandle.getLoadedUrl());
    }
}
