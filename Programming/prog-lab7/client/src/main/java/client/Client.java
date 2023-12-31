package client;

import client.utility.AuthHandler;
import client.utility.UserHandler;
import common.exceptions.ConnectionErrorException;
import common.exceptions.NotInDeclaredLimitsException;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseCode;
import common.interaction.User;
import common.utility.Outputer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.security.NoSuchAlgorithmException;

/**
 * Runs the client.
 */
public class Client {
    private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private UserHandler userHandler;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private AuthHandler authHandler;
    private User user;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, UserHandler userHandler,
                  AuthHandler authHandler) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userHandler = userHandler;
        this.authHandler = authHandler;
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            while (true) {
                try {
                    connectToServer();
                    processAuthentication();
                    processRequestToServer();
                    break;
                } catch (ConnectionErrorException exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                        Outputer.printerror("��������� ���������� ������� �����������!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                        Outputer.printerror("����� �������� ����������� '" + reconnectionTimeout +
                                "' ��������� �� ��������� ��������� ��������!");
                        Outputer.println("��������� ����������� ����� ����������� ����������.");
                    } catch (Exception timeoutException) {
                        Outputer.printerror("��������� ������ ��� ������� �������� �����������!");
                        Outputer.println("��������� ����������� ����� ����������� ����������.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            Outputer.println("������ ������� ���������.");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("������ �� ����� ���� �������!");
        } catch (IOException exception) {
            Outputer.printerror("��������� ������ ��� ������� ��������� ���������� � ��������!");
        }
    }

    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionErrorException, NotInDeclaredLimitsException {
        try {
            if (reconnectionAttempts >= 1) Outputer.println("��������� ���������� � ��������...");
            Outputer.println(host+ " "+port);
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            Outputer.println("���������� � �������� �����������.");
            Outputer.println("�������� ���������� �� ����� �������...");
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            Outputer.println("���������� �� ����� ������� ��������.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("����� ������� ������ �����������!");
            throw new NotInDeclaredLimitsException();
        } catch (IOException exception) {
            Outputer.printerror("��������� ������ ��� ���������� � ��������!");
            throw new ConnectionErrorException();
        }
    }

    /**
     * Server request process.
     */
    private void processRequestToServer() {

        Request requestToServer = null;

        Response serverResponse = null;

        do {
            try {
                requestToServer = serverResponse != null ? userHandler.handle(serverResponse.getResponseCode(), user) :
                        userHandler.handle(null, user);

                if (requestToServer.isEmpty()) continue;

                serverWriter.writeObject(requestToServer);

                serverResponse = (Response) serverReader.readObject();

                Outputer.print(serverResponse.getResponseBody());

            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("��������� ������ ��� �������� ������ �� ������!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("��������� ������ ��� ������ ���������� ������!");
            } catch (IOException exception) {
                Outputer.printerror("���������� � �������� ���������!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    if (requestToServer.getCommandName().equals("exit"))
                        Outputer.println("������� �� ����� ���������������� �� �������.");
                    else Outputer.println("���������� ��������� ������� �������.");
                }
            }
        } while (!requestToServer.getCommandName().equals("exit"));
    }

    /**
     * Handle process authentication.
     */
    private void processAuthentication() {
        Request requestToServer = null;
        Response serverResponse = null;
        do {
            try {
                requestToServer = authHandler.handle();
                if (requestToServer.isEmpty()) continue;
                serverWriter.writeObject(requestToServer);
                serverResponse = (Response) serverReader.readObject();
                Outputer.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
                Outputer.printerror("��������� ������ ��� �������� ������ �� ������!");
            } catch (ClassNotFoundException exception) {
                Outputer.printerror("��������� ������ ��� ������ ���������� ������!");
            } catch (IOException exception) {
                Outputer.printerror("���������� � �������� ���������!");
                try {
                    connectToServer();
                } catch (ConnectionErrorException | NotInDeclaredLimitsException reconnectionException) {
                    Outputer.println("���������� ��������� ����������� �������.");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } while (serverResponse == null || !serverResponse.getResponseCode().equals(ResponseCode.OK));
        user = requestToServer.getUser();
    }
}