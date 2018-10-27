package seedu.address.model.mark;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * Stores a list of marked Persons.
 */
public class Mark {
    public static final String DEFAULT_NAME = "default";
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
