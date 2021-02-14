package home;

//import home.core.protocol.StringProtocol;
import home.nio.MessengerClientNio;
import home.nio.ClientMessageReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.*;

public class MessengerClient {
    static Logger log = LoggerFactory.getLogger(MessengerClient.class);

    public static void main(String... args) throws IOException  { //throws Exception
        final int PORT = 19000;
        final String ADDRESS = "localhost";
        ByteBuffer buffer = allocate(255);
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, OP_CONNECT);
        channel.connect(new InetSocketAddress(ADDRESS, PORT));
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

        System.out.println("Initialize Messenger Client");

        // Запускаем тред чтения с клавиатуры
        ClientMessageReader readerTread = new ClientMessageReader (System.in, selector, channel, queue);
        readerTread.start();

        new MessengerClientNio().run();
    }

}
