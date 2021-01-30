package home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.concurrent.PriorityBlockingQueue;

public class MessengerServer {
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    public static void main(String... args) { //throws Exception

        System.out.println("Initialize Messenger Server");

        String messageText;
        int serverHostPort = 19001;
        ServerSocket serverSocket = null; //сокет сервера
        Socket clientSocket; //сокет клиента
        BufferedReader clientSocketReader = null; // буферизированный поток чтения из сокета
        BufferedWriter clientSocketWriter = null; // буферизированный поток записи вdddd сокет

        try {
            // подимаем соединение и инициализируем потоки для читения и записи соообщения на клиент
            serverSocket = new ServerSocket(serverHostPort);
            System.out.println("Messenger Server is started");

            clientSocket = serverSocket.accept(); // будет ждать пока клиент не соединится с ним
            clientSocketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientSocketWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            messageText = clientSocketReader.readLine(); // читаем сообщение от клиента
            System.out.println("Client message: " + messageText);

            clientSocketWriter.write("Echo from Server: " + messageText + "\n"); // отправляем сообщение на клиент
            clientSocketWriter.flush();

        } catch ( IOException ex) {
            log.error("Communicate with Client", ex);
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("Close Server");
            if (serverSocket != null) try {
                serverSocket.close();
            } catch ( IOException ex) {
                log.error("Close Server socket", ex);
            }
            if (clientSocketReader != null) try {
                clientSocketReader.close();
            } catch ( IOException ex) {
                log.error("Close reader stream from Client", ex);
            }
            if (clientSocketWriter != null) try {
                clientSocketWriter.close();
            } catch ( IOException ex) {
                log.error("Close writer stream to Client", ex);
            }
        }


//        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
    }

}

