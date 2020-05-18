package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName netty
 * @ClassName NIOFileChannelRead
 * @Description TODO
 * @Author mi
 * @Date 2020/5/15 16:54
 * @Version 1.0
 **/
public class NIOFileChannelRead {
    public static void main(String[] args) throws IOException {
        // 创建一个文件流
        File file = new File("D:\\demo\\test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
