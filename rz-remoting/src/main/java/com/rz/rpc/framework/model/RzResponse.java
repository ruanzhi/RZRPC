package com.rz.rpc.framework.model;

import java.io.Serializable;

/**
 * @author as
 * @create 2018-11-10 11:58
 * @desc 服务端返回结果
 */
public class RzResponse implements Serializable {
    //UUID,唯一标识一次返回值
    private String uniqueKey;
    //客户端指定的服务请求超时时间
    private long invokeTimeout;
    //接口调用返回的结果对象
    private Object result;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public long getInvokeTimeout() {
        return invokeTimeout;
    }

    public void setInvokeTimeout(long invokeTimeout) {
        this.invokeTimeout = invokeTimeout;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
