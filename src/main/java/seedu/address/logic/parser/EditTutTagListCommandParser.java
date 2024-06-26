package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.EditTutTagListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditTutTagListCommand object
 */
public class EditTutTagListCommandParser implements Parser<EditTutTagListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTutTagListCommand
     * and returns a EditTutTagListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTutTagListCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTutTagListCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String commandFlag = argMultimap.getPreamble();
        try {
            EditTutTagListCommand.CommandSubtype commandSubtype = StatefulParserUtil.isCreatingNewTag(commandFlag);

            // if the EditTutTagListCommand is to list all available tutorial tags
            if (EditTutTagListCommand.isListCommand(commandSubtype)) {
                return new EditTutTagListCommand(commandSubtype);
            }

            // if the EditTutTagListCommand is not to list all available tutorial tags, PREFIX_TAG must be present
            if (!StatefulParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditTutTagListCommand.MESSAGE_USAGE));
            }

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG);
            String tagName = argMultimap.getValue(PREFIX_TAG).get();
            Tag.isTagNameValid(tagName);
            return new EditTutTagListCommand(tagName, commandSubtype);

        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTutTagListCommand.MESSAGE_USAGE));
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
