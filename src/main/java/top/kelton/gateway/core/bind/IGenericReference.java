package top.kelton.gateway.core.bind;

/**
 * @description: RPC泛化调用
 * @author: zzk
 * @create: 2024-11-16 11:13
 **/
public interface IGenericReference {

    String $invoke(String methodName);

}
