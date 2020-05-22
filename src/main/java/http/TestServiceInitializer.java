package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ProjectName netty
 * @ClassName TestServiceInitializer
 * @Description TODO
 * @Author mi
 * @Date 2020/5/22 16:59
 * @Version 1.0
 **/
public class TestServiceInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 获得pipeline
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 加入一个netty
        // netty提供的处理http的编码解码器
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new TestHttpServiceHandler());
    }
}
