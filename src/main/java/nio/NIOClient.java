package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ProjectName netty
 * @ClassName NIOClient
 * @Description TODO
 * @Author mi
 * @Date 2020/5/18 15:41
 * @Version 1.0
 **/
public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 获取一个网络通道
        SocketChannel open = SocketChannel.open();
        open.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if (!open.connect(inetSocketAddress)) {
            while (!open.finishConnect()) {
            }
        }

        System.out.println("2312");
        // 如果链接成功 就发送数据
        String str = "test";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());

        // 发送数据 将buffer写入channel
        open.write(byteBuffer);
        System.in.read();
    }
}
