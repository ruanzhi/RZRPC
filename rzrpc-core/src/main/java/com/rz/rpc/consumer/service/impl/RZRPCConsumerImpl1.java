package com.rz.rpc.consumer.service.impl;

import com.rz.rpc.proxy.RZRPCInvocationHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * Created by as on 2018/1/19.
 */
public class RZRPCConsumerImpl1 implements FactoryBean {
    /**
     * 以下两个变量共同组成服务的唯一标识
     */
    private String interfaceName;//服务对应接口的全限定名
    private String version;//服务版本号


    public Object getObject() throws Exception {
        return getProxy();
    }

    public Class<?> getObjectType() {
        try {
            return Class.forName(interfaceName).getClass();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSingleton() {
        return false;
    }

    public Object getProxy() throws ClassNotFoundException {
        Class clz = Class.forName(interfaceName);
        return Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new RZRPCInvocationHandler(interfaceName, version));
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
