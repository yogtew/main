package seedu.address.model.student;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Student}'s contains any of the tags given.
 */
public class IsTaggedPredicate implements Predicate<Student> {
    private final List<Tag> tags;

    public IsTaggedPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Student student) {
        return tags.stream()
                .anyMatch(tag -> student.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsTaggedPredicate // instanceof handles nulls
                && tags.equals(((IsTaggedPredicate) other).tags)); // state check
    }

}
