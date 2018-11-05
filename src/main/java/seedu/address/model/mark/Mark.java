package seedu.address.model.mark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.student.Student;

// todo: REGEX validation on name: alphanumeric & no spaces
/**
 * Stores a set of marked Students.
 */
public class Mark {
    public static final String DEFAULT_NAME = "default";
    public static final Mark EMPTY = new Mark();
    private HashSet<Student> set;
    private String name;

    public static final String MARK_NAME_CONSTRAINTS =
            "Mark names should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MARK_VALIDATION_REGEX = "[\\p{Alnum}]+";

    public Mark(Collection<Student> inputCollection, String markName) {
        this.set = new HashSet<>(inputCollection);
        checkArguments(markName);
        name = markName;
    }

    public static void checkArguments(String markName) {
        if (!isValidMark(markName)) {
            throw new IllegalArgumentException(MARK_NAME_CONSTRAINTS);
        }
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

    /**
            * Returns true if a given string is a valid name.
     */
    public static boolean isValidMark(String test) {
        return test.matches(MARK_VALIDATION_REGEX);
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

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getSet() {
        return set;
    }

    public boolean contains(Student oldStudent) {
        return set.contains(oldStudent);
    }
}
