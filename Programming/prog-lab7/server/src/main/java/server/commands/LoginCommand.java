package server.commands;

import common.exceptions.DatabaseHandlingException;
import common.exceptions.UserIsNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.DatabaseUserManager;
import server.utility.ResponseOutputer;

/**
 * Command 'login'. Allows the user to login.
 */
public class LoginCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public LoginCommand(DatabaseUserManager databaseUserManager) {
        super("login", "", "���������� �������");
        this.databaseUserManager = databaseUserManager;
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
            if (databaseUserManager.checkUserByUsernameAndPassword(user)) ResponseOutputer.appendln("������������ " +
                    user.getUsername() + " �����������.");
            else throw new UserIsNotFoundException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("�������������: ����...���.��� ���������� �������...");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("���������� �������� ������ �������!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("��������� ������ ��� ��������� � ���� ������!");
        } catch (UserIsNotFoundException exception) {
            ResponseOutputer.appenderror("������������ ��� ������������ ��� ������!");
        }
        return false;
    }
}