package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.student.Student;

/**
 * Provides a handle to a student card in the student list panel.
 */
public class StudentCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String FACULTY_FIELD_ID = "#faculty";
    private static final String STUDENT_NUMBER_FIELD_ID = "#studentNumber";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ATTENDANCE_FIELD_ID = "#attendance";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label facultyLabel;
    private final Label studentNumberLabel;
    private final Label emailLabel;
    private final Label attendanceLabel;
    private final List<Label> tagLabels;

    public StudentCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        facultyLabel = getChildNode(FACULTY_FIELD_ID);
        studentNumberLabel = getChildNode(STUDENT_NUMBER_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        attendanceLabel = getChildNode(ATTENDANCE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getFaculty() {
        return facultyLabel.getText();
    }

    public String getStudentNumber() {
        return studentNumberLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAttendance() {
        return attendanceLabel.getText();
    }
    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code student}.
     */
    public boolean equals(Student student) {
        return getName().equals(student.getName().fullName)
                && getFaculty().equals(student.getFaculty().value)
                && getStudentNumber().equals(student.getStudentNumber().value)
                && getEmail().equals(student.getEmail().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(student.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
