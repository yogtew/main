package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableCalendar;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path ADDRESSBOOK_SAVE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleAddressBookData.xml");
    public static final Path CALENDAR_SAVE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleCalendarData.xml");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyAddressBook> initialStudentDataSupplier = () -> null;
    protected Supplier<ReadOnlyCalendar> initialEventDataSupplier = () -> null;
    protected Path saveAddressBookFileLocation = ADDRESSBOOK_SAVE_LOCATION_FOR_TESTING;
    protected Path saveCalendarFileLocation = CALENDAR_SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialStudentDataSupplier,
                   Supplier<ReadOnlyCalendar> initialEventDataSupplier,
                   Path saveAddressBookFileLocation,
                   Path saveCalendarFileLocation) {
        super();
        this.initialStudentDataSupplier = initialStudentDataSupplier;
        this.initialEventDataSupplier = initialEventDataSupplier;
        this.saveAddressBookFileLocation = saveAddressBookFileLocation;
        this.saveCalendarFileLocation = saveCalendarFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialStudentDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialStudentDataSupplier.get()),
                    this.saveAddressBookFileLocation);
        }
        if (initialEventDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableCalendar(this.initialEventDataSupplier.get()),
                    this.saveCalendarFileLocation);
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(saveAddressBookFileLocation);
        userPrefs.setCalendarFilePath(saveCalendarFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the calendar data stored inside the storage file.
     */
    public Calendar readStorageCalendar() {
        try {
            return new Calendar(storage.readCalendar().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Calendar format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the address book storage file.
     */
    public Path getAddressBookStorageSaveLocation() {
        return storage.getAddressBookFilePath();
    }


    /**
     * Returns the file path of the address book storage file.
     */
    public Path getCalendarStorageSaveLocation() {
        return storage.getCalendarFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager(model.getAddressBook(), model.getCalendar(), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredStudentList(), model.getFilteredEventList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
