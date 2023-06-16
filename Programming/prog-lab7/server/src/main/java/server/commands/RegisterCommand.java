package server.commands;

import common.exceptions.DatabaseHandlingException;
import common.exceptions.UserAlreadyExists;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.DatabaseUserManager;
import server.utility.ResponseOutputer;

/**
 * Command 'register'. Allows the user to register.
 */
public class RegisterCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public RegisterCommand(DatabaseUserManager databaseUserManager) {
        super("register", "", "���������� �������");
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
            if (databaseUserManager.insertUser(user)) ResponseOutputer.appendln("������������ " +
                    user.getUsername() + " ���������������.");
            else throw new UserAlreadyExists();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("�������������: ����...���.��� ���������� �������...");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("���������� �������� ������ �������!");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("��������� ������ ��� ��������� � ���� ������!");
        } catch (UserAlreadyExists exception) {
            ResponseOutputer.appenderror("������������ " + user.getUsername() + " ��� ����������!");
        }
        return false;
    }
}