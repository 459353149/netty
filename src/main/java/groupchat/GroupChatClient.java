package groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @ProjectName netty
 * @ClassName GroupChatClient
 * @Description TODO
 * @Author mi
 * @Date 2020/5/25 17:08
 * @Version 1.0
 **/
public class GroupChatClient {

    private final String host;

    private final Integer port;

    public GroupChatClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入相关的handler
                            // 向pipeline中加入一个编码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect(host, port).sync();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                System.out.println("发送消息："+s);
                sync.channel().writeAndFlush(s);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient("127.0.0.1",7000);
        groupChatClient.run();
    }

}
