package server.commands;

import common.exceptions.DatabaseHandlingException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.GroupRaw;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'add'. Adds a new element to collection.
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add", "{element}", "�������� ����� ������� � ���������");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            GroupRaw groupRaw = (GroupRaw) objectArgument;
            collectionManager.addToCollection(databaseCollectionManager.insertGroup(groupRaw, user));
            ResponseOutputer.appendln("������ ������� ���������!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("�������������: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("���������� �������� ������ �������!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("��������� ������ ��� ��������� � ���� ������!");
        }
        return false;
    }
}