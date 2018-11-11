package seedu.address.model.group;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.student.Student;

// todo: REGEX validation on name: alphanumeric & no spaces
/**
 * Stores a set of grouped Students.
 */
public class Group {
    public static final String DEFAULT_NAME = "default";
    public static final String GROUP_NAME_CONSTRAINTS =
            "Group names should only contain alphanumeric characters, and it should not be blank";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Specified group does not exist";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String GROUP_VALIDATION_REGEX = "[\\p{Alnum}]+";

    private HashSet<Student> set;
    private String name;

    public Group(Collection<Student> inputCollection, String groupName) {
        this.set = new HashSet<>(inputCollection);
        checkArguments(groupName);
        name = groupName;
    }

    public Group(Group group) {
        this.set = new HashSet<>(group.set);
        this.name = group.name;
    }

    public Group(List<Student> inputList) {
        this(inputList, DEFAULT_NAME);
    }

    public Group(Set<Student> set) {
        this(set, DEFAULT_NAME);
    }

    public Group() {
        set = new HashSet<>();
        name = DEFAULT_NAME;
    }

    /**
     * checks the validity of the name and throws an exception otherwise
     * @param groupName
     */
    public static void checkArguments(String groupName) {
        if (!isValidGroupName(groupName)) {
            throw new IllegalArgumentException(GROUP_NAME_CONSTRAINTS);
        }
    }

    /**
            * Returns true if a given string is a valid name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_VALIDATION_REGEX);
    }

    public ArrayList<Student> getList() {
        return new ArrayList<>(set);
    }

    public Predicate<Student> getPredicate() {
        return student -> set.contains(student);
    }

    /**
     * Joins two groups (union)
     * @param other other group to union
     * @return new group containing union
     */
    public Group join(Group other) {
        Set<Student> copy = new HashSet<>(set);
        copy.addAll(other.getList());
        return new Group(copy);
    }

    /**
     * Common elements of two groups (intersect)
     * @param other other group to check
     * @return new group containing intersection
     */
    public Group intersect(Group other) {
        ArrayList<Student> copy = new ArrayList<>(set);
        copy.retainAll(other.getList());
        return new Group(copy);
    }

    /**
     * Returns an empty Group
     * @return empty group
     */
    public static Group getEmpty() {
        return new Group();
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

    public int count() {
        return set.size();
    }

    public boolean contains(Student oldStudent) {
        return set.contains(oldStudent);
    }
}
