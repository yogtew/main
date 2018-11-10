package seedu.address.testutil;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.UserPrefs;

public class DebugModelManager extends ModelManager {
    public DebugModelManager(ReadOnlyAddressBook addressBook, ReadOnlyCalendar calendar, UserPrefs userPrefs) {
        super(addressBook, calendar, userPrefs);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DebugModelManager)) {
            return false;
        }

        // state check
        DebugModelManager other = (DebugModelManager) obj;
        if (versionedAddressBook.equals(other.versionedAddressBook)) {
            if (filteredStudents.equals(other.filteredStudents)) {
                if (versionedCalendar.equals(other.versionedCalendar)) {
                    if (filteredEvents.equals(other.filteredEvents)) {
                        return true;
                    }
                    System.out.println("Filtered events don't match");
                }
                System.out.println("Calendars don't match");
            }
            System.out.println("Filtered students don't match");
        }
        System.out.println("VersionedABs don't match");
        return false;
    }
}
