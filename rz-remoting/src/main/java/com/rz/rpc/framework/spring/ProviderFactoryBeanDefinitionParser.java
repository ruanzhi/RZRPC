package com.rz.rpc.framework.spring;

import com.rz.rpc.framework.provider.ProviderFactoryBean;
import com.rz.rpc.framework.revoker.RevokerFactoryBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author as
 * @create 2018-11-10 16:30
 * @desc 返回服务端对应的Factorybean
 */
public class ProviderFactoryBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    private static final Logger logger = LoggerFactory.getLogger(ProviderFactoryBeanDefinitionParser.class);

    protected Class getBeanClass(Element element) {
        return ProviderFactoryBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        try {
            String serviceItf = element.getAttribute("interface");
            String timeOut = element.getAttribute("timeout");
            String serverPort = element.getAttribute("serverPort");
            String ref = element.getAttribute("ref");
            String weight = element.getAttribute("weight");
            String workerThreads = element.getAttribute("workerThreads");
            String appKey = element.getAttribute("appKey");
            String groupName = element.getAttribute("groupName");

            bean.addPropertyValue("serverPort", Integer.parseInt(serverPort));
            bean.addPropertyValue("timeout", Integer.parseInt(timeOut));
            bean.addPropertyValue("serviceItf", Class.forName(serviceItf));
            bean.addPropertyReference("serviceObject", ref);
            bean.addPropertyValue("appKey", appKey);

            if (NumberUtils.isNumber(weight)) {
                bean.addPropertyValue("weight", Integer.parseInt(weight));
            }
            if (NumberUtils.isNumber(workerThreads)) {
                bean.addPropertyValue("workerThreads", Integer.parseInt(workerThreads));
            }
            if (StringUtils.isNotBlank(groupName)) {
                bean.addPropertyValue("groupName", groupName);
            }
        } catch (Exception e) {
            logger.error("ProviderFactoryBeanDefinitionParser error.", e);
            throw new RuntimeException(e);
        }
    }
}
