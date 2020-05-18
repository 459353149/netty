package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ProjectName netty
 * @ClassName NIOFileChannelCopy
 * @Description TODO
 * @Author mi
 * @Date 2020/5/15 17:05
 * @Version 1.0
 **/
public class NIOFileChannelCopy {
    public static void main(String[] args) throws IOException {
        // 创建一个文件流
        File file = new File("D:\\demo\\test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        FileOutputStream fileInputStream1 = new FileOutputStream("D:\\demo\\test1.txt");
        FileChannel channel1 = fileInputStream1.getChannel();
        byteBuffer.flip();
        channel1.write(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
    }
}
