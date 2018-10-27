package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AttendanceTest {

    @Test
    public void equals() {
        Attendance attendance = new Attendance("present");

        // if its the same object, return true
        assertTrue(attendance.equals(attendance));

        // if it has the same value, return true
        Attendance anotherAttd = new Attendance(attendance.value);
        assertTrue(attendance.equals(anotherAttd));

        // if it is of different types, return false
        assertFalse(attendance.equals(1));

        // if it is null, return false;
        assertFalse(attendance.equals(null));

        // if it is different attendance, return false
        Attendance diffAttd = new Attendance("absent");
        assertFalse(attendance.equals(diffAttd));
    }
}
