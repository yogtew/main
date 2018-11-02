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
import seedu.address.model.student.Faculty;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} and (@code Calendar) with sample data.
 */
public class SampleDataUtil {

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Damith Rajapakse"), new StudentNumber("110100110"), new Email("damith@comp.nus.edu"),
                new Faculty("School of Computing"), new Attendance("absent"),
                getTagSet("student", "computerscience")),
            new Student(new Name("Alan Turing"), new StudentNumber("100100110"), new Email("aturing@comp.com"),
                new Faculty("School of Computing"), new Attendance("absent"),
                getTagSet("student", "computerscience")),
            new Student(new Name("Von Neumann"), new StudentNumber("100100101"), new Email("neumann@comp.com"),
                new Faculty("School of Computing"), new Attendance("absent"),
                getTagSet("student", "softwareengineering")),
            new Student(new Name("Bob Ross"), new StudentNumber("12345678"), new Email("bobross@painting.com"),
                new Faculty("Faculty of Arts and Social Sciences"), new Attendance("absent"),
                getTagSet("student", "humanities")),
            new Student(new Name("Isaac Newton"), new StudentNumber("9810000"), new Email("isaac@phy.com"),
                new Faculty("Faculty of Science"), new Attendance("absent"),
                getTagSet("student", "physics")),
            new Student(new Name("George Washington"), new StudentNumber("04071776"), new Email("george@usa.com"),
                new Faculty("Faculty of Arts and Social Sciences"), new Attendance("absent"),
                getTagSet("student", "politicalscience"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
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
