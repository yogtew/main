package seedu.address.model.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHYSICS;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.StudentBuilder;

public class StudentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        student.getTags().remove(0);
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // different student number and email -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // different name -> returns false
        editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // same name, same student number, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withFaculty(VALID_FACULTY_BOB)
                .withTags(VALID_TAG_PHYSICS).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_BOB).withFaculty(VALID_FACULTY_BOB)
                .withTags(VALID_TAG_PHYSICS).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same student number, same email, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE).withFaculty(VALID_FACULTY_BOB).withTags(VALID_TAG_PHYSICS).build();
        assertTrue(ALICE.isSameStudent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different student -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different student number -> returns false
        editedAlice = new StudentBuilder(ALICE).withStudentNumber(VALID_STUDENT_NUMBER_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different faculty -> returns false
        editedAlice = new StudentBuilder(ALICE).withFaculty(VALID_FACULTY_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_PHYSICS).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
