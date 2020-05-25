package groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ProjectName netty
 * @ClassName GroupChatClientHandler
 * @Description TODO
 * @Author mi
 * @Date 2020/5/25 17:16
 * @Version 1.0
 **/
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}
