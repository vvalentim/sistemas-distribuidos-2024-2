package com.vvalentim.server;

import com.vvalentim.helpers.AddressPromptHelper;
import com.vvalentim.protocol.request.RequestType;
import com.vvalentim.server.commands.authentication.LoginCommand;
import com.vvalentim.server.commands.authentication.LogoutCommand;
import com.vvalentim.server.commands.notificationCategories.NotificationCategoryDeleteCommand;
import com.vvalentim.server.commands.notificationCategories.NotificationCategoryFindCommand;
import com.vvalentim.server.commands.notificationCategories.NotificationCategoryListCommand;
import com.vvalentim.server.commands.notificationCategories.NotificationCategorySaveCommand;
import com.vvalentim.server.commands.users.*;
import com.vvalentim.server.database.MemoryDatabase;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {
    public static final boolean DEBUG = true;

    private final String hostname;

    private final int port;

    private final RequestCommandFactory requestCommandFactory;

    private ServerSocket socket = null;

    private boolean isRunning = false;

    public Server() {
        this("127.0.0.1", 5200);
    }

    public Server(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.requestCommandFactory = new RequestCommandFactory();

        this.requestCommandFactory.addCommand(RequestType.LOGIN, LoginCommand.class);
        this.requestCommandFactory.addCommand(RequestType.LOGOUT, LogoutCommand.class);

        this.requestCommandFactory.addCommand(RequestType.USER_CREATE, UserCreateCommand.class);
        this.requestCommandFactory.addCommand(RequestType.USER_LIST, UserListCommand.class);
        this.requestCommandFactory.addCommand(RequestType.USER_FIND, UserFindCommand.class);
        this.requestCommandFactory.addCommand(RequestType.USER_UPDATE, UserUpdateCommand.class);
        this.requestCommandFactory.addCommand(RequestType.USER_DELETE, UserDeleteCommand.class);

        this.requestCommandFactory.addCommand(RequestType.NOTIFICATION_CATEGORY_SAVE, NotificationCategorySaveCommand.class);
        this.requestCommandFactory.addCommand(RequestType.NOTIFICATION_CATEGORY_LIST, NotificationCategoryListCommand.class);
        this.requestCommandFactory.addCommand(RequestType.NOTIFICATION_CATEGORY_FIND, NotificationCategoryFindCommand.class);
        this.requestCommandFactory.addCommand(RequestType.NOTIFICATION_CATEGORY_DELETE, NotificationCategoryDeleteCommand.class);
    }

    @Override
    public void run() {
        try {
            System.out.println("Initializing server socket...");

            this.socket = new ServerSocket();
            this.socket.bind(new InetSocketAddress(this.hostname, this.port));
            this.isRunning = true;

            System.out.println(this.socket.toString());
            System.out.println("Server listening on " + this.hostname + ":" + this.port + "...");

            while (this.isRunning) {
                System.out.println("Waiting for new connection...");

                Socket connection = this.socket.accept();

                System.out.println("Connection accepted from: " + connection.toString());

                ConnectionHandler handler = new ConnectionHandler(this.requestCommandFactory, connection);

                handler.start();
            }
        } catch (IOException e) {
            if (!(e instanceof SocketException)) {
                throw new RuntimeException(e);
            }

            System.out.println("Server socket has been closed.");
        } finally {
            this.close();
        }
    }

    public void close() throws RuntimeException {
        try {
            this.isRunning = false;
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        MemoryDatabase db = MemoryDatabase.getInstance();
        AddressPromptHelper addrHelper = new AddressPromptHelper();

        Server server = new Server();
        // Server server = new Server(addrHelper.getAddress(), addrHelper.getPort());
        server.start();
    }
}