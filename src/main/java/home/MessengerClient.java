package home;

//import home.core.protocol.StringProtocol;
import home.nio.MessengerClientNio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
//import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessengerClient {
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    public static void main(String... args) throws IOException  { //throws Exception

        System.out.println("Initialize Messenger Client");

        new MessengerClientNio().run();
    }

}
