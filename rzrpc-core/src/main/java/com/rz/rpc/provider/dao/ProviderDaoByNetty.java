package com.rz.rpc.provider.dao;

/**
 * Created by as on 2018/1/21.
 */
public interface ProviderDaoByNetty {
    /**
     * 开启监听
     *
     * @return
     */
    boolean startListenByNetty();

    /**
     * 注册服务
     *
     * @param interfaceName
     * @param version
     * @param implClassName
     * @param ip
     */
    boolean registryService(String interfaceName, String version, String implClassName, String ip);
}
