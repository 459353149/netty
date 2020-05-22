package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ProjectName netty
 * @ClassName TestService
 * @Description TODO
 * @Author mi
 * @Date 2020/5/22 16:58
 * @Version 1.0
 **/
public class TestService {


    public static void main(String[] args) {

        EventLoopGroup boosGroup = new NioEventLoopGroup(1);

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(boosGroup,workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServiceInitializer());

            ChannelFuture channelFuture = bootstrap.bind(6868).sync();

            // 监听
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
