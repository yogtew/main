package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.MarkTestUtil.A;
import static seedu.address.testutil.MarkTestUtil.B;
import static seedu.address.testutil.MarkTestUtil.C;
import static seedu.address.testutil.MarkTestUtil.D;
import static seedu.address.testutil.TypicalEvents.getTypicalCalendar;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.mark.MarkAndCommand;
import seedu.address.logic.commands.mark.MarkFindCommand;
import seedu.address.logic.commands.mark.MarkJoinCommand;
import seedu.address.logic.commands.mark.MarkShowCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.mark.Mark;
import seedu.address.model.student.IsTaggedPredicate;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.DebugModelManager;
import seedu.address.testutil.MarkTestUtil;

/**
 * Tests the function of each subcommand (find, and, join)
 * "show" isn't tested as it merely updates the GUI
 */
public class MarkCommandTest {

    private Model model = new DebugModelManager(
            MarkTestUtil.getTypicalAddressBook(), getTypicalCalendar(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private String markName1 = "tut1";
    private String markName2 = "tut2";
    private String markName3 = "cs2103";
    private String tempMarkName = "temp";
    private Mark markTut1 = new Mark(Arrays.asList(A, B), markName1);
    private Mark markTut2 = new Mark(Arrays.asList(C, D), markName2);
    private Mark markClass = new Mark(Arrays.asList(A, B, C, D), markName3);

    @Test
    public void show() {
        // ensures that the displayed list is the same
        MarkShowCommand command = new MarkShowCommand(markName1);
        Model expectedModel = new DebugModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        String expectedMessage = String.format(MarkShowCommand.MESSAGE_SUCCESS, 2, markName1);
        model.setMark(markName1, markTut1);
        expectedModel.setMark(markName1, markTut1);
        expectedModel.updateFilteredStudentList(markTut1.getPredicate());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assert(model.getFilteredStudentList().equals(Arrays.asList(A, B)));
    }

    @Test
    public void find() {
        // empty
        MarkFindCommand command = new MarkFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("randomTag")), tempMarkName);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        String expectedMessage = String.format(MarkFindCommand.MESSAGE_SUCCESS, 0, tempMarkName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // tut1
        command = new MarkFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("tut1")), markName1);
        expectedModel.setMark(markName1, markTut1);
        expectedMessage = String.format(MarkFindCommand.MESSAGE_SUCCESS, 2, markName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // tut2
        command = new MarkFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("tut2")), markName2);
        expectedModel.setMark(markName2, markTut2);
        expectedMessage = String.format(MarkFindCommand.MESSAGE_SUCCESS, 2, markName2);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // class
        command = new MarkFindCommand(
                new IsTaggedPredicate(SampleDataUtil.getTagSet("cs2103")), markName3);
        expectedModel.setMark(markName3, markClass);
        expectedMessage = String.format(MarkFindCommand.MESSAGE_SUCCESS, 4, markName3);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void and() {
        // all 3 empty marks
        MarkAndCommand command = new MarkAndCommand(Mark.DEFAULT_NAME, Mark.DEFAULT_NAME, Mark.DEFAULT_NAME);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setMark(markName1, Mark.getEmpty());
        String expectedMessage = String.format(MarkAndCommand.MESSAGE_SUCCESS, 0,
                Mark.DEFAULT_NAME, Mark.DEFAULT_NAME, Mark.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // setup marks
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setMark(markName1, markTut1);
        expectedModel.setMark(markName2, markTut2);
        expectedModel.setMark(markName3, Mark.getEmpty());

        model.setMark(markName1, markTut1);
        model.setMark(markName2, markTut2);

        //  mark m/m1 and m/m1 m/m1, m1 self intersect is still m1
        command = new MarkAndCommand(markName1, markName1, markName1);
        expectedMessage = String.format(MarkAndCommand.MESSAGE_SUCCESS, 2, markName1, markName1, markName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        //  mark m/default and m/m1 m/default, m1 intersect default should be still m1
        command = new MarkAndCommand(Mark.DEFAULT_NAME, markName1, Mark.DEFAULT_NAME);
        expectedModel.setMark(Mark.DEFAULT_NAME, markTut1);
        model.setMark(Mark.DEFAULT_NAME, markTut1);
        expectedMessage = String.format(MarkAndCommand.MESSAGE_SUCCESS,
                2, Mark.DEFAULT_NAME, markName1, Mark.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // A, B intersect A, C should be A
        Mark markM = new Mark(Arrays.asList(A, C), markName2);
        Mark expectedMark = new Mark(Arrays.asList(A), markName2);
        String markMName = "m";
        String expectedMarkName = "MAndTut1";

        model.setMark(markMName, markM);
        expectedModel.setMark(markMName, markM);
        expectedModel.setMark(expectedMarkName, expectedMark);

        command = new MarkAndCommand(expectedMarkName, markName1, markMName);
        expectedMessage = String.format(MarkAndCommand.MESSAGE_SUCCESS, 1, expectedMarkName, markName1, markMName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void join() {
        // all 3 empty marks
        MarkJoinCommand command = new MarkJoinCommand(Mark.DEFAULT_NAME, Mark.DEFAULT_NAME, Mark.DEFAULT_NAME);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setMark(markName1, Mark.getEmpty());
        String expectedMessage = String.format(MarkJoinCommand.MESSAGE_SUCCESS,
                0, Mark.DEFAULT_NAME, Mark.DEFAULT_NAME, Mark.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // setup marks
        expectedModel = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        expectedModel.setMark(markName1, markTut1);
        expectedModel.setMark(markName2, markTut2);

        model.setMark(markName1, markTut1);
        model.setMark(markName2, markTut2);

        expectedModel.setMark(markName3, markClass);

        //  mark m/m1 join m/m1 m/m1, m1 self join is still m1
        command = new MarkJoinCommand(markName1, markName1, markName1);
        expectedMessage = String.format(MarkJoinCommand.MESSAGE_SUCCESS, 2, markName1, markName1, markName1);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        //  mark m/default and m/m1 m/default, m1 intersect default should be still m1
        command = new MarkJoinCommand(Mark.DEFAULT_NAME, markName1, Mark.DEFAULT_NAME);
        expectedModel.setMark(Mark.DEFAULT_NAME, markTut1);
        model.setMark(Mark.DEFAULT_NAME, markTut1);
        expectedMessage = String.format(MarkJoinCommand.MESSAGE_SUCCESS,
                2, Mark.DEFAULT_NAME, markName1, Mark.DEFAULT_NAME);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);

        // A, B intersect A, C should be A, B, C
        Mark markM = new Mark(Arrays.asList(A, C), markName2);
        Mark expectedMark = new Mark(Arrays.asList(A, B, C), markName2);
        String markMName = "m";
        String expectedMarkName = "MJoinTut1";

        model.setMark(markMName, markM);
        expectedModel.setMark(markMName, markM);
        expectedModel.setMark(expectedMarkName, expectedMark);

        command = new MarkJoinCommand(expectedMarkName, markName1, markMName);
        expectedMessage = String.format(MarkJoinCommand.MESSAGE_SUCCESS, 3, expectedMarkName, markName1, markMName);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final MarkFindCommand standardFindCommand = new MarkFindCommand(
                new IsTaggedPredicate("tut1"), Mark.DEFAULT_NAME);
        final MarkJoinCommand standardJoinCommand = new MarkJoinCommand(Mark.DEFAULT_NAME, markName1, markName2);
        final MarkAndCommand standardAndCommand = new MarkAndCommand(Mark.DEFAULT_NAME, markName1, markName2);

        // same values -> returns true
        assertTrue(standardFindCommand.equals(new MarkFindCommand(
                new IsTaggedPredicate("tut1"), Mark.DEFAULT_NAME)));
        assertTrue(standardJoinCommand.equals(new MarkJoinCommand(Mark.DEFAULT_NAME, markName1, markName2)));
        assertTrue(standardAndCommand.equals(new MarkAndCommand(Mark.DEFAULT_NAME, markName1, markName2)));

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
        assertFalse(standardFindCommand.equals(new MarkFindCommand(
                new IsTaggedPredicate("tut1"), "asd")));
        assertFalse(standardFindCommand.equals(new MarkFindCommand(
                new IsTaggedPredicate("asd"), Mark.DEFAULT_NAME)));

        assertFalse(standardJoinCommand.equals(new MarkJoinCommand("asd", markName1, markName2)));
        assertFalse(standardJoinCommand.equals(new MarkJoinCommand(Mark.DEFAULT_NAME, "asd", markName2)));
        assertFalse(standardJoinCommand.equals(new MarkJoinCommand(Mark.DEFAULT_NAME, markName1, "asd")));

        assertFalse(standardAndCommand.equals(new MarkAndCommand("asd", markName1, markName2)));
        assertFalse(standardAndCommand.equals(new MarkAndCommand(Mark.DEFAULT_NAME, "asd", markName2)));
        assertFalse(standardAndCommand.equals(new MarkAndCommand(Mark.DEFAULT_NAME, markName1, "asd")));
    }

}
