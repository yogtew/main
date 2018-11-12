package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CONSULTATION;
import static seedu.address.testutil.TypicalEvents.CONSULTATION;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalEvents.TUTORIAL_NO_DESCRIPTION;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;

public class ScheduleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void schedule() {
        Model model = getModel();

        /* Case: scheduling a tutorial -> tutorial scheduled*/
        Event toSchedule = TUTORIAL;
        String command =
                "   " + ScheduleCommand.COMMAND_WORD
                + "  " + EVENT_NAME_DESC_TUTORIAL
                + "  " + DATE_DESC_TUTORIAL
                + "  " + START_TIME_DESC_TUTORIAL
                + "  " + END_TIME_DESC_TUTORIAL
                + "  " + DESCRIPTION_DESC_TUTORIAL + " ";

        assertCommandSuccess(command, toSchedule);

        /* Case: undo scheduling the tutorial to the list -> tutorial deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding the tutorial to the list -> tutorial added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toSchedule);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: schedule an event with all fields same as another event in the calendar
         * except event name -> added
         */
        toSchedule = new EventBuilder(TUTORIAL).withEventName(VALID_EVENT_NAME_CONSULTATION).build();
        command = ScheduleCommand.COMMAND_WORD
                + EVENT_NAME_DESC_CONSULTATION
                + DATE_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL;
        assertCommandSuccess(command, toSchedule);

        /* Case: schedule an event with parameters in random order -> added */
        toSchedule = CONSULTATION;
        command = ScheduleCommand.COMMAND_WORD
                + START_TIME_DESC_CONSULTATION
                + DATE_DESC_CONSULTATION
                + DESCRIPTION_DESC_CONSULTATION
                + EVENT_NAME_DESC_CONSULTATION
                + END_TIME_DESC_CONSULTATION;
        assertCommandSuccess(command, toSchedule);

        /* ----------------------------------------- Perform invalid schedule operations ---------------------------- */

        /* Case: schedule a duplicate event -> rejected */
        command = EventUtil.getScheduleCommand(CONSULTATION);
        assertCommandFailure(command, ScheduleCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: schedule an event with all parameters except description being the same -> rejected */
        command = EventUtil.getScheduleCommand(TUTORIAL_NO_DESCRIPTION);
        assertCommandFailure(command, ScheduleCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: missing event name -> rejected */
        command = ScheduleCommand.COMMAND_WORD
                + DATE_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = ScheduleCommand.COMMAND_WORD
                + EVENT_NAME_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        /* Case: missing start time -> rejected */
        command = ScheduleCommand.COMMAND_WORD
                + EVENT_NAME_DESC_TUTORIAL
                + DATE_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        /* Case: missing end time -> rejected */
        command = ScheduleCommand.COMMAND_WORD
                + EVENT_NAME_DESC_TUTORIAL
                + DATE_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

    }

    /**
     * Executes the {@code ScheduleCommand} that adds {@code toSchedule} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ScheduleCommand} with the details of
     * {@code toSchedule}.<br>
     * 4. {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * the current model added with {@code toSchedule}.<br>
     * 5. Student list panel remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Event toSchedule) {
        assertCommandSuccess(EventUtil.getScheduleCommand(toSchedule), toSchedule);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(Event)}. Executes {@code command}
     * instead.
     * @see ScheduleCommandSystemTest#assertCommandSuccess(Event)
     */
    private void assertCommandSuccess(String command, Event toSchedule) {
        Model expectedModel = getModel();
        expectedModel.addEvent(toSchedule);
        String expectedResultMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS, toSchedule);


        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code EventtListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ScheduleCommandSystemTest#assertCommandSuccess(String, Event)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedStudentCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EventListPanel} remain unchanged.<br>
     * 5. Student list panel, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedStudentCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
