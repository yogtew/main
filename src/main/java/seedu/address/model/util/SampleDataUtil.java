package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.StartTime;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} and (@code Calendar) with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Damith Rajapakse"), new Phone("110100110"), new Email("damith@comp.nus.edu"),
                new Address("School of Computing"), new Attendance("absent"),
                getTagSet("student", "computerscience")),
            new Person(new Name("Alan Turing"), new Phone("100100110"), new Email("aturing@comp.com"),
                new Address("School of Computing"), new Attendance("absent"),
                getTagSet("student", "computerscience")),
            new Person(new Name("Von Neumann"), new Phone("100100101"), new Email("neumann@comp.com"),
                new Address("School of Computing"), new Attendance("absent"),
                getTagSet("student", "softwareengineering")),
            new Person(new Name("Bob Ross"), new Phone("12345678"), new Email("bobross@painting.com"),
                new Address("Faculty of Arts and Social Sciences"), new Attendance("absent"),
                getTagSet("student", "humanities")),
            new Person(new Name("Isaac Newton"), new Phone("9810000"), new Email("isaac@phy.com"),
                new Address("Faculty of Science"), new Attendance("absent"),
                getTagSet("student", "physics")),
            new Person(new Name("George Washington"), new Phone("04071776"), new Email("george@usa.com"),
                new Address("Faculty of Arts and Social Sciences"), new Attendance("absent"),
                getTagSet("student", "politicalscience"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("CS2103 tutorial 7"), new Date("17-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), new Description("v1.2")),
            new Event(new EventName("CS3230 tutorial 7"), new Date("17-10-2018"), new StartTime("14:00"),
                    new EndTime("15:00"), new Description("prepare tutorial material")),
            new Event(new EventName("Staff Meeting - week 9"), new Date("17-10-2018"), new StartTime("18:00"),
                    new EndTime("20:00"), new Description("v1.2")),
            new Event(new EventName("CS2103 tutorial 8"), new Date("24-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), new Description("mid v1.3")),
            new Event(new EventName("CS2103 tutorial 9"), new Date("31-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), new Description("v1.3 - demo")),
            new Event(new EventName("TA staff meeting"), new Date("31-10-2018"), new StartTime("10:00"),
                    new EndTime("12:30"), new Description("YIH")),
            new Event(new EventName("CS2103 tutorial 10"), new Date("7-11-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), new Description("mid v1.4")),
            new Event(new EventName("CS2103 exam"), new Date("5-12-2018"), new StartTime("17:00"),
                    new EndTime("18:00"), new Description("open book exam")),
            new Event(new EventName("CS2103 wrap up"), new Date("12-12-2018"), new StartTime("17:00"),
                    new EndTime("19:00"), new Description("clear all admin tasks")),
            new Event(new EventName("Post-semester lunch"), new Date("14-12-2018"), new StartTime("13:00"),
                    new EndTime("16:00"), new Description("@ SoC")),
        };
    }

    public static ReadOnlyCalendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (Event sampleEvent : getSampleEvents()) {
            sampleCalendar.addEvent(sampleEvent);
        }
        return sampleCalendar;
    }

}
