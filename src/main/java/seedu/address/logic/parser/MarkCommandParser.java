package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAGSTATUS;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagStatus;


/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class MarkCommandParser implements Parser<MarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkCommand}
     * and returns a {@code MarkCommand} object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TAGSTATUS);

        Set<Index> indices;
        try {
            indices = StatefulParserUtil.parseIndices(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE), ive);
        }

        if (!StatefulParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG, PREFIX_TAGSTATUS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG, PREFIX_TAGSTATUS);
        String tagNamesString = argMultimap.getValue(PREFIX_TAG).get();
        Set<String> tagNames = StatefulParserUtil.parseTagNamesString(tagNamesString);

        String statusIdentifier = argMultimap.getValue(PREFIX_TAGSTATUS).get();



        // an alternative approach is to instantiate the Tag object and try to
        // catch the Illegal Exception Error. The tag can then be fed into the
        // MarkCommand. The author decided to pass in the tagName instead as the
        // TagName might be used to search for tags in future implementations
        try {
            TagStatus tagStatus = TagStatus.getTagStatus(statusIdentifier);
            tagNames.forEach(Tag::isTagNameValid);
            return new MarkCommand(indices, tagNames, tagStatus);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
