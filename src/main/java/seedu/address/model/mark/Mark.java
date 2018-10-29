package seedu.address.model.mark;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Stores a list of marked Persons.
 */
public class Mark {
    public static final String DEFAULT_NAME = "default";
    public static final Mark EMPTY = new Mark();
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

    public Mark() {
        list = new ArrayList<>();
        name = DEFAULT_NAME;
    }

    public ArrayList<Person> getList() {
        return list;
    }

    public Predicate<Person> getPredicate() {
        return person -> list.contains(person);
    }

    public Mark join(Mark other) {
        ArrayList<Person> copy = new ArrayList<>(list);
        copy.removeAll(other.getList());
        copy.addAll(other.getList());
        return new Mark(copy);
    }

    public Mark intersect(Mark other) {
        ArrayList<Person> copy = new ArrayList<>(list);
        copy.retainAll(other.getList());
        return new Mark(copy);
    }


}
