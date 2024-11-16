package top.kelton.gateway.session;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import top.kelton.gateway.session.handlers.SessionServerHandler;

/**
 * @description:
 * @author: zzk
 * @create: 2024-11-14 22:03
 **/
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Configuration configuration;
    public SessionChannelInitializer(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline line = channel.pipeline();
        line.addLast(new HttpRequestDecoder());
        line.addLast(new HttpResponseEncoder());
        line.addLast(new HttpObjectAggregator(1024 * 1024));
        line.addLast(new SessionServerHandler(configuration));
    }
}
