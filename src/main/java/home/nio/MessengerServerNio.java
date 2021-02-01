package home.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.ByteBuffer.allocate;
import static java.nio.channels.SelectionKey.OP_ACCEPT;

public class MessengerServerNio {
    static final int PORT = 19000;
    static final String ADDRESS = "localhost";

    private Selector selector;
    private ByteBuffer readBuffer = allocate(8192);
    private EchoWorker worker = new EchoWorker();
    private final List<ChangeRequest> changeRequests = new LinkedList<>();
    private final Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<>();

    private MessengerServerNio() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(ADDRESS, PORT););
        selector = SelectorProvider.provider().openSelector();
        serverChannel.register(selector, OP_ACCEPT);
        new Thread(worker).start();
    }

}
