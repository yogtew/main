package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyCalendar;

/**
 * A class to access Calendar data stored as an xml file on the hard disk.
 */
public class XmlCalendarStorage implements CalendarStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private Path filePath;

    public XmlCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCalendarFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {
        return readCalendar(filePath);
    }

    /**
     * Similar to {@link #readCalendar()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Calendar file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableCalendar xmlCalendar = XmlFileStorage.loadCalendarDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlCalendar.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        saveCalendar(calendar, filePath);
    }

    /**
     * Similar to {@link #saveCalendar(ReadOnlyCalendar)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException {
        requireNonNull(calendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveCalendarDataToFile(filePath, new XmlSerializableCalendar(calendar));
    }

}
