package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FACULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FACULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHYSICS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.student.Faculty;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_MATH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + FACULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + FACULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple stuudent numbers - last student number accepted
        assertParseSuccess(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_AMY + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + FACULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + FACULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple faculties - last faculty accepted
        assertParseSuccess(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + FACULTY_DESC_AMY
                + FACULTY_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_MATH, VALID_TAG_PHYSICS)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + FACULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY + FACULTY_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + FACULTY_DESC_BOB,
                expectedMessage);

        // missing student number prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_STUDENT_NUMBER_BOB + EMAIL_DESC_BOB + FACULTY_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + VALID_EMAIL_BOB + FACULTY_DESC_BOB,
                expectedMessage);

        // missing faculty prefix
        assertParseFailure(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + VALID_FACULTY_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_STUDENT_NUMBER_BOB + VALID_EMAIL_BOB + VALID_FACULTY_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + FACULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid student number
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_STUDENT_NUMBER_DESC + EMAIL_DESC_BOB + FACULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, StudentNumber.MESSAGE_STUDENT_NUMBER_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + INVALID_EMAIL_DESC + FACULTY_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid faculty
        assertParseFailure(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + INVALID_FACULTY_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Faculty.MESSAGE_FACULTY_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + FACULTY_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_MATH, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB + INVALID_FACULTY_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + STUDENT_NUMBER_DESC_BOB + EMAIL_DESC_BOB
                + FACULTY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
