package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.GroupTestUtil.A;
import static seedu.address.testutil.GroupTestUtil.B;
import static seedu.address.testutil.GroupTestUtil.C;
import static seedu.address.testutil.GroupTestUtil.D;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.group.GroupAndCommand;
import seedu.address.logic.commands.group.GroupFindCommand;
import seedu.address.logic.commands.group.GroupJoinCommand;
import seedu.address.logic.commands.group.GroupShowCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.GroupTestUtil;

/**
 * Tests the function of each subcommand (find, and, join)
 * "show" isn't tested as it merely updates the GUI
 */
public class GroupCommandTest {

    private Model model = new ModelManager(
            GroupTestUtil.getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private String groupName1 = "tut1";
    private String groupName2 = "tut2";
    private String groupName3 = "cs2103";
    private String tempGroupName = "temp";
    private Group groupTut1 = new Group(Arrays.asList(A, B), groupName1);
    private Group groupTut2 = new Group(Arrays.asList(C, D), groupName2);
    private Group groupClass = new Group(Arrays.asList(A, B, C, D), groupName3);

    @Test
    public void show() {
        // ensures that the displayed list is the same
        GroupShowCommand command = new GroupShowCommand(groupName1);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        String expectedMessage = String.format(GroupShowCommand.MESSAGE_SUCCESS, 2, groupName1);
        model.setGroup(groupName1, groupTut1);
        expectedModel.setGroup(groupName1, groupTut1);
        expectedModel.updateFilteredStudentList(groupTut1.getPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assert(model.getFilteredStudentList().equals(Arrays.asList(A, B)));
    }

    @Test
    public void find() {
        // empty
        GroupFindCommand command = new GroupFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("randomTag")), tempGroupName);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        String expectedMessage = String.format(GroupFindCommand.MESSAGE_SUCCESS, 0, tempGroupName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // tut1
        command = new GroupFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("tut1")), groupName1);
        expectedModel.setGroup(groupName1, groupTut1);
        expectedMessage = String.format(GroupFindCommand.MESSAGE_SUCCESS, 2, groupName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // tut2
        command = new GroupFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("tut2")), groupName2);
        expectedModel.setGroup(groupName2, groupTut2);
        expectedMessage = String.format(GroupFindCommand.MESSAGE_SUCCESS, 2, groupName2);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // class
        command = new GroupFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("cs2103")), groupName3);
        expectedModel.setGroup(groupName3, groupClass);
        expectedMessage = String.format(GroupFindCommand.MESSAGE_SUCCESS, 4, groupName3);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void and() {
        // all 3 empty groups
        GroupAndCommand command = new GroupAndCommand(Group.DEFAULT_NAME, Group.DEFAULT_NAME, Group.DEFAULT_NAME);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setGroup(groupName1, Group.getEmpty());
        String expectedMessage = String.format(GroupAndCommand.MESSAGE_SUCCESS, 0,
                Group.DEFAULT_NAME, Group.DEFAULT_NAME, Group.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // setup groups
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setGroup(groupName1, groupTut1);
        expectedModel.setGroup(groupName2, groupTut2);
        expectedModel.setGroup(groupName3, Group.getEmpty());

        model.setGroup(groupName1, groupTut1);
        model.setGroup(groupName2, groupTut2);

        //  group g/m1 and g/m1 g/m1, m1 self intersect is still m1
        command = new GroupAndCommand(groupName1, groupName1, groupName1);
        expectedMessage = String.format(GroupAndCommand.MESSAGE_SUCCESS, 2, groupName1, groupName1, groupName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        //  group g/default and g/m1 g/default, m1 intersect default should be still m1
        command = new GroupAndCommand(Group.DEFAULT_NAME, groupName1, Group.DEFAULT_NAME);
        expectedModel.setGroup(Group.DEFAULT_NAME, groupTut1);
        model.setGroup(Group.DEFAULT_NAME, groupTut1);
        expectedMessage = String.format(GroupAndCommand.MESSAGE_SUCCESS,
                2, Group.DEFAULT_NAME, groupName1, Group.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // A, B intersect A, C should be A
        Group groupM = new Group(Arrays.asList(A, C), groupName2);
        Group expectedGroup = new Group(Arrays.asList(A), groupName2);
        String groupMName = "m";
        String expectedGroupName = "MAndTut1";

        model.setGroup(groupMName, groupM);
        expectedModel.setGroup(groupMName, groupM);
        expectedModel.setGroup(expectedGroupName, expectedGroup);

        command = new GroupAndCommand(expectedGroupName, groupName1, groupMName);
        expectedMessage = String.format(GroupAndCommand.MESSAGE_SUCCESS, 1, expectedGroupName, groupName1, groupMName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void join() {
        // all 3 empty groups
        GroupJoinCommand command = new GroupJoinCommand(Group.DEFAULT_NAME, Group.DEFAULT_NAME, Group.DEFAULT_NAME);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setGroup(groupName1, Group.getEmpty());
        String expectedMessage = String.format(GroupJoinCommand.MESSAGE_SUCCESS,
                0, Group.DEFAULT_NAME, Group.DEFAULT_NAME, Group.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // setup groups
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setGroup(groupName1, groupTut1);
        expectedModel.setGroup(groupName2, groupTut2);

        model.setGroup(groupName1, groupTut1);
        model.setGroup(groupName2, groupTut2);

        expectedModel.setGroup(groupName3, groupClass);

        //  group g/m1 join g/m1 g/m1, m1 self join is still m1
        command = new GroupJoinCommand(groupName1, groupName1, groupName1);
        expectedMessage = String.format(GroupJoinCommand.MESSAGE_SUCCESS, 2, groupName1, groupName1, groupName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        //  group g/default and g/m1 g/default, m1 intersect default should be still m1
        command = new GroupJoinCommand(Group.DEFAULT_NAME, groupName1, Group.DEFAULT_NAME);
        expectedModel.setGroup(Group.DEFAULT_NAME, groupTut1);
        model.setGroup(Group.DEFAULT_NAME, groupTut1);
        expectedMessage = String.format(GroupJoinCommand.MESSAGE_SUCCESS,
                2, Group.DEFAULT_NAME, groupName1, Group.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // A, B intersect A, C should be A, B, C
        Group groupM = new Group(Arrays.asList(A, C), groupName2);
        Group expectedGroup = new Group(Arrays.asList(A, B, C), groupName2);
        String groupMName = "m";
        String expectedGroupName = "MJoinTut1";

        model.setGroup(groupMName, groupM);
        expectedModel.setGroup(groupMName, groupM);
        expectedModel.setGroup(expectedGroupName, expectedGroup);

        command = new GroupJoinCommand(expectedGroupName, groupName1, groupMName);
        expectedMessage = String.format(GroupJoinCommand.MESSAGE_SUCCESS, 3, expectedGroupName, groupName1, groupMName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final GroupFindCommand standardFindCommand = new GroupFindCommand(
                new IsTaggedPredicate("tut1"), Group.DEFAULT_NAME);
        final GroupJoinCommand standardJoinCommand = new GroupJoinCommand(Group.DEFAULT_NAME, groupName1, groupName2);
        final GroupAndCommand standardAndCommand = new GroupAndCommand(Group.DEFAULT_NAME, groupName1, groupName2);

        // same values -> returns true
        assertTrue(standardFindCommand.equals(new GroupFindCommand(
                new IsTaggedPredicate("tut1"), Group.DEFAULT_NAME)));
        assertTrue(standardJoinCommand.equals(new GroupJoinCommand(Group.DEFAULT_NAME, groupName1, groupName2)));
        assertTrue(standardAndCommand.equals(new GroupAndCommand(Group.DEFAULT_NAME, groupName1, groupName2)));

        // same object -> returns true
        assertTrue(standardFindCommand.equals(standardFindCommand));
        assertTrue(standardJoinCommand.equals(standardJoinCommand));
        assertTrue(standardAndCommand.equals(standardAndCommand));

        // null -> returns false
        assertFalse(standardFindCommand.equals(null));
        assertFalse(standardJoinCommand.equals(null));
        assertFalse(standardAndCommand.equals(null));

        // different types -> returns false
        assertFalse(standardFindCommand.equals(new ClearCommand()));
        assertFalse(standardJoinCommand.equals(new ClearCommand()));
        assertFalse(standardAndCommand.equals(new ClearCommand()));

        // different values -> returns false
        assertFalse(standardFindCommand.equals(new GroupFindCommand(
                new IsTaggedPredicate("tut1"), "asd")));
        assertFalse(standardFindCommand.equals(new GroupFindCommand(
                new IsTaggedPredicate("asd"), Group.DEFAULT_NAME)));

        assertFalse(standardJoinCommand.equals(new GroupJoinCommand("asd", groupName1, groupName2)));
        assertFalse(standardJoinCommand.equals(new GroupJoinCommand(Group.DEFAULT_NAME, "asd", groupName2)));
        assertFalse(standardJoinCommand.equals(new GroupJoinCommand(Group.DEFAULT_NAME, groupName1, "asd")));

        assertFalse(standardAndCommand.equals(new GroupAndCommand("asd", groupName1, groupName2)));
        assertFalse(standardAndCommand.equals(new GroupAndCommand(Group.DEFAULT_NAME, "asd", groupName2)));
        assertFalse(standardAndCommand.equals(new GroupAndCommand(Group.DEFAULT_NAME, groupName1, "asd")));
    }

}
