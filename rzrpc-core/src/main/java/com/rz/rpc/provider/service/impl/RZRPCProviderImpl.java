package com.rz.rpc.provider.service.impl;

import com.rz.rpc.provider.dao.ProviderDao;
import com.rz.rpc.provider.dao.ProviderDaoByNetty;
import com.rz.rpc.provider.dao.ProviderDaoByNio;
import com.rz.rpc.provider.dao.impl.ProviderDaoImpl;
import com.rz.rpc.provider.dao.impl.ProviderDaoImplByNetty;
import com.rz.rpc.provider.dao.impl.ProviderDaoImplByNio;
import com.rz.rpc.provider.service.RZRPCProvider;

/**
 * Created by as on 2018/1/17.
 */
public class RZRPCProviderImpl implements RZRPCProvider {

    /**
     * 以下变量为发布一个服务的必要变量
     */
       private String interfaceName;//服务对应接口的全限定名
    private String version;//服务版本号
    private String implClassName;//实现该服务接口的类
    private String ip;//发布该服务的地址

    private static boolean isListened = false;//是否已经开启监听

    private ProviderDao providerDao;
    private ProviderDaoByNio providerDaoByNio;
    private ProviderDaoByNetty providerDaoByNetty;

    public RZRPCProviderImpl() {
        providerDao = new ProviderDaoImpl();
        providerDaoByNio = new ProviderDaoImplByNio();
        providerDaoByNetty = new ProviderDaoImplByNetty();
        //开启服务监听端口
        if (!isListened) {
            //BIO方式启动服务
//            if (providerDao.startListen())
//                isListened = true;
//            else throw new RuntimeException();
            //NIO方式启动
//            if (providerDaoByNio.startListenByNio())
//                isListened = true;
//            else throw new RuntimeException();
            //netty方式启动
            if (providerDaoByNetty.startListenByNetty())
                isListened = true;
            else throw new RuntimeException();
        }
    }

    public void checkInfo() throws Exception {
        //先判断服务参数信息是否完整
        if (interfaceName == null || interfaceName.length() == 0 ||
                version == null || version.length() == 0 ||
                implClassName == null || implClassName.length() == 0 ||
                ip == null || ip.length() == 0)
            throw new RuntimeException("信息不完整.....");
    }

    public boolean servicePublish() throws Exception {
        checkInfo();
        //step1. 注册服务.注册服务之前先判断服务是否开启,若没有开启,则首先开启服务
        synchronized (RZRPCProviderImpl.class) {
            if (!isListened) {
                //BIO方式启动服务
//                if (providerDao.startListen())
//                    isListened = true;
//                else throw new RuntimeException("启动失败...");
                //NIO方式启动服务
//                if (providerDaoByNio.startListenByNio())
//                    isListened = true;
//                else throw new RuntimeException("启动失败...");
                //netty方式启动
                if (providerDaoByNetty.startListenByNetty())
                    isListened = true;
                else throw new RuntimeException();
            }
            providerDao.registryService(interfaceName, version, implClassName, ip);
        }
        return true;
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

    public String getImplClassName() {
        return implClassName;
    }

    public void setImplClassName(String implClassName) {
        this.implClassName = implClassName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
