package server;

import common.exceptions.ClosingSocketException;
import common.exceptions.ConnectionErrorException;
import common.exceptions.OpeningServerSocketException;
import common.utility.Outputer;
import server.utility.CommandManager;
import server.utility.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Runs the server.
 */
public class Server {
    private int port;
    private ServerSocket serverSocket;
    private CommandManager commandManager;
    private boolean isStopped;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private Semaphore semaphore;

    public Server(int port, int maxClients, CommandManager commandManager) {
        this.port = port;
        this.commandManager = commandManager;
        this.semaphore = new Semaphore(maxClients);
    }

    /**
     * Begins server operation.
     */
    public void run() {
        try {
            openServerSocket();
            while (!isStopped()) {
                try {
                    acquireConnection();
                    if (isStopped()) throw new ConnectionErrorException();
                    Socket clientSocket = connectToClient();
                    cachedThreadPool.submit(new ConnectionHandler(this, clientSocket, commandManager));
                } catch (ConnectionErrorException exception) {
                    if (!isStopped()) {
                        Outputer.printerror("��������� ������ ��� ���������� � ��������!");
                        App.logger.error("��������� ������ ��� ���������� � ��������!");
                    } else break;
                }
            }
            cachedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            Outputer.println("������ ������� ���������.");
        } catch (OpeningServerSocketException exception) {
            Outputer.printerror("������ �� ����� ���� �������!");
            App.logger.fatal("������ �� ����� ���� �������!");
        } catch (InterruptedException e) {
            Outputer.printerror("��������� ������ ��� ���������� ������ � ��� ������������� ���������!");
        }
    }

    /**
     * Acquire connection.
     */
    public void acquireConnection() {
        try {
            semaphore.acquire();
            App.logger.info("���������� �� ����� ���������� ��������.");
        } catch (InterruptedException exception) {
            Outputer.printerror("��������� ������ ��� ��������� ���������� �� ����� ����������!");
            App.logger.error("��������� ������ ��� ��������� ���������� �� ����� ����������!");
        }
    }

    /**
     * Release connection.
     */
    public void releaseConnection() {
        semaphore.release();
        App.logger.info("������ ���������� ���������������.");
    }

    /**
     * Finishes server operation.
     */
    public synchronized void stop() {
        try {
            App.logger.info("���������� ������ �������...");
            if (serverSocket == null) throw new ClosingSocketException();
            isStopped = true;
            cachedThreadPool.shutdown();
            serverSocket.close();
            Outputer.println("���������� ������ � ��� ������������� ���������...");
            App.logger.info("������ ������� ���������.");
        } catch (ClosingSocketException exception) {
            Outputer.printerror("���������� ��������� ������ ��� �� ����������� �������!");
            App.logger.error("���������� ��������� ������ ��� �� ����������� �������!");
        } catch (IOException exception) {
            Outputer.printerror("��������� ������ ��� ���������� ������ �������!");
            Outputer.println("���������� ������ � ��� ������������� ���������...");
            App.logger.error("��������� ������ ��� ���������� ������ �������!");
        }
    }

    /**
     * Checked stops of server.
     *
     * @return Status of server stop.
     */
    private synchronized boolean isStopped() {
        return isStopped;
    }

    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocketException {
        try {
            App.logger.info("������ �������...");
            serverSocket = new ServerSocket(port);
            App.logger.info("������ �������.");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("���� '" + port + "' ��������� �� ��������� ��������� ��������!");
            App.logger.fatal("���� '" + port + "' ��������� �� ��������� ��������� ��������!");
            throw new OpeningServerSocketException();
        } catch (IOException exception) {
            Outputer.printerror("��������� ������ ��� ������� ������������ ���� '" + port + "'!");
            App.logger.fatal("��������� ������ ��� ������� ������������ ���� '" + port + "'!");
            throw new OpeningServerSocketException();
        }
    }

    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionErrorException {
        try {
            Outputer.println("������������� ����� '" + port + "'...");
            App.logger.info("������������� ����� '" + port + "'...");
            Socket clientSocket = serverSocket.accept();
            Outputer.println("���������� � �������� �����������.");
            App.logger.info("���������� � �������� �����������.");
            return clientSocket;
        } catch (IOException exception) {
            throw new ConnectionErrorException();
        }
    }
}