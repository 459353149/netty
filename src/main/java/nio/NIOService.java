package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @ProjectName netty
 * @ClassName NIOService
 * @Description TODO
 * @Author mi
 * @Date 2020/5/18 14:20
 * @Version 1.0
 **/
public class NIOService {
    public static void main(String[] args) throws IOException {
        // 创建一个serviceSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 得到一个selector对象
        Selector selector = Selector.open();

        // 绑定一个监听端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把serverSocketChannel注册到selector中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 等待1秒 如果没有事件发生 就
            if (selector.selectNow()== 0) {
                continue;
            }
            // 如果返回的不是0  获取到相关的selectionKeys集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                System.out.println("链接成功");
                SelectionKey selectionKey = iterator.next();
                // 获取selectionKey的事件
                // 链接事件
                if (selectionKey.isAcceptable()) {
                    System.out.println("注册事件");
                    //如果是OP_ACCEPT 又新增的客户端俩捏
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将当前的socketChannel 注册到selector中 同时关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 如果是一个读的事件
                if (selectionKey.isReadable()) {
                    System.out.println("读事件");
                    // 通过key反向获取channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 获取到该channel关联的buffer
                    ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();
                    // 把当前channel的数据读入到byteBuffer
                    channel.read(attachment);

                    System.out.println("数据为：" + new String(attachment.array()));

                }
                //手动从集合中移除当前的key
                iterator.remove();

            }
        }

    }
}
