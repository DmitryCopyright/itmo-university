package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;
/**
 * Command 'max_by_form_of_education'. Prints the element of the collection with maximum form of education.
 */
public class MaxByFormOfEducationCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public MaxByFormOfEducationCommand(CollectionManager collectionManager) {
        super("max_by_form_of_education", "", "вывести элемент, значение поля formOfEducation которого максимально");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendln(collectionManager.maxByFormOfEducation());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return true;
    }
}