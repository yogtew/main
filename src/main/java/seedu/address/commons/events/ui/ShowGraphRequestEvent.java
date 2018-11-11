package seedu.address.commons.events.ui;

import java.util.ArrayList;

import seedu.address.commons.events.BaseEvent;


/**
 * An event requesting to view the help page.
 */
public class ShowGraphRequestEvent extends BaseEvent {

    private final ArrayList<Integer> attendanceList;

    public ShowGraphRequestEvent(ArrayList<Integer> attendanceList) {

        this.attendanceList = attendanceList;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public ArrayList<Integer> getAttendanceList() {
        return attendanceList;
    }
}
