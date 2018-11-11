package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

import javafx.util.Pair;

public class TagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_indexSingleTag_success() {
        // Set a tag to student
        HashSet<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("tut1"));

        Student expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0)).withTags("tut1").build();
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.SET);

        String expectedMessage = "Successfully updated tags of 1 student";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getCalendar(), new UserPrefs());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Add a tag to student
        tagsToAdd.add(new Tag("tut2"));
        tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.ADD);

        expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0)).withTags("tut1", "tut2").build();

        expectedMessage = "Successfully added tags to 1 student";

        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Remove a tag from a student
        HashSet<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(new Tag("tut1"));
        tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToRemove, TagCommandMode.DEL);

        expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0)).withTags("tut2").build();

        expectedMessage = "Successfully deleted tags from 1 student";

        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexSingleTag_fail() {
        // Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
        // Set a tag to student
        HashSet<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("tut1"));
        Index bigIndex = Index.fromZeroBased(Integer.MAX_VALUE);

        TagCommand tagCommand = new TagCommand(bigIndex, tagsToAdd, TagCommandMode.SET);

        assertCommandFailure(tagCommand, model, commandHistory, Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_indexMultipleTag_success() {
        // Set a tag to student
        HashSet<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("tut1"));
        tagsToAdd.add(new Tag("cs2103"));

        Student expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0))
                .withTags("tut1", "cs2103").build();
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.SET);

        String expectedMessage = "Successfully updated tags of 1 student";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getCalendar(), new UserPrefs());
        expectedModel.updateStudent(model.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Add a tag to student
        tagsToAdd.add(new Tag("tut2"));
        tagsToAdd.add(new Tag("cs2105"));
        tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.ADD);

        expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0))
                .withTags("tut1", "tut2", "cs2103", "cs2105").build();

        expectedMessage = "Successfully added tags to 1 student";

        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Remove a tag from a student
        HashSet<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(new Tag("tut1"));
        tagsToRemove.add(new Tag("tut2"));
        tagCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToRemove, TagCommandMode.DEL);

        expectedStudent = new StudentBuilder(model.getFilteredStudentList().get(0))
                .withTags("cs2103", "cs2105").build();

        expectedMessage = "Successfully deleted tags from 1 student";

        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), expectedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_groupSingleTag_success() {
        // Set a tag to student
        HashSet<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("tut1"));

        String groupName = "m1";
        Group group = new Group(Arrays.asList(ALICE, BENSON), groupName);
        model.setGroup(groupName, new Group(group));

        List<Pair<Student, Student>> expectedStudents = group.getList().stream().map(
            student -> new Pair<>(student, new StudentBuilder(student).withTags("tut1").build()))
            .collect(Collectors.toList());
        TagCommand tagCommand = new TagCommand(groupName, tagsToAdd, TagCommandMode.SET);

        String expectedMessage = "Successfully updated tags of 2 students";

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getCalendar(), new UserPrefs());
        expectedStudents.forEach(pair -> expectedModel.updateStudent(pair.getKey(), pair.getValue()));
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Add a tag to student
        tagsToAdd.add(new Tag("tut2"));
        tagCommand = new TagCommand(groupName, tagsToAdd, TagCommandMode.ADD);

        expectedStudents = expectedStudents.stream().map(pair -> new Pair<>(pair.getValue(),
                new StudentBuilder(pair.getValue()).withTags("tut1", "tut2").build()))
                .collect(Collectors.toList());

        expectedMessage = "Successfully added tags to 2 students";

        expectedStudents.forEach(pair -> expectedModel.updateStudent(pair.getKey(), pair.getValue()));
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

        // Remove a tag from a student
        HashSet<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(new Tag("tut1"));
        tagCommand = new TagCommand(groupName, tagsToRemove, TagCommandMode.DEL);

        expectedStudents = expectedStudents.stream().map(pair -> new Pair<>(pair.getValue(),
            new StudentBuilder(pair.getValue()).withTags("tut2").build()))
            .collect(Collectors.toList());

        expectedMessage = "Successfully deleted tags from 2 students";

        expectedStudents.forEach(pair -> expectedModel.updateStudent(pair.getKey(), pair.getValue()));
        expectedModel.commitAddressBook();

        assertCommandSuccess(tagCommand, model, commandHistory, expectedMessage, expectedModel);

    }

    @Test
    public void equals() {
        HashSet<Tag> tagsToAdd = new HashSet<>();
        tagsToAdd.add(new Tag("tut1"));

        HashSet<Tag> sameTags = new HashSet<>();
        sameTags.add(new Tag("tut1"));

        final TagCommand standardAddCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.ADD);
        final TagCommand standardSetCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.SET);
        final TagCommand standardDelCommand = new TagCommand(INDEX_FIRST_STUDENT, tagsToAdd, TagCommandMode.DEL);

        assertTrue(standardAddCommand.equals(standardAddCommand));
        assertTrue(standardSetCommand.equals(standardSetCommand));
        assertTrue(standardDelCommand.equals(standardDelCommand));

        assertFalse(standardAddCommand.equals(null));
        assertFalse(standardSetCommand.equals(null));
        assertFalse(standardDelCommand.equals(null));

        assertFalse(standardAddCommand.equals(new ClearCommand()));
        assertFalse(standardSetCommand.equals(new ClearCommand()));
        assertFalse(standardDelCommand.equals(new ClearCommand()));

        assertFalse(standardAddCommand.equals(standardSetCommand));
        assertFalse(standardSetCommand.equals(standardDelCommand));
        assertFalse(standardDelCommand.equals(standardAddCommand));

        String groupName = "tut1";
        final TagCommand groupAddCommand = new TagCommand(groupName, tagsToAdd, TagCommandMode.ADD);
        final TagCommand groupSetCommand = new TagCommand(groupName, tagsToAdd, TagCommandMode.SET);
        final TagCommand groupDelCommand = new TagCommand(groupName, tagsToAdd, TagCommandMode.DEL);

        final TagCommand similarGroupAddCommand = new TagCommand(groupName, sameTags, TagCommandMode.ADD);
        final TagCommand similarGroupSetCommand = new TagCommand(groupName, sameTags, TagCommandMode.SET);
        final TagCommand similarGroupDelCommand = new TagCommand(groupName, sameTags, TagCommandMode.DEL);

        assertTrue(groupAddCommand.equals(groupAddCommand));
        assertTrue(groupSetCommand.equals(groupSetCommand));
        assertTrue(groupDelCommand.equals(groupDelCommand));

        assertFalse(groupAddCommand.equals(standardAddCommand));
        assertFalse(groupSetCommand.equals(standardSetCommand));
        assertFalse(groupDelCommand.equals(standardDelCommand));

        assertTrue(groupAddCommand.equals(similarGroupAddCommand));
        assertTrue(groupSetCommand.equals(similarGroupSetCommand));
        assertTrue(groupDelCommand.equals(similarGroupDelCommand));



    }




}
