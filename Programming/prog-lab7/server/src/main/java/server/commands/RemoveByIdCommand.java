package server.commands;

import common.data.StudyGroup;
import common.exceptions.*;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_by_id", "<ID>", "������� ������� �� ��������� �� ID");
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
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            long id = Long.parseLong(stringArgument);
            StudyGroup groupToRemove = collectionManager.getById(id);
            if (groupToRemove == null) throw new GroupNotFoundException();
            if (!groupToRemove.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkGroupUserId(groupToRemove.getId(), user)) throw new ManualDatabaseEditException();
            databaseCollectionManager.deleteGroupById(id);
            collectionManager.removeFromCollection(groupToRemove);
            ResponseOutputer.appendln("������ ������� �������!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("�������������: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("��������� �����!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID ������ ���� ����������� ������!");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("������ � ����� ID � ��������� ���!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("��������� ������ ��� ��������� � ���� ������!");
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