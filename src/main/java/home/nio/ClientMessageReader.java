package home.nio;
import java.io.InputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import static java.nio.channels.SelectionKey.OP_WRITE;

/**
 * Чтение с InputStream
 */

public class ClientMessageReader extends Thread {
    private Boolean stopTread = false;
    private InputStream inputStream;
    private SocketChannel channel;
    private Selector selector;
    private BlockingQueue<String> queue;

//    private static ClientMessageReader self = null;
//    private ClientMessageReader() {
//        super();
//        self = this;
//    }
//    public static ClientMessageReader getInstance() {
//        if (self == null) {
//            synchronized(ClientMessageReader.class) {
//                if (self == null) {
//                    self = new ClientMessageReader();
//                }
//            }
//        }
//        return self;
//    }

    public ClientMessageReader (InputStream inputStream, Selector selector, SocketChannel channel, BlockingQueue<String> queue) {
        super();
        this.inputStream = inputStream;
        this.selector = selector;
        this.channel = channel;
        this.queue = queue;
    }

    public void run() {
        // создаем отдельный поток на чтение ввода с InputStream
        System.out.println("Press you message or \\q for exit:");
        Scanner scanner = new Scanner(inputStream);
        while (!stopTread) {
            String line = scanner.nextLine();
            if ("\\q".equals(line)) {
                System.exit(0);
            }
            try {
                queue.put(line);
            } catch (InterruptedException e) {
                e.printStackTrace();
//                    System.out.println("MessengerClientNio.run: queue be notified");
            }
            SelectionKey key = channel.keyFor(selector);
            key.interestOps(OP_WRITE);
            selector.wakeup();
        }
    }
}
