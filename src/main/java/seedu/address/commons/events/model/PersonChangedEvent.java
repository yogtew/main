package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/** Indicates the AddressBook in the model has changed*/
public class PersonChangedEvent extends BaseEvent {

    public final Person oldPerson;
    public final Person newPerson;

    public PersonChangedEvent(Person oldPerson, Person newPerson) {
        this.oldPerson = oldPerson;
        this.newPerson = newPerson;
    }

    @Override
    public String toString() {
        if (newPerson != null) {
            return "person changed: " + oldPerson.toString() + " -> " + newPerson.toString();
        }
        return "person deleted: " + oldPerson.toString();
    }
}
