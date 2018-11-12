package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.StudentCardHandle;
import guitests.guihandles.StudentListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.event.Event;
import seedu.address.model.student.Student;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(StudentCardHandle expectedCard, StudentCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getFaculty(), actualCard.getFaculty());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getStudentNumber(), actualCard.getStudentNumber());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedStudent}.
     */
    public static void assertCardDisplaysStudent(Student expectedStudent, StudentCardHandle actualCard) {
        assertEquals(expectedStudent.getName().fullName, actualCard.getName());
        assertEquals(expectedStudent.getStudentNumber().value, actualCard.getStudentNumber());
        assertEquals(expectedStudent.getEmail().value, actualCard.getEmail());
        assertEquals(expectedStudent.getFaculty().value, actualCard.getFaculty());
        assertEquals(expectedStudent.getAttendance().value.toString(), actualCard.getAttendance());
        assertEquals(expectedStudent.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(Event expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getEventName().eventName, actualCard.getEventName());
        assertEquals(expectedEvent.getDate().date, actualCard.getDate());
        assertEquals(expectedEvent.getStartTime().startTime, actualCard.getStartTime());
        assertEquals(expectedEvent.getEndTime().endTime, actualCard.getEndTime());

        expectedEvent.getDescription().ifPresentOrElse(desc -> {
            assertEquals(desc.description, actualCard.getDescription().orElse(null));
        }, () -> assertEquals(" ", actualCard.getDescription().orElse(null)));
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, Student... students) {
        for (int i = 0; i < students.length; i++) {
            studentListPanelHandle.navigateToCard(i);
            assertCardDisplaysStudent(students[i], studentListPanelHandle.getStudentCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, Event... events) {
        for (int i = 0; i < events.length; i++) {
            eventListPanelHandle.navigateToCard(i);
            assertCardDisplaysEvent(events[i], eventListPanelHandle.getEventCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, List<Student> students) {
        assertListMatching(studentListPanelHandle, students.toArray(new Student[0]));
    }

    /**
     * Asserts that the list in {@code eventListPanelHandle} displays the details of {@code events} correctly and
     * in the correct order.
     */
    public static void assertListMatching(EventListPanelHandle eventListPanelHandle, List<Event> events) {
        assertListMatching(eventListPanelHandle, events.toArray(new Event[0]));
    }

    /**
     * Asserts the size of the list in {@code studentListPanelHandle
     * } equals to {@code size}.
     */
    public static void assertListSize(StudentListPanelHandle studentListPanelHandle, int size) {
        int numberOfStudents = studentListPanelHandle.getListSize();
        assertEquals(size, numberOfStudents);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
