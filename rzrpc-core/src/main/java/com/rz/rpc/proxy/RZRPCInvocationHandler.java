package com.rz.rpc.proxy;

import com.rz.rpc.consumer.dao.ConsumerDao;
import com.rz.rpc.consumer.dao.impl.ConsumerDaoImpl;
import com.rz.rpc.domain.RZRPCRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by as on 2018/1/19.
 */
public class RZRPCInvocationHandler implements InvocationHandler {
    private String interfaceName;//接口的全限定名
    private String version;//服务版本号
    private ConsumerDao consumerDao;//初始化客户端辅助类

    public RZRPCInvocationHandler(String interfaceName, String version) {
        this.interfaceName = interfaceName;
        this.version = version;
        this.consumerDao = new ConsumerDaoImpl();
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法的名称
        String methodName = method.getName();
        //方法的返回类型
        Class returnType = method.getReturnType();
        //Object[]转换为list<Object>
        List<Object> params = new ArrayList<Object>();
        for (Object arg : args) {
            params.add(arg);
        }

        if (interfaceName == null || interfaceName.length() == 0
                || version == null || version.length() == 0)
            throw new RuntimeException("参数错误");
        //根据参数获取服务的ip地址列表
        String serviceID = interfaceName + "_" + version;
        Set<String> ips = consumerDao.getServiceIPsByID(serviceID);
        if (ips == null || ips.size() == 0)
            throw new RuntimeException("没有发现提供此服务的服务地址");
        //step2. 路由,获取该服务的地址,路由的结果会返回至少一个地址,所以这里不需要抛出异常
        String serviceAddress = consumerDao.getIP(serviceID, methodName, params, ips);
        //step3. 根据传入的参数,拼装Request对象,这里一定会返回一个合法的request对象,所以不需要抛出异常
        RZRPCRequest requestDO = consumerDao.getRequestDO(interfaceName, version, methodName, params);
        //step3. 传入Request对象,序列化并传入服务端,拿到响应后,反序列化为object对象
        Object result = null;
        try {
            //  result = consumerDao.sendDataByBIO(serviceAddress, requestDO);
            //  result = consumerDao.sendDataByNIO(serviceAddress, requestDO);
            result = consumerDao.sendDataByNetty(serviceAddress, requestDO);
        } catch (Exception e) {
            //在服务调用的过程种出现问题
            throw new RuntimeException(e.getMessage());
        }
        if (result == null)
            throw new RuntimeException("调用服务错误");
        //step4. 返回object对象
        return result;
    }
}
