package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.email.Body;
import seedu.address.model.email.EmailDraft;
import seedu.address.model.email.Subject;

public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Subject subjectStub = new Subject("subject");
        Body bodyStub = new Body("body");
        EmailDraft emailDraft = new EmailDraft(outOfBoundIndex, subjectStub, bodyStub);
        EmailCommand emailCommand = new EmailCommand(emailDraft);
        assertCommandFailure(emailCommand, model, commandHistory,
                                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
