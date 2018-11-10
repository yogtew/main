package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.Student;

/** Indicates the AddressBook in the model has changed*/
public class StudentChangedEvent extends BaseEvent {

    public final Student oldStudent;
    public final Student newStudent;

    public StudentChangedEvent(Student oldStudent, Student newStudent) {
        this.oldStudent = oldStudent;
        this.newStudent = newStudent;
    }

    @Override
    public String toString() {
        if (newStudent != null) {
            return "student changed: " + oldStudent.toString() + " -> " + newStudent.toString();
        }
        return "student deleted: " + oldStudent.toString();
    }
}
