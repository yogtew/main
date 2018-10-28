package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BODY_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_EMAIL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.email.Body.MESSAGE_BODY_CONSTRAINTS;
import static seedu.address.model.email.Subject.MESSAGE_SUBJECT_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.Body;
import seedu.address.model.email.EmailDraft;
import seedu.address.model.email.Subject;

public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        Subject subject = new Subject(VALID_SUBJECT_EMAIL);
        Body body = new Body(VALID_BODY_EMAIL);
        EmailDraft emailDraft = new EmailDraft(INDEX_FIRST_PERSON, subject, body);

        assertParseSuccess(parser, INDEX_DESC_AMY + SUBJECT_DESC + BODY_DESC, new EmailCommand(emailDraft));
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedSubjectMessage = String.format(MESSAGE_SUBJECT_CONSTRAINTS, EmailCommand.MESSAGE_USAGE);
        String expectedIndexMessage = String.format(MESSAGE_INVALID_INDEX, EmailCommand.MESSAGE_USAGE);
        String expectedBodyMessage = String.format(MESSAGE_BODY_CONSTRAINTS, EmailCommand.MESSAGE_USAGE);
        //invalid index
        assertParseFailure(parser, INVALID_INDEX_DESC + SUBJECT_DESC + BODY_DESC, expectedIndexMessage);

        //invalid subject
        assertParseFailure(parser, INDEX_DESC_AMY + INVALID_SUBJECT_DESC + BODY_DESC, expectedSubjectMessage);

        //invalid body
        assertParseFailure(parser, INDEX_DESC_AMY + SUBJECT_DESC + INVALID_BODY_DESC, expectedBodyMessage);
    }
}
