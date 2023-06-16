package server.commands;

import common.data.StudyGroup;
import common.exceptions.*;
import common.interaction.GroupRaw;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.DatabaseHandler;
import server.utility.ResponseOutputer;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.ResultSet;

/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_greater", "{element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            GroupRaw groupRaw = (GroupRaw) objectArgument;


            StudyGroup groupToFind = new StudyGroup (
                   0L,
                    groupRaw.getName(),
                    groupRaw.getCoordinates(),
                    LocalDateTime.now(),
                    groupRaw.getStudentCount(),
                    groupRaw.getExpelledStudent(),
                    groupRaw.getAverageMark(),
                    groupRaw.getFormOfEducation(),
                    groupRaw.getPerson(),
                    user
            );
            StudyGroup groupFromCollection = collectionManager.getByValue(groupToFind);
            if (groupFromCollection == null) throw new GroupNotFoundException();
            for (StudyGroup studyGroup : collectionManager.getGreater(groupFromCollection)) {
                if (!studyGroup.getOwner().equals(user)) throw new PermissionDeniedException();
                if (!databaseCollectionManager.checkGroupUserId(studyGroup.getId(), user)) throw new ManualDatabaseEditException();
            }
            for (StudyGroup studyGroup : collectionManager.getGreater(groupFromCollection)) {
                databaseCollectionManager.deleteGroupById(studyGroup.getId());
                collectionManager.removeFromCollection(studyGroup);
            }
            ResponseOutputer.appendln("Группы успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (GroupNotFoundException exception) {
            ResponseOutputer.appenderror("Группы с такими характеристиками в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("Произошла ошибка при обращении к базе данных!");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("Недостаточно прав для выполнения данной команды!");
            ResponseOutputer.appendln("Принадлежащие другим пользователям объекты доступны только для чтения.");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("Произошло прямое изменение базы данных!");
            ResponseOutputer.appendln("Перезапустите клиент для избежания возможных ошибок.");
        }
        return false;
    }
}