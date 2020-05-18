package group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @ProjectName netty
 * @ClassName GroupClient
 * @Description TODO
 * @Author mi
 * @Date 2020/5/18 16:55
 * @Version 1.0
 **/
public class GroupClient {

    private final String HOST = "127.0.0.1";
    private final int post = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String userName;

    public GroupClient() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, post));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName);
    }

    public static void main(String[] args) throws IOException {

        GroupClient groupClient = new GroupClient();

        new Thread(() -> {
            while (true) {
                try {
                    groupClient.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String next = scanner.next();
            groupClient.send(next);
        }
    }


    public void send(String msg) throws IOException {
        msg = userName + "说：" + msg;
        socketChannel.write(ByteBuffer.wrap(msg.getBytes()));

    }

    public void read() throws IOException {
        int select = selector.select();
        if (select > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    // 得到channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.write(byteBuffer);
                    System.out.println(new String(byteBuffer.array()));
                }
                iterator.remove();
            }
        }
    }
}
