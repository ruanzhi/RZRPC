package com.rz.rpc.service;

import com.rz.rpc.domain.ServiceInfo;

import java.util.Set;

/**
 * Created by as on 2018/1/17.
 */
public interface ServiceAccess {

    /**
     * 根据用户提供的注册信息，进行注册服务
     *
     * @param serviceInfo
     * @return
     */
    public boolean registryService(ServiceInfo serviceInfo);

    /**
     * 根据服务id查询提供服务的ip地址列表
     *
     * @param serviceID
     * @return
     */
    public Set<String> queryServiceIPsByID(String serviceID);

    /**
     * 根据服务的唯一标识ID查询服务的信息
     *
     * @param serviceID
     * @return
     */
    public ServiceInfo queryServiceInfoByID(String serviceID);

}
