package com.rz.rpc.consumer.dao;

import com.rz.rpc.domain.RZRPCRequest;

import java.util.List;
import java.util.Set;

/**
 * Created by as on 2018/1/17.
 */
public interface ConsumerDao {
    /**
     * 根据服务id获取对应的服务ip地址列表
     *
     * @param serviceId
     * @return
     */
    public Set<String> getServiceIPsByID(String serviceId);

    /**
     * 根据参数获取一个提供服务ip地址
     *
     * @param serviceID
     * @param methodName
     * @param params
     * @param ips
     * @return
     */
    String getIP(String serviceID, String methodName, List<Object> params, Set<String> ips);

    /**
     * 封装一个请求实体
     *
     * @param interfaceName
     * @param version
     * @param methodName
     * @param params
     * @return
     */
    RZRPCRequest getRequestDO(String interfaceName, String version, String methodName, List<Object> params);

    /**
     * BIO方式条用远程调用服务端
     *
     * @param serviceAddress
     * @param requestDO
     * @return
     */
    Object sendDataByBIO(String serviceAddress, RZRPCRequest requestDO) throws Exception;

    /**
     * NIO方式条用远程调用服务端
     *
     * @param serviceAddress
     * @param requestDO
     * @return
     * @throws Exception
     */
    Object sendDataByNIO(String serviceAddress, RZRPCRequest requestDO) throws Exception;

    /**
     * Netty方式条用远程调用服务端
     *
     * @param serviceAddress
     * @param requestDO
     * @return
     * @throws Exception
     */
    Object sendDataByNetty(String serviceAddress, RZRPCRequest requestDO) throws Exception;
}
