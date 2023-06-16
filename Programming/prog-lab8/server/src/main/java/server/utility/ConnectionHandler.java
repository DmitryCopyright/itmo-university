package server.utility;

import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseCode;
import common.utility.Outputer;
import server.App;
import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Handles user connection.
 */
public class ConnectionHandler implements Runnable {
    private Server server;
    private Socket clientSocket;
    private CommandManager commandManager;
    private CollectionManager collectionManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);


    public ConnectionHandler(Server server, Socket clientSocket, CommandManager commandManager,
                             CollectionManager collectionManager) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }

    /**
     * Main handling cycle.
     */
    @Override
    public void run() {
        Request userRequest = null;
        Response responseToUser = null;
        boolean stopFlag = false;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                userRequest = (Request) clientReader.readObject();
                Outputer.println(userRequest.toString());
                responseToUser = forkJoinPool.invoke(new HandleRequestTask(userRequest, commandManager,
                        collectionManager));
                Outputer.printerror("������ '" + userRequest.getCommandName() + "' ���������.");
                Response finalResponseToUser = responseToUser;
                if (!fixedThreadPool.submit(() -> {
                    try {
                        TimeUnit time = TimeUnit.SECONDS;
                        time.sleep(10);
                        clientWriter.writeObject(finalResponseToUser);
                        clientWriter.flush();
                        clientWriter.reset();
                        return true;
                    } catch (IOException exception) {
                        Outputer.printerror("��������� ������ ��� �������� ������ �� ������!");
                        App.logger.error(exception.toString() + "\n" + exception.getMessage() + "\n");

                    }
                    return false;
                }).get()) break;
            } while (responseToUser.getResponseCode() != ResponseCode.SERVER_EXIT &&
                    responseToUser.getResponseCode() != ResponseCode.CLIENT_EXIT);
            if (responseToUser.getResponseCode() == ResponseCode.SERVER_EXIT)
                stopFlag = true;
        } catch (ClassNotFoundException exception) {
            Outputer.printerror("��������� ������ ��� ������ ���������� ������!");
            App.logger.error("��������� ������ ��� ������ ���������� ������!");
        } catch (CancellationException | ExecutionException | InterruptedException exception) {
            Outputer.println("��� ��������� ������� ��������� ������ ���������������!");
            App.logger.warn("��� ��������� ������� ��������� ������ ���������������!");
        } catch (IOException exception) {
            Outputer.printerror("�������������� ������ ���������� � ��������!");
            App.logger.warn("�������������� ������ ���������� � ��������!");
        } finally {
            try {
                fixedThreadPool.shutdown();
                clientSocket.close();
                Outputer.println("������ �������� �� �������.");
                App.logger.info("������ �������� �� �������.");
            } catch (IOException exception) {
                Outputer.printerror("��������� ������ ��� ������� ��������� ���������� � ��������!");
                App.logger.error("��������� ������ ��� ������� ��������� ���������� � ��������!");
            }
            if (stopFlag) server.stop();
            server.releaseConnection();
        }
    }
}