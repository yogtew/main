package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
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
            new Student(new Name("John"), new StudentNumber("1880960"), new Email("s1880960@example.com"),
                    new Faculty("Medicine"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut1", "year3", "gradeC")),
            new Student(new Name("Preston"), new StudentNumber("1815275"), new Email("s1815275@example.com"),
                    new Faculty("Pharmacy"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut3", "year2", "gradeA")),
            new Student(new Name("Samuel"), new StudentNumber("4614852"), new Email("s4614852@example.com"),
                    new Faculty("Arts and Social Sciences"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut3", "year2", "gradeA")),
            new Student(new Name("Dylan"), new StudentNumber("2061329"), new Email("s2061329@example.com"),
                    new Faculty("Business"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeB")),
            new Student(new Name("Chris"), new StudentNumber("4475679"), new Email("s4475679@example.com"),
                    new Faculty("Medicine"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut3", "year3", "gradeB")),
            new Student(new Name("Bill"), new StudentNumber("1065767"), new Email("s1065767@example.com"),
                    new Faculty("Music"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut3", "year3", "gradeC")),
            new Student(new Name("Chloe"), new StudentNumber("2814519"), new Email("s2814519@example.com"),
                    new Faculty("Pharmacy"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut3", "year2", "gradeB")),
            new Student(new Name("Maria"), new StudentNumber("9461250"), new Email("s9461250@example.com"),
                    new Faculty("Design and Environment"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut3", "year3", "gradeA")),
            new Student(new Name("Ariana"), new StudentNumber("8093288"), new Email("s8093288@example.com"),
                    new Faculty("Pharmacy"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut1", "year2", "gradeC")),
            new Student(new Name("Natalie"), new StudentNumber("2413185"), new Email("s2413185@example.com"),
                    new Faculty("Music"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut3", "year2", "gradeB")),
            new Student(new Name("Raymond"), new StudentNumber("5841571"), new Email("s5841571@example.com"),
                    new Faculty("Pharmacy"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeA")),
            new Student(new Name("Jake"), new StudentNumber("4995245"), new Email("s4995245@example.com"),
                    new Faculty("Engineering"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeA")),
            new Student(new Name("Yogu"), new StudentNumber("5224770"), new Email("s5224770@example.com"),
                    new Faculty("Science"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut1", "year2", "gradeC")),
            new Student(new Name("Alan"), new StudentNumber("4327961"), new Email("s4327961@example.com"),
                    new Faculty("Pharmacy"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut3", "year3", "gradeB")),
            new Student(new Name("Gina"), new StudentNumber("4452497"), new Email("s4452497@example.com"),
                    new Faculty("Pharmacy"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut1", "year3", "gradeC")),
            new Student(new Name("Claudine"), new StudentNumber("2649506"), new Email("s2649506@example.com"),
                    new Faculty("Medicine"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeA")),
            new Student(new Name("Tessa"), new StudentNumber("1133087"), new Email("s1133087@example.com"),
                    new Faculty("Design and Environment"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeB")),
            new Student(new Name("Geralt"), new StudentNumber("9590590"), new Email("s9590590@example.com"),
                    new Faculty("Computing"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeC")),
            new Student(new Name("Harry"), new StudentNumber("7437484"), new Email("s7437484@example.com"),
                    new Faculty("Arts and Social Sciences"), new Attendance("present"),
                    getTagSet("student", "cs2103", "tut2", "year2", "gradeC")),
            new Student(new Name("Putin"), new StudentNumber("6234958"), new Email("s6234958@example.com"),
                    new Faculty("Pharmacy"), new Attendance("absent"),
                    getTagSet("student", "cs2103", "tut1", "year3", "gradeB"))
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

    public static Event[] getSampleSortedEvents() {
        Event[] events = new Event[] {
            new Event(new EventName("TA staff meeting"), new Date("31-10-2018"), new StartTime("10:00"),
                    new EndTime("12:30"), Optional.of(new Description("YIH"))),
            new Event(new EventName("CS2103 tutorial 7"), new Date("17-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), Optional.of(new Description("v1.2"))),
            new Event(new EventName("CS3230 tutorial 7"), new Date("17-10-2018"), new StartTime("14:00"),
                    new EndTime("15:00"), Optional.of(new Description("prepare tutorial material"))),
            new Event(new EventName("Staff Meeting - week 9"), new Date("17-10-2018"), new StartTime("18:00"),
                    new EndTime("20:00"), Optional.of(new Description("v1.2"))),
            new Event(new EventName("CS2103 exam"), new Date("5-12-2018"), new StartTime("17:00"),
                    new EndTime("18:00"), Optional.of(new Description("open book exam"))),
            new Event(new EventName("Post-semester lunch"), new Date("14-12-2018"), new StartTime("13:00"),
                    new EndTime("16:00"), Optional.of(new Description("@ SoC"))),
            new Event(new EventName("CS2103 tutorial 8"), new Date("24-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), Optional.of(new Description("mid v1.3"))),
            new Event(new EventName("CS2103 tutorial 9"), new Date("31-10-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), Optional.of(new Description("v1.3 - demo"))),
            new Event(new EventName("CS2103 tutorial 10"), new Date("7-11-2018"), new StartTime("13:00"),
                    new EndTime("14:00"), Optional.of(new Description("mid v1.4"))),
            new Event(new EventName("CS2103 wrap up"), new Date("12-12-2018"), new StartTime("17:00"),
                    new EndTime("19:00"), Optional.of(new Description("clear all admin tasks"))),
            new Event(new EventName("SoC Christmas party"), new Date("25-12-2018"), new StartTime("18:00"),
                    new EndTime("20:00"), Optional.of(new Description("Pot luck dinner"))),
            new Event(new EventName("CS3230 tutorial 8"), new Date("18-10-2018"), new StartTime("14:00"),
                    new EndTime("15:00"), Optional.of(new Description("NP-completeness"))),
            new Event(new EventName("CS3230 tutorial 9"), new Date("1-11-2018"), new StartTime("14:00"),
                    new EndTime("15:00"), Optional.empty()),
            new Event(new EventName("CS320 staff meeting - week 9"), new Date("2-11-2018"), new StartTime("13:00"),
                    new EndTime("16:00"), Optional.of(new Description("Assignment #2"))),
            new Event(new EventName("CS3230 tutorial 10"), new Date("7-11-2018"), new StartTime("14:00"),
                    new EndTime("16:00"), Optional.of(new Description("review assignment"))),
            new Event(new EventName("CS3230 consultation"), new Date("15-11-2018"), new StartTime("16:00"),
                    new EndTime("17:00"), Optional.of(new Description("With Alan"))),
            new Event(new EventName("CS2103 consultation"), new Date("16-11-2018"), new StartTime("16:00"),
                    new EndTime("17:00"), Optional.of(new Description("With team W13-3"))),
            new Event(new EventName("CS3230 tutorial 11"), new Date("14-11-2018"), new StartTime("14:00"),
                    new EndTime("15:00"), Optional.of(new Description("review content"))),
            new Event(new EventName("CS3230 final exam"), new Date("1-12-2018"), new StartTime("13:00"),
                    new EndTime("15:00"), Optional.of(new Description("Invigilating"))),
        };
        Arrays.sort(events, Event.COMPARATOR);
        return events;
    }

    public static ReadOnlyCalendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (Event sampleEvent : getSampleSortedEvents()) {
            sampleCalendar.addEvent(sampleEvent);
        }
        return sampleCalendar;
    }

}
