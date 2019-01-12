package com.rz.rpc.framework.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author as
 * @create 2018-11-10 16:01
 * @desc 消费端自定义标签名称空间处理器
 */
public class RemoteReferenceNamespaceHandler extends NamespaceHandlerSupport  {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new RevokerFactoryBeanDefinitionParser());
    }
}
