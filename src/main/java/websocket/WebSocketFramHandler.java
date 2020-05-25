package websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @ProjectName netty
 * @ClassName WebSocketFramHandler
 * @Description TODO
 * @Author mi
 * @Date 2020/5/25 18:00
 * @Version 1.0
 **/
public class WebSocketFramHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

        System.out.println("服务器收到消息");
    }
}
