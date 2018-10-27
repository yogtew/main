package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s contains any of the tags given.
 */
public class IsTaggedPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    public IsTaggedPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(tag -> person.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsTaggedPredicate // instanceof handles nulls
                && tags.equals(((IsTaggedPredicate) other).tags)); // state check
    }

}
