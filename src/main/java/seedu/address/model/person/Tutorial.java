package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tutorial group(s) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutorial(String)}
 */
public class Tutorial {

    public static final String MESSAGE_CONSTRAINTS =
            "Tutorial group should be of the form [Day][HH]-[Group number]," +
                    " e.g. Thu11-4.";
    public static final String VALIDATION_REGEX = "(Mon|Tue|Wed|Thu|Fri)(1[0-9]|2[0-3]|0[0-9])(-[1-9]|-10)";
    public final String value;

    /**
     * Constructs a {@code Tutorial}.
     *
     * @param tutorial A valid tutorial group.
     */
    public Tutorial(String tutorial) {
        requireNonNull(tutorial);
        checkArgument(isValidTutorial(tutorial), MESSAGE_CONSTRAINTS);
        value = tutorial;
    }

    /**
     * Returns true if a given string is a valid tutorial.
     */
    public static boolean isValidTutorial(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tutorial)) {
            return false;
        }

        Tutorial otherTutorial = (Tutorial) other;
        return value.equals(otherTutorial.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
