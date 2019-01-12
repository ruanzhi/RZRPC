package com.rz.rpc.framework.model;

import java.io.Serializable;

/**
 * @author as
 * @create 2018-11-10 9:58
 * @desc 客户端提交过来的请求
 */
public class RzRequest implements Serializable {

    private String uniqueKey;//UUID 唯一标识一次返回值
    private ProviderService providerService;//服务端提供者信息
    private String invokedMethodName;//调用名称
    private Object[] args;//传递参数
    private String appName;//消费端名称
    private long invokeTimeout;//消费请求超时时长 注意不是方法执行的超时时长。

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public ProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
    }

    public String getInvokedMethodName() {
        return invokedMethodName;
    }

    public void setInvokedMethodName(String invokedMethodName) {
        this.invokedMethodName = invokedMethodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(long invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }
}
