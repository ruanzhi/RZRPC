package com.rz.rpc.framework.revoker;

import com.rz.rpc.framework.model.InvokerService;
import com.rz.rpc.framework.model.ProviderService;
import com.rz.rpc.framework.zookeeper.IRegisterCenter4Invoker;
import com.rz.rpc.framework.zookeeper.RegisterCenter;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

/**
 * @author as
 * @create 2018-11-10 16:17
 * @desc 消费端引入服务提供者时候需要的Factorybean
 */
public class RevokerFactoryBean implements FactoryBean, InitializingBean {

    //下面这几个属性是xml里面配置的
    private Class<?> targetInterface;   //服务接口
    private int timeout;  //超时时间
    private String clusterStrategy; //负载均衡策略
    private String remoteAppKey;    //服务提供者唯一标识
    private String groupName = "default";    //服务分组组名

    private Object serviceObject;    //返回给消费端的代理对象bean

    @Override
    public Object getObject() throws Exception {
        return serviceObject;
    }

    @Override
    public Class<?> getObjectType() {
        return targetInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IRegisterCenter4Invoker registerCenter4Consumer = RegisterCenter.singleton();  //获取服务注册中心
        registerCenter4Consumer.initProviderMap(remoteAppKey, groupName);        //初始化服务提供者列表到本地缓存
         //初始化Netty Channel
        Map<String, List<ProviderService>> providerMap = registerCenter4Consumer.getServiceMetaDataMap4Consume();
        if (MapUtils.isEmpty(providerMap)) {
            throw new RuntimeException("service provider list is empty.");
        }
        NettyChannelPoolFactory.channelPoolFactoryInstance().initChannelPoolFactory(providerMap);
        //获取服务提供者代理对象
        RevokerProxyBeanFactory proxyFactory = RevokerProxyBeanFactory.singleton(targetInterface, timeout, clusterStrategy);
        this.serviceObject = proxyFactory.getProxy();

        //将消费者信息注册到注册中心
        InvokerService invoker = new InvokerService();
        invoker.setServiceItf(targetInterface);
        invoker.setRemoteAppKey(remoteAppKey);
        invoker.setGroupName(groupName);
        registerCenter4Consumer.registerInvoker(invoker);
    }
}
