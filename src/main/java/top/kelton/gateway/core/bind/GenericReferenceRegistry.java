package top.kelton.gateway.core.bind;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import top.kelton.gateway.session.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 泛化调用注册器
 * @author: zzk
 * @create: 2024-11-16 13:21
 **/
public class GenericReferenceRegistry {

    private final Configuration configuration;
    private final Map<String, GenericReferenceProxyFactory> knownGenericReferences = new HashMap<>();


    public GenericReferenceRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        // 获取基础服务（创建成本较高，需缓存）
        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
        RegistryConfig registryConfig = configuration.getRegistryConfig(application);
        ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);
        // 构建Dubbo服务
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig).registry(registryConfig).reference(reference).start();
        // 获取泛化调用服务
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // 创建并保存泛化工厂
        knownGenericReferences.put(methodName, new GenericReferenceProxyFactory(genericService));
    }

    public IGenericReference getGenericReference(String methodName) {
        GenericReferenceProxyFactory proxyFactory = knownGenericReferences.get(methodName);
        if (proxyFactory == null) {
            throw new RuntimeException("[" + methodName + "] is not register into genericReferenceRegistry.");
        }
        return proxyFactory.newInstance(methodName);
    }
}
