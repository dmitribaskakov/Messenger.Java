package home.socket;

//import home.core.protocol.StringProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
//import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessengerClientSocked {1
    static Logger log = LoggerFactory.getLogger(MessengerClientSocked.class);

    public static void main(String... args) { //throws Exception

        System.out.println("Initialize Messenger Client");

        BufferedReader consoleReader; // ридер читающий с консоли
        String messageText = "";
        System.out.println("Your message:");
        try {
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            messageText = consoleReader.readLine(); // ждём пока клиент что-нибудь
        } catch ( IOException ex) {
            log.error("Read message", ex);
        }

        String serverHostName = "localhost";
        int serverHostPort = 19001;
        Socket clientSocket = null; //сокет для взаимодействия с сервером
        BufferedReader clientSocketReader = null; // буферизированный поток чтения из сокета
        BufferedWriter clientSocketWriter = null; // буферизированный поток записи в сокет

        try {
            // открываем соединение с сервером и инициализируем потоки для читения и записи соообщения на сервер
            clientSocket = new Socket(serverHostName, serverHostPort);
            clientSocketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientSocketWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            clientSocketWriter.write(messageText + "\n"); // отправляем сообщение на сервер
            clientSocketWriter.flush();

            String serverMessageText = clientSocketReader.readLine(); // ждём, что скажет сервер
            System.out.println(serverMessageText); // получив - выводим на экран

        } catch ( UnknownHostException ex) {
            log.error("Unknown Server Host", ex);
        } catch ( IOException ex) {
            log.error("Communicate with Server", ex);
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("Close connect to Server");
            if (clientSocket != null) try {
                clientSocket.close();
            } catch ( IOException ex) {
                log.error("Close socket to Server", ex);
            }
            if (clientSocketReader != null) try {
                clientSocketReader.close();
            } catch ( IOException ex) {
                log.error("Close reader stream from Server", ex);
            }
            if (clientSocketWriter != null) try {
                clientSocketWriter.close();
            } catch ( IOException ex) {
                log.error("Close writer stream to Server", ex);
            }
        }

    }
}
