package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.Faculty;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.Assert;

public class XmlAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_STUDENT_NUMBER = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_STUDENT_NUMBER = BENSON.getStudentNumber().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getFaculty().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        XmlAdaptedStudent student = new XmlAdaptedStudent(BENSON);
        assertEquals(BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(INVALID_NAME, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(null, VALID_STUDENT_NUMBER,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidStudentNumber_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_NAME, INVALID_STUDENT_NUMBER, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = StudentNumber.MESSAGE_STUDENT_NUMBER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullStudentNumber_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_NAME, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_NAME, VALID_STUDENT_NUMBER, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_NAME, VALID_STUDENT_NUMBER, null,
                VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_NAME, VALID_STUDENT_NUMBER, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Faculty.MESSAGE_FACULTY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedStudent student = new XmlAdaptedStudent(VALID_NAME, VALID_STUDENT_NUMBER,
                VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Faculty.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedStudent student =
                new XmlAdaptedStudent(VALID_NAME, VALID_STUDENT_NUMBER, VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, student::toModelType);
    }

}
