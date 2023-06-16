package client.utility;

import client.AppClient;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;
import common.utility.Outputer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user a login and password.
 */
public class AuthAsker {
    private Scanner userScanner;

    public AuthAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Asks user a login.
     *
     * @return login.
     */
    public String askLogin() {
        String login;
        while (true) {
            try {
                Outputer.println("������� �����:");
                Outputer.print(AppClient.PS2);
                login = userScanner.nextLine().trim();
                if (login.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("������� ������ �� ����������!");
            } catch (MustBeNotEmptyException exception) {
                Outputer.printerror("��� �� ����� ���� ������!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("�������������� ������!");
                System.exit(0);
            }
        }
        return login;
    }

    /**
     * Asks user a password.
     *
     * @return password.
     */
    public String askPassword() {
        String password;
        while (true) {
            try {
                Outputer.println("������� ������:");
                Outputer.print(AppClient.PS2);
                password = userScanner.nextLine().trim();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("�������� ����� ��� ������!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("�������������� ������!");
                System.exit(0);
            }
        }
        return password;
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     */
    public boolean askQuestion(String question) {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Outputer.println(finalQuestion);
                Outputer.print(AppClient.PS2);
                answer = userScanner.nextLine().trim();
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Outputer.printerror("����� �� ���������!");
            } catch (NotInDeclaredLimitsException exception) {
                Outputer.printerror("����� ������ ���� ����������� ������� '+' ��� '-'!");
            } catch (IllegalStateException exception) {
                Outputer.printerror("�������������� ������!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}