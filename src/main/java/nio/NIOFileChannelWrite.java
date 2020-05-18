package nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName netty
 * @ClassName NIOFileChannelWrite
 * @Description TODO
 * @Author mi
 * @Date 2020/5/15 16:43
 * @Version 1.0
 **/
public class NIOFileChannelWrite {

    public static void main(String[] args) throws Exception {
        String test="test nio";
        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\demo\\test.txt");
        // 同输出流 获取对应的文件channel
        FileChannel channel = fileOutputStream.getChannel();
        // 创建一个缓冲区 因为channel要跟buffer交互
        ByteBuffer byteBuffer =ByteBuffer.allocate(1024);
        //将数据放入缓冲区
        byteBuffer.put(test.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);

    }
}
