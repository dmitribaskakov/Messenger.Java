package home;

import home.nio.MessengerServerNio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

//import java.util.concurrent.PriorityBlockingQueue;

public class MessengerServer {
    static Logger log = LoggerFactory.getLogger(MessengerServer.class);

    public static void main(String... args) throws IOException { //throws Exception
        System.out.println("Initialize Messenger Server");

        new MessengerServerNio().run();
    }
}

