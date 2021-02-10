package home.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.*;

public class MessengerClientNio {
    static final int PORT = 19000;
    static final String ADDRESS = "localhost";
    private ByteBuffer buffer = allocate(255);

    static Logger log = LoggerFactory.getLogger(MessengerClientNio.class);

    public void run() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, OP_CONNECT);
        channel.connect(new InetSocketAddress(ADDRESS, PORT));
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        BlockingQueue<String> queueSystemOut = new ArrayBlockingQueue<String>(16);

        // создаем отдельный поток на чтение ввода с клавиатуры
        new Thread(() -> {
            System.out.println("Press you message or \\q for exit:");
            Scanner scanner = new Scanner(System.in);
            while (true) {
//                while (!queueSystemOut.isEmpty()) {
//                    try {
//                        System.out.println(queueSystemOut.take());
//                    } catch (InterruptedException e) {
//
//                    }
//                }
                String line = scanner.nextLine();
                if ("\\q".equals(line)) {
                    System.exit(0);
                }
                try {
                    queue.put(line);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    System.out.println("MessengerClientNio.run: queue be notified");
                }
                SelectionKey key = channel.keyFor(selector);
                key.interestOps(OP_WRITE);
                selector.wakeup();
            }
        }).start();

        // в текущем потоке обмен сообщениями с сервером
        while (true) {
            selector.select();
            for (SelectionKey selectionKey : selector.selectedKeys()) {
                if (selectionKey.isConnectable()) {
                    channel.finishConnect();
                    selectionKey.interestOps(OP_WRITE);
                    //System.out.println("[Connected to server]");
                } else if (selectionKey.isReadable()) {
                    buffer.clear();
                    channel.read(buffer);
                    try {
                        queueSystemOut.put("[Received = '" + new String(buffer.array())+"']");
                    } catch (InterruptedException e) {

                    }
                    //System.out.println("[Received = '" + new String(buffer.array())+"']");
                } else if (selectionKey.isWritable()) {
                    String line = queue.poll();
                    if (line != null) {
                        channel.write(ByteBuffer.wrap(line.getBytes()));
                        try {
                            queueSystemOut.put("[Send to server = '" + line +"']");
                        } catch (InterruptedException e) {

                        }
                        //System.out.println("[Send to server = '" + line +"']");
                    }
                    selectionKey.interestOps(OP_READ);
                }
            }
        }
    }
//
//    public static void main(String[] args) throws Exception {
//        new MessengerClientNio().run();
//    }
}
