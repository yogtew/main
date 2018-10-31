package seedu.address.model.email;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidBody = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Body(invalidBody));
    }

    @Test
    public void isValidBody() {
        //null body
        Assert.assertThrows(NullPointerException.class, () -> Body.isValidBody(null));

        //invalid body
        assertFalse(Body.isValidBody("")); //empty string
        assertFalse(Body.isValidBody(" ")); //spaces only


        //valid body
        assertTrue(Body.isValidBody("peter jack")); // alphabets only
        assertTrue(Body.isValidBody("12345")); // numbers only
        assertTrue(Body.isValidBody("peter the 2nd")); // alphanumeric characters
        assertTrue(Body.isValidBody("Capital Tan")); // with capital letters
        assertTrue(Body.isValidBody("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Body.isValidBody("^")); // non-alphanumeric characters
        assertTrue(Body.isValidBody("peter*")); // contains non-alphanumeric characters


    }
}
