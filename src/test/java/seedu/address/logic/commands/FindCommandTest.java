package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.Messages.MESSAGE_PERSON_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FieldContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FieldContainsKeywordsPredicate[] firstPredicate =
            {new FieldContainsKeywordsPredicate(Collections.singletonList("first"))};
        FieldContainsKeywordsPredicate[] secondPredicate =
            {new FieldContainsKeywordsPredicate(Collections.singletonList("second"))};

        FindCommand findFirstCommand = new FindCommand(Arrays.asList(firstPredicate));
        FindCommand findSecondCommand = new FindCommand(Arrays.asList(secondPredicate));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(Arrays.asList(firstPredicate));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSON_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate[] predicate = {preparePredicate(" ")};
        FindCommand command = new FindCommand(Arrays.asList(predicate));
        expectedModel.updateFilteredPersonList(predicate[0]);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate[] predicate = {preparePredicate("Kurz Elle Kunz")};
        FindCommand command = new FindCommand(Arrays.asList(predicate));
        expectedModel.updateFilteredPersonList(predicate[0]);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FieldContainsKeywordsPredicate[] predicate = {new FieldContainsKeywordsPredicate(Arrays.asList("keyword"))};
        FindCommand findCommand = new FindCommand(Arrays.asList(predicate));
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate[0] + "}";

        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FieldContainsKeywordsPredicate preparePredicate(String userInput) {
        return new FieldContainsKeywordsPredicate(PREFIX_NAME, Arrays.asList(userInput.split("\\s+")));
    }
}
