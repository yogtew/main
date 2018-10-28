package seedu.address.model.email;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidSubject() {
        //null body
        Assert.assertThrows(NullPointerException.class, () -> Body.isValidBody(null));

        //invalid body
        assertFalse(Subject.isValidSubject("")); //empty string
        assertFalse(Subject.isValidSubject(" ")); //spaces only

        //valid body
        assertTrue(Subject.isValidSubject("peter jack")); // alphabets only
        assertTrue(Subject.isValidSubject("peter the 2nd")); // alphanumeric characters
        assertTrue(Subject.isValidSubject("Capital Tan")); // with capital letters
        assertTrue(Subject.isValidSubject("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Body.isValidBody("^")); // non-alphanumeric characters
        assertTrue(Body.isValidBody("peter*")); // contains non-alphanumeric characters


    }
}

