package home.nio;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

public class WorkerResponseToMessage implements Runnable {
    private final List<ServerDataEvent> queue = new LinkedList<>();

    /** обработка сообщения сервером
     * @param server сервер сообщения
     * @param socket сокет сообщения
     * @param data сообщение в буфере
     * @param count длина сообщения в буфере
     */
   public void processData(MessengerServerNio server, SocketChannel socket, byte[] data, int count) {
        //копирум данные из буфера и создаем дата класс + добавляем его в очередь
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);

        synchronized (queue) {
            queue.add(new ServerDataEvent(server, socket, dataCopy));
            queue.notify();
        }
    }

    public void run() {
        ServerDataEvent dataEvent;
        while (true) {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        System.out.println("WorkerResponseToMessage.run: queue is notified");
                    }
                }
                System.out.println("Recieved = " + new String(queue.get(0).data));
                dataEvent = queue.remove(0);
            }
            dataEvent.server.send(dataEvent.socket, dataEvent.data);
        }
    }
}
