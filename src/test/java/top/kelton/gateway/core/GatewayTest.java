package top.kelton.gateway.core;

import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description:
 * @author: zzk
 * @create: 2024-11-14 22:18
 **/
public class GatewayTest {

    private final Logger logger = LoggerFactory.getLogger(GatewayTest.class);

    @Test
    public void test() throws ExecutionException, InterruptedException {
        SessionServer server = new SessionServer();

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();

        if (null == channel) throw new RuntimeException("netty server start error: channel is null");

        while (!channel.isActive()) {
            logger.info("NettyServer启动服务 ...");
            Thread.sleep(500);
        }
        logger.info("NettyServer启动服务完成 {}", channel.localAddress());

        Thread.sleep(Long.MAX_VALUE);
    }
}
