package seedu.address.model.student;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * Tests that a {@code Student}'s contains any of the tags given.
 */
public class IsTaggedPredicate implements Predicate<Student> {
    private final Set<Tag> tags;

    public IsTaggedPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    public IsTaggedPredicate(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
