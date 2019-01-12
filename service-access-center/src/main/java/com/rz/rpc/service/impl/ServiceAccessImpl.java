package com.rz.rpc.service.impl;

import com.rz.rpc.domain.ServiceInfo;
import com.rz.rpc.service.ServiceAccess;
import com.rz.rpc.utils.ServicesUtil;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by as on 2018/1/17.
 */
@Service("serviceAccess")
public class ServiceAccessImpl implements ServiceAccess {


    public boolean registryService(ServiceInfo serviceInfo) {
        if (serviceInfo.getInterfaceName() == null || serviceInfo.getInterfaceName().length() == 0 ||
                serviceInfo.getImplClassName() == null || serviceInfo.getImplClassName().length() == 0 ||
                serviceInfo.getVersion() == null || serviceInfo.getVersion().length() == 0 ||
                serviceInfo.getIp() == null || serviceInfo.getIp().length() == 0)
            return false;

        String serviceId = serviceInfo.getInterfaceName() + "_" + serviceInfo.getVersion();

        if (ServicesUtil.getServiceSingle().getServices().containsKey(serviceId)) {
            //以后注册只需要更新提供服务ip地址列表
            ServicesUtil.getServiceSingle().getServices().get(serviceId).getIps().add(serviceInfo.getIp());
        } else {
            //第一次注册
            serviceInfo.getIps().add(serviceInfo.getIp());
            ServicesUtil.getServiceSingle().getServices().put(serviceId, serviceInfo);
        }
        return true;

    }

    public Set<String> queryServiceIPsByID(String serviceID) {
        if (!ServicesUtil.getServiceSingle().getServices().containsKey(serviceID))
            return null;
        return ServicesUtil.getServiceSingle().getServices().get(serviceID).getIps();
    }

    public ServiceInfo queryServiceInfoByID(String serviceID) {
        if (!ServicesUtil.getServiceSingle().getServices().containsKey(serviceID))
            return null;
        return ServicesUtil.getServiceSingle().getServices().get(serviceID);
    }
}
