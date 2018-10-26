package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.email.EmailDraft;

/**
 * A utility class for EmailDraft.
 */
public class EmailDraftUtil {

    /**
     * Returns an email command string for adding the {@code email}.
     */
    public static String getEmailCommand(EmailDraft emailDraft) {
        return EmailCommand.COMMAND_WORD + " " + getEmailDetails(emailDraft);
    }

    /**
     * Returns the part of command string for the given {@code email}'s details.
     */

    private static String getEmailDetails(EmailDraft emailDraft) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_INDEX + emailDraft.getIndex().toString() + " ");
        sb.append(PREFIX_SUBJECT + emailDraft.getSubject().value + " ");
        sb.append(PREFIX_BODY + emailDraft.getBody().value + " ");

        return sb.toString();
    }

}
