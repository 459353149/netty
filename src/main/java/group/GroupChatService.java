package group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @ProjectName netty
 * @ClassName GroupChatService
 * @Description TODO
 * @Author mi
 * @Date 2020/5/18 16:32
 * @Version 1.0
 **/
public class GroupChatService {
    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int port = 6667;

    public GroupChatService() {


        // 得到选择器
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) throws Exception {
        GroupChatService groupChatServier = new GroupChatService();
        groupChatServier.listen();
    }


    public void listen() throws IOException {
        // 循环监听
        while (true) {
            int count = selector.select(2000);
            // 有事件处理
            if (count > 0) {
                // 遍历得到的selectorkey
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);

                        System.out.println(accept.getRemoteAddress() + "上线");
                    }
                    if (selectionKey.isReadable()) {
                        readDate(selectionKey);
                    }
                    iterator.remove();
                }
            }
        }
    }


    public void readDate(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        try {
            int read = channel.read(byteBuffer);
            String msg = new String(byteBuffer.array());
            if (read > 0) {
                sendToOther(msg, channel);
                System.out.println(msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                selectionKey.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    public void sendToOther(String msg, SocketChannel channel) {
        // 获取所有的channel 排除自己
        for (SelectionKey key : selector.keys()) {
            SocketChannel selectableChannel = (SocketChannel) key.channel();
            if (selectableChannel != channel) {
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                try {
                    selectableChannel.write(wrap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
