package seedu.address.model.mark;

import java.util.*;

public class MarkManager {
    private Map<String, Mark> marks;
    public MarkManager() {
        marks = new HashMap<>();
        marks.put(Mark.DEFAULT_NAME, new Mark(new ArrayList<>()));
    }

    public Mark getMark(String markName) {
        return marks.get(markName);
    }

    public void setMark(String markName, Mark mark) { marks.put(markName, mark); }
}
