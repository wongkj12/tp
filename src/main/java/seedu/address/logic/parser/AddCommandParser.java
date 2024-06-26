package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.HashSet;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ID, PREFIX_PHONE,
                        PREFIX_EMAIL);

        if (!StatefulParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ID, PREFIX_PHONE,
                PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ID, PREFIX_PHONE, PREFIX_EMAIL);
        PersonType type = StatefulParserUtil.parsePersonType(argMultimap.getPreamble());
        Name name = StatefulParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Id id = StatefulParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());
        Phone phone = StatefulParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = StatefulParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());

        Person person = Person.of(type, name, id, phone, email, new HashSet<Tag>());

        return new AddCommand(person);
    }

}
