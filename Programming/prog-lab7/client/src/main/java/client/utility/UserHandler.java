package client.utility;

import client.AppClient;
import common.data.*;
import common.exceptions.CommandUsageException;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.interaction.GroupRaw;
import common.interaction.Request;
import common.interaction.ResponseCode;
import common.interaction.User;
import common.utility.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Receives user requests.
 */
public class UserHandler {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseCode Last server's response code.
     * @param user               User object.
     * @return New request to server.
     */
    public Request handle(ResponseCode serverResponseCode, User user) {
        String userInput;
        String[] userCommand;
        ProcessingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.ERROR ||
                            serverResponseCode == ResponseCode.SERVER_EXIT))
                        throw new IncorrectInputInScriptException();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        scriptStack.pop();
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            Outputer.print(AppClient.PS1);
                            Outputer.println(userInput);
                        }
                    } else {
                        Outputer.print(AppClient.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println();
                    Outputer.printerror("��������� ������ ��� ����� �������!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        Outputer.printerror("��������� ���������� ������� �����!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == ResponseCode.ERROR || processingCode == ProcessingCode.ERROR))
                    throw new IncorrectInputInScriptException();
                switch (processingCode) {
                    case OBJECT:
                        GroupRaw groupAddRaw = generateGroupAdd();
                        return new Request(userCommand[0], userCommand[1], groupAddRaw, user);
                    case UPDATE_OBJECT:
                        GroupRaw groupUpdateRaw = generateGroupUpdate();
                        return new Request(userCommand[0], userCommand[1], groupUpdateRaw, user);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        Outputer.println("�������� ������ '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                Outputer.printerror("���� �� �������� �� ������!");
            } catch (ScriptRecursionException exception) {
                Outputer.printerror("������� �� ����� ���������� ����������!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            Outputer.printerror("���������� ������� ��������!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request(user);
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID> {element}");
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<ID>");
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException("<file_name>");
                    return ProcessingCode.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add_if_min":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "remove_greater":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException("{element}");
                    return ProcessingCode.OBJECT;
                case "history":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "sum_of_students_count":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "max_by_form_of_education":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "server_exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                default:
                    Outputer.println("������� '" + command + "' �� �������. �������� 'help' ��� �������.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            Outputer.println("�������������: '" + command + "'");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    /**
     * Generates group to add.
     *
     * @return Group to add.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private GroupRaw generateGroupAdd() throws IncorrectInputInScriptException {
        StudyGroupAsker studyGroupAsker = new StudyGroupAsker(userScanner);
        if (fileMode()) studyGroupAsker.setFileMode();
        return new GroupRaw(
                studyGroupAsker.askName(),
                studyGroupAsker.askCoordinates(),
                studyGroupAsker.askStudentsCount(),
                studyGroupAsker.askExpelledStudents(),
                studyGroupAsker.askAverageMark(),
                studyGroupAsker.askFormOfEducation(),
                studyGroupAsker.askPerson()
        );
    }

    /**
     * Generates group to update.
     *
     * @return Groups to update.
     * @throws IncorrectInputInScriptException When something went wrong in script.
     */
    private GroupRaw generateGroupUpdate() throws IncorrectInputInScriptException {
        StudyGroupAsker studyGroupAsker = new StudyGroupAsker(userScanner);
        if (fileMode()) studyGroupAsker.setFileMode();
        String name = studyGroupAsker.askQuestion("������ �������� �������� ������?") ?
                studyGroupAsker.askName() : null;
        Coordinates coordinates = studyGroupAsker.askQuestion("������ �������� ����������?") ?
                studyGroupAsker.askCoordinates() : null;
        int studentsCount = studyGroupAsker.askQuestion("������ �������� ���������� ���������?") ?
                studyGroupAsker.askStudentsCount() : -1;
        int expelledStudents = studyGroupAsker.askQuestion("������ �������� ���������� ����������� ���������?") ?
                studyGroupAsker.askExpelledStudents() : -1;
        int averageMark = studyGroupAsker.askQuestion("������ �������� ������� ����?") ?
                studyGroupAsker.askAverageMark() : -1;
        FormOfEducation formOfEducation = studyGroupAsker.askQuestion("������ �������� ����� ��������?") ?
                studyGroupAsker.askFormOfEducation() : null;
        Person person = studyGroupAsker.askQuestion("������ �������� ������?") ?
                studyGroupAsker.askPerson() : null;
        return new GroupRaw(
                name,
                coordinates,
                studentsCount,
                expelledStudents,
                averageMark,
                formOfEducation,
                person
        );
    }

    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
