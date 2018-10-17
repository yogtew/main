package seedu.address.model.mark;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

public class Mark {
    public static String DEFAULT_NAME = "default";
    private ArrayList<Person> list;
    private String name;

    public Mark(List<Person> inputList, String markName) {
        list = new ArrayList<>();
        list.addAll(inputList);
        name = markName;
    }

    public Mark(List<Person> inputList) {
        this(inputList, DEFAULT_NAME);
    }

    public ArrayList<Person> getList() {
        return list;
    }


}
