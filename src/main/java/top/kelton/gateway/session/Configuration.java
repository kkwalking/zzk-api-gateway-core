package top.kelton.gateway.session;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import top.kelton.gateway.core.bind.GenericReferenceRegistry;
import top.kelton.gateway.core.bind.IGenericReference;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 配置
 * @author: zzk
 * @create: 2024-11-16 14:26
 **/
public class Configuration {

    private final GenericReferenceRegistry registry = new GenericReferenceRegistry(this);
    // RPC key->  rpc-provider-01
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    // RPC  zookeeper://127.0.0.1:2181
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    // RPC key -> 接口级别
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {

    //     配置应用、接口、方法信息

        // 示例应用
        ApplicationConfig application = new ApplicationConfig();
        application.setName("rpc-provider-01");
        application.setQosEnable(false);
        // 注册中心地址
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        // dubbo泛化调用
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("top.kelton.gateway.rpc.IUserService");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        applicationConfigMap.put("rpc-provider-01", application);
        registryConfigMap.put("rpc-provider-01", registry);
        referenceConfigMap.put("top.kelton.gateway.rpc.IUserService", reference);
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        registry.addGenericReference(application, interfaceName, methodName);
    }

    public IGenericReference getGenericReference(String methodName) {
        return registry.getGenericReference(methodName);
    }

}
