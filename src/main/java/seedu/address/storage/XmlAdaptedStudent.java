package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Faculty;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Student.
 */
public class XmlAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String studentNumber;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String faculty;
    @XmlElement(required = true)
    private String attendance;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedStudent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedStudent() {}

    /**
     * Constructs an {@code XmlAdaptedStudent} with the given student details.
     */
    public XmlAdaptedStudent(String name, String studentNumber,
                             String email, String faculty, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.faculty = faculty;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Student into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedStudent
     */
    public XmlAdaptedStudent(Student source) {
        name = source.getName().fullName;
        studentNumber = source.getStudentNumber().value;
        email = source.getEmail().value;
        faculty = source.getFaculty().value;
        attendance = source.getAttendance().value.toString();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted student object into the model's Student object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student
     */
    public Student toModelType() throws IllegalValueException {
        final List<Tag> studentTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            studentTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (studentNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentNumber.class.getSimpleName()));
        }
        if (!StudentNumber.isValidPhone(studentNumber)) {
            throw new IllegalValueException(StudentNumber.MESSAGE_PHONE_CONSTRAINTS);
        }
        final StudentNumber modelStudentNumber = new StudentNumber(studentNumber);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (faculty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Faculty.class.getSimpleName()));
        }
        if (!Faculty.isValidAddress(faculty)) {
            throw new IllegalValueException(Faculty.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Faculty modelFaculty = new Faculty(faculty);

        if (attendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Attendance.class.getSimpleName()));
        }
        final Attendance modelAttendance = new Attendance(attendance);

        final Set<Tag> modelTags = new HashSet<>(studentTags);
        return new Student(modelName, modelStudentNumber, modelEmail, modelFaculty, modelAttendance, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedStudent)) {
            return false;
        }

        XmlAdaptedStudent otherStudent = (XmlAdaptedStudent) other;
        return Objects.equals(name, otherStudent.name)
                && Objects.equals(studentNumber, otherStudent.studentNumber)
                && Objects.equals(email, otherStudent.email)
                && Objects.equals(faculty, otherStudent.faculty)
                && tagged.equals(otherStudent.tagged);
    }
}
