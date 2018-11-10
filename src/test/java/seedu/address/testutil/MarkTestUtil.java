package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

public class MarkTestUtil {
    public static final Student A = new StudentBuilder().withTags("cs2103", "tut1", "m")
            .withName("Adam").withFaculty("SoC").withStudentNumber("A0123456A").withEmail("test@example.com").build();
    public static final Student B = new StudentBuilder().withTags("cs2103", "tut1", "f")
            .withName("Brina").withFaculty("SoC").withStudentNumber("A0123456A").withEmail("test@example.com").build();
    public static final Student C = new StudentBuilder().withTags("cs2103", "tut2", "m")
            .withName("Chris").withFaculty("SoC").withStudentNumber("A0123456A").withEmail("test@example.com").build();
    public static final Student D = new StudentBuilder().withTags("cs2103", "tut2", "f")
            .withName("Diane").withFaculty("SoC").withStudentNumber("A0123456A").withEmail("test@example.com").build();
    private MarkTestUtil() {}

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(A, B, C, D));
    }
}
