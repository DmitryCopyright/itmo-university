package client.utility;

import common.interaction.Request;
import common.interaction.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Handle user login and password.
 */
public class AuthHandler {
    private final String loginCommand = "login";
    private final String registerCommand = "register";

    private Scanner userScanner;

    public AuthHandler(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Handle user authentication.
     *
     * @return Request of user.
     */
    public Request handle() throws NoSuchAlgorithmException {
        AuthAsker authAsker = new AuthAsker(userScanner);
        String command = authAsker.askQuestion("� ��� ��� ���� ������� ������?") ? loginCommand : registerCommand;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        User user = new User(authAsker.askLogin(), md.digest(authAsker.askPassword().getBytes()));
        return new Request(command, "", user);
    }
}