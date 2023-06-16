package server.commands;


import common.data.StudyGroup;
import common.exceptions.DatabaseHandlingException;
import common.exceptions.ManualDatabaseEditException;
import common.exceptions.PermissionDeniedException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.App;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public ClearCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("clear", "", "�������� ���������");
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
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            for (StudyGroup studyGroup : collectionManager.getCollection()) {
                if (!studyGroup.getOwner().equals(user)) throw new PermissionDeniedException();
                if (!databaseCollectionManager.checkGroupUserId(studyGroup.getId(), user)) throw new ManualDatabaseEditException();
            }

            collectionManager.clearCollection();
            ResponseOutputer.appendln("��������� �������!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("�������������: '" + getName() + " " + getUsage() + "'");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("��������� ������ ��� ��������� � ���� ������!");
            App.logger.error(exception.toString() + "\n" + exception.getMessage());
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("������������ ���� ��� ���������� ������ �������!");
            ResponseOutputer.appendln("������������� ������ ������������� ������� �������� ������ ��� ������.");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("��������� ������ ��������� ���� ������!");
            ResponseOutputer.appendln("������������� ������ ��� ��������� ��������� ������.");
        }
        return false;
    }
}