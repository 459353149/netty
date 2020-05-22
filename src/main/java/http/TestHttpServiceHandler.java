package http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @ProjectName netty
 * @ClassName TestHttpServiceHandler
 * @Description TODO HttpObject 表示客户端和服务端通讯数据为 HttpObject 被封装了一次
 * @Author mi
 * @Date 2020/5/22 16:58
 * @Version 1.0
 **/
public class TestHttpServiceHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * 读取客户端数据
     * @param channelHandlerContext
     * @param httpObject
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        if(httpObject instanceof HttpRequest){
            System.out.println("httpObject "+httpObject.getClass());
            System.out.println("客户端地址："+channelHandlerContext.channel().remoteAddress());




            // 回复信息
            ByteBuf byteBuf = Unpooled.copiedBuffer("我是服务器端", CharsetUtil.UTF_8);
            FullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, byteBuf);
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(defaultFullHttpResponse);
        }
    }
}
