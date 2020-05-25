package groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @ProjectName netty
 * @ClassName GroupChatService
 * @Description TODO
 * @Author mi
 * @Date 2020/5/25 15:41
 * @Version 1.0
 **/
public class GroupChatService {

    /**
     * 监听端口
     */
    private Integer port;

    public GroupChatService(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        GroupChatService groupChatService = new GroupChatService(7000);
        groupChatService.run();
    }


    /**
     *
     */
    public void run() {
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boosGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 向pipeline中加入一个编码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            // 加入自己的handler
                            pipeline.addLast(new GroupChatServiceHandler());
                        }
                    });

            System.out.println("服务器启动");
            ChannelFuture sync = bootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
