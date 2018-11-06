package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_CONSULTATION;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_TUTORIAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.testutil.TypicalEvents.TUTORIAL;
import static seedu.address.testutil.TypicalEvents.TUTORIAL_NO_DESCRIPTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.email.Body;
import seedu.address.model.email.EmailDraft;
import seedu.address.model.email.Subject;
import seedu.address.model.student.Attendance;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.EmailDraftUtil;
import seedu.address.testutil.EventsUtil;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.StudentUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Student student = new StudentBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudentUtil.getAddCommand(student));
        assertEquals(new AddCommand(student), command);
    }

    @Test
    public void parseCommand_attendance() throws Exception {
        final Attendance attendance = new Attendance("Some attendance.");
        AttendanceCommand command = (AttendanceCommand) parser.parseCommand(AttendanceCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_ATTENDANCE + attendance.value);
        assertEquals(new AttendanceCommand(INDEX_FIRST_STUDENT, attendance), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_STUDENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Student student = new StudentBuilder().build();
        EditCommand.EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder(student).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_STUDENT.getOneBased() + " " + StudentUtil.getEditStudentDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_STUDENT, descriptor), command);
    }

    @Test
    public void parseCommand_email() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(ParserUtil.MESSAGE_INVALID_INDEX);
        Subject subject = new Subject(VALID_SUBJECT_EMAIL);
        Body body = new Body(VALID_BODY_EMAIL);
        EmailDraft emailDraft = new EmailDraft(INDEX_FIRST_STUDENT, subject, body);
        EmailCommand command = (EmailCommand) parser.parseCommand(EmailDraftUtil.getEmailCommand(emailDraft));
        assertEquals(new EmailCommand(emailDraft), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_STUDENT.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_STUDENT), command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " "
                + EVENT_NAME_DESC_TUTORIAL
                + DATE_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
                + DESCRIPTION_DESC_TUTORIAL
        );
        assertEquals(new ScheduleCommand(TUTORIAL), command);
    }

    @Test
    public void parseCommand_cancel() throws Exception {
        CancelCommand command = (CancelCommand) parser.parseCommand(
                CancelCommand.COMMAND_WORD + " "
                + EVENT_NAME_DESC_TUTORIAL
                + DATE_DESC_TUTORIAL
                + START_TIME_DESC_TUTORIAL
                + END_TIME_DESC_TUTORIAL
        );
        assertEquals(new CancelCommand(TUTORIAL_NO_DESCRIPTION), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
