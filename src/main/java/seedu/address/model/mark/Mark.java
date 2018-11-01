package seedu.address.model.mark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Stores a set of marked Persons.
 */
public class Mark {
    public static final String DEFAULT_NAME = "default";
    public static final Mark EMPTY = new Mark();
    private HashSet<Person> set;
    private String name;

    public Mark(List<Person> inputList, String markName) {
        set = new HashSet<>();
        set.addAll(inputList);
        name = markName;
    }

    public Mark(Set<Person> set, String markName) {
        this.set = new HashSet<>();
        this.set.addAll(set);
        name = markName;
    }

    public Mark(List<Person> inputList) {
        this(inputList, DEFAULT_NAME);
    }

    public Mark(Set<Person> set) {
        this(set, DEFAULT_NAME);
    }

    public Mark() {
        set = new HashSet<>();
        name = DEFAULT_NAME;
    }

    public ArrayList<Person> getList() {
        return new ArrayList<>(set);
    }

    public Predicate<Person> getPredicate() {
        return person -> set.contains(person);
    }

    /**
     * Joins two marks (union)
     * @param other other mark to union
     * @return new mark containing union
     */
    public Mark join(Mark other) {
        Set<Person> copy = new HashSet<>(set);
        copy.addAll(other.getList());
        return new Mark(copy);
    }

    /**
     * Common elements of two marks (intersect)
     * @param other other mark to check
     * @return new mark containing intersection
     */
    public Mark intersect(Mark other) {
        ArrayList<Person> copy = new ArrayList<>(set);
        copy.retainAll(other.getList());
        return new Mark(copy);
    }


    public String getName() {
        return name;
    }

    public Set<Person> getSet() {
        return set;
    }

    public boolean contains(Person oldPerson) {
        return set.contains(oldPerson);
    }
}
