package com.rz.rpc.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by as on 2018/1/17.
 */
public class RZRPCRequest implements Serializable{
    private String interfaceName;
    private String methodName;
    private List<Object> params;
    private String version;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
