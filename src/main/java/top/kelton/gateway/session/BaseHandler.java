package top.kelton.gateway.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description:
 * @author: zzk
 * @create: 2024-11-14 22:04
 **/
public abstract class BaseHandler <T> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        session(ctx, ctx.channel(), msg);
    }

    protected abstract void session(ChannelHandlerContext ctx, final Channel channel, T request);
}
