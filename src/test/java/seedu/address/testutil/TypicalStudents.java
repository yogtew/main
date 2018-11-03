package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHYSICS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withFaculty("School of Computing").withEmail("alice@example.com")
            .withStudentNumber("A94351253").withAttendance("0")
            .withTags("computerscience").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withFaculty("School of Computing")
            .withEmail("johnd@example.com").withStudentNumber("U98765432").withAttendance("0")
            .withTags("informationsystems", "student").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withStudentNumber("A95352563")
            .withEmail("heinz@example.com").withFaculty("Faculty of Engineering").withAttendance("0").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withStudentNumber("A87652533")
            .withEmail("cornelia@example.com").withFaculty("School of Computing").withTags("businessanalytics")
            .withAttendance("0").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withStudentNumber("A9482224")
            .withEmail("werner@example.com").withFaculty("School of Computing").withAttendance("0").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withStudentNumber("A9482427")
            .withEmail("lydia@example.com").withFaculty("Faculty of Engineering").withAttendance("0").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withStudentNumber("A9482442")
            .withEmail("anna@example.com").withFaculty("School of Computing").withAttendance("0").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withStudentNumber("U8482424")
            .withEmail("stefan@example.com").withFaculty("Faculty of Science").withAttendance("0").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withStudentNumber("A8482131")
            .withEmail("hans@example.com").withFaculty("Faculty of Science").withAttendance("0").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withStudentNumber(VALID_STUDENT_NUMBER_AMY)
            .withEmail(VALID_EMAIL_AMY).withFaculty(VALID_ADDRESS_AMY).withAttendance(VALID_ATTENDANCE_AMY)
            .withTags(VALID_TAG_MATH).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB)
            .withStudentNumber(VALID_STUDENT_NUMBER_BOB)
            .withEmail(VALID_EMAIL_BOB).withFaculty(VALID_FACULTY_BOB)
            .withAttendance(VALID_ATTENDANCE_BOB).withTags(VALID_TAG_PHYSICS, VALID_TAG_MATH)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
