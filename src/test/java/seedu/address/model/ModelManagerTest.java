package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENT;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CalendarBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasStudent(null);
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredStudentList().remove(0);
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointer () {
        thrown.expect(NullPointerException.class);
        modelManager.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInCalendar_returnsFalse() {
        assertFalse(modelManager.hasEvent(TUTORIAL));
    }

    @Test
    public void hasEvent_eventInCalendar_returnsTrue() {
        modelManager.addEvent(TUTORIAL);
        assertTrue(modelManager.hasEvent(TUTORIAL));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withStudent(ALICE).withStudent(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        Calendar calendar = new CalendarBuilder().withEvent(TUTORIAL).withEvent(CONSULTATION).build();
        Calendar differentCalendar = new Calendar();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, calendar, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, calendar, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, calendar, userPrefs)));

        // different calendar -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentCalendar, userPrefs)));

        // different filteredStudentList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, calendar, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENT);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, calendar, differentUserPrefs)));
    }
}
