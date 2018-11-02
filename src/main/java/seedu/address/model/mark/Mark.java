package seedu.address.model.mark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.student.Student;

/**
 * Stores a set of marked Students.
 */
public class Mark {
    public static final String DEFAULT_NAME = "default";
    public static final Mark EMPTY = new Mark();
    private HashSet<Student> set;
    private String name;

    public Mark(List<Student> inputList, String markName) {
        set = new HashSet<>();
        set.addAll(inputList);
        name = markName;
    }

    public Mark(Set<Student> set, String markName) {
        this.set = new HashSet<>();
        this.set.addAll(set);
        name = markName;
    }

    public Mark(List<Student> inputList) {
        this(inputList, DEFAULT_NAME);
    }

    public Mark(Set<Student> set) {
        this(set, DEFAULT_NAME);
    }

    public Mark() {
        set = new HashSet<>();
        name = DEFAULT_NAME;
    }

    public ArrayList<Student> getList() {
        return new ArrayList<>(set);
    }

    public Predicate<Student> getPredicate() {
        return student -> set.contains(student);
    }

    /**
     * Joins two marks (union)
     * @param other other mark to union
     * @return new mark containing union
     */
    public Mark join(Mark other) {
        Set<Student> copy = new HashSet<>(set);
        copy.addAll(other.getList());
        return new Mark(copy);
    }

    /**
     * Common elements of two marks (intersect)
     * @param other other mark to check
     * @return new mark containing intersection
     */
    public Mark intersect(Mark other) {
        ArrayList<Student> copy = new ArrayList<>(set);
        copy.retainAll(other.getList());
        return new Mark(copy);
    }


    public String getName() {
        return name;
    }

    public Set<Student> getSet() {
        return set;
    }

    public boolean contains(Student oldStudent) {
        return set.contains(oldStudent);
    }
}
