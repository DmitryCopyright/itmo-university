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
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);


    public ConnectionHandler(Server server, Socket clientSocket, CommandManager commandManager) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.commandManager = commandManager;
    }

    /**
     * Main handling cycle.
     */
    @Override
    public void run() {
        Request userRequest = null;
        Response responseToUser = null;
        boolean stopFlag = false;
// try {
   //  App.logger.error("breakpoint1" + "\n" + clientSocket.getInputStream() + "\n" + clientSocket.getOutputStream());
// } catch (IOException e){App.logger.error(e.getMessage());}
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());


             ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
         //   App.logger.error("breakpoint2" + "\n" + clientReader + "\n" + clientWriter);

            do {
                userRequest = (Request) clientReader.readObject();
                responseToUser = forkJoinPool.invoke(new HandleRequestTask(userRequest, commandManager));
                App.logger.info("������ '" + userRequest.getCommandName() + "' ���������.");
                Response finalResponseToUser = responseToUser;
                if (!fixedThreadPool.submit(() -> {
                    try {
                        clientWriter.writeObject(finalResponseToUser);
                        clientWriter.flush();
                        return true;
                    } catch (IOException exception) {
                        Outputer.printerror("��������� ������ ��� �������� ������ �� ������!");
                        App.logger.error("��������� ������ ��� �������� ������ �� ������!");
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