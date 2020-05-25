package groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;

/**
 * @ProjectName netty
 * @ClassName GroupChatServiceHandler
 * @Description TODO
 * @Author mi
 * @Date 2020/5/25 15:52
 * @Version 1.0
 **/
public class GroupChatServiceHandler extends SimpleChannelInboundHandler<String> {

    /**
     * GlobalEventExecutor 全局的时间执行器，
     */
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将当前channel加入到group中
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        // 将加入的channel 推送给别的客户端
        channels.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + "  加入聊天");
    }

    /**
     * 表示channel处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "  上线了");
    }

    /**
     * 当channel处于非活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "  离线了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        channels.writeAndFlush("客户端：" + ctx.channel().remoteAddress() + "  离开了");

    }

    /**
     * 处理业务
     * @param channelHandlerContext
     * @param s
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        channels.forEach(c -> {
            if (c != channel) {
                c.writeAndFlush("客户: " + channel.remoteAddress() + "  发送消息 " + s);
            }
        });
    }
}
