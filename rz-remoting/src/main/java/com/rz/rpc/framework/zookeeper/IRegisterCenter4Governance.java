package com.rz.rpc.framework.zookeeper;

import com.rz.rpc.framework.model.InvokerService;
import com.rz.rpc.framework.model.ProviderService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * @author as
 * @create 2018-11-10 11:02
 * @desc 服务治理接口
 */
public interface IRegisterCenter4Governance {

    /**
     * 获取服务提供者列表与服务消费者列表
     *
     * @param serviceName
     * @param appKey
     * @return
     */
    public Pair<List<ProviderService>, List<InvokerService>> queryProvidersAndInvokers(String serviceName, String appKey);

}
