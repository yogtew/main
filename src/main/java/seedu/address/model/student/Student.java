package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the faculty book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final StudentNumber studentNumber;
    private final Email email;

    // Data fields
    private final Faculty faculty;
    private final Attendance attendance;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, StudentNumber studentNumber, Email email, Faculty faculty, Set<Tag> tags) {
        requireAllNonNull(name, studentNumber, email, faculty, tags);
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.faculty = faculty;
        this.tags.addAll(tags);
        this.attendance = new Attendance("absent");
    }

    /**
     *
     * Overloaded Constructor
     */
    public Student(Name name, StudentNumber studentNumber, Email email, Faculty faculty, Attendance attendance, Set<Tag> tags) {
        requireAllNonNull(name, studentNumber, email, faculty, attendance, tags);
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.faculty = faculty;
        this.attendance = attendance;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public Email getEmail() {
        return email;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both students of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.getName().equals(getName())
                && (otherStudent.getStudentNumber().equals(getStudentNumber()) || otherStudent.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(getName())
                && otherStudent.getStudentNumber().equals(getStudentNumber())
                && otherStudent.getEmail().equals(getEmail())
                && otherStudent.getFaculty().equals(getFaculty())
                && otherStudent.getAttendance().equals(getAttendance())
                && otherStudent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, studentNumber, email, faculty, attendance, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" StudentNumber: ")
                .append(getStudentNumber())
                .append(" Email: ")
                .append(getEmail())
                .append(" Faculty: ")
                .append(getFaculty())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
