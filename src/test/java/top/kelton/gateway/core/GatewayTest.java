package top.kelton.gateway.core;

import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.kelton.gateway.session.Configuration;
import top.kelton.gateway.session.GenericReferenceSessionFactoryBuilder;
import top.kelton.gateway.session.SessionServer;

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
        Configuration configuration = new Configuration();
        configuration.addGenericReference("rpc-provider-01", "top.kelton.gateway.rpc.IUserService", "getUserInfo");
        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        Future<Channel> channelFuture = builder.build(configuration);

        logger.info("启动服务完成 {}", channelFuture.get().id());

        Thread.sleep(Long.MAX_VALUE);
    }
}
