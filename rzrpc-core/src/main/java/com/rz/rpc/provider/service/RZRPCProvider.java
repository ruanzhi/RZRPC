package com.rz.rpc.provider.service;

/**
 * Created by as on 2018/1/17.
 */
public interface RZRPCProvider {

    public void checkInfo() throws Exception;

    /**
     * 发布服务
     *
     * @return
     * @throws Exception
     */
    public boolean servicePublish() throws Exception;
}
