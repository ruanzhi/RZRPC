package com.rz.rpc.consumer.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rz.rpc.consumer.dao.ConsumerDao;
import com.rz.rpc.domain.RZRPCRequest;
import com.rz.rpc.thread.NIO.Client.NIOClient;
import com.rz.rpc.thread.Netty.NettyClient;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.HttpClientUtil;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by as on 2018/1/17.
 */
public class ConsumerDaoImpl implements ConsumerDao {

    public Set<String> getServiceIPsByID(String serviceId) {
        //调用服务注册查找中心的服务,获取ip列表
        Set<String> ips = new HashSet<String>();
        String url = "http://" + Constant.SERVICEACCESSCENTER_IP + ":" + Constant.SERVICEACCESSCENTER_PORT + "/"
                + Constant.QUERYSERVICEIPSBYID + "?serviceID=" + serviceId;

        String result = HttpClientUtil.doGet(url);
        System.out.println(result);
        if (result == null || "".equals(result)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("ips");
        for (int i = 0; i < jsonArray.size(); i++) {
            String ip = jsonArray.getString(i);
            ips.add(ip);
        }
        return ips;
    }

    /**
     * 得到ip
     *
     * @param serviceID
     * @param methodName
     * @param params
     * @param ips
     * @return
     */
    public String getIP(String serviceID, String methodName, List<Object> params, Set<String> ips) {
        //可以根据接口\方法\参数进行路由,这里我们先简单实现,选出列表的第一个,模拟路由的过程
        String[] temparr = new String[ips.size()];
        ips.toArray(temparr);
        return temparr[0];
    }

    public RZRPCRequest getRequestDO(String interfaceName, String version, String methodName, List<Object> params) {
        RZRPCRequest requestDO = new RZRPCRequest();
        requestDO.setInterfaceName(interfaceName);
        requestDO.setMethodName(methodName);
        requestDO.setParams(params);
        requestDO.setVersion(version);
        return requestDO;
    }

    public Object sendDataByBIO(String serviceAddress, RZRPCRequest requestDO) throws Exception {
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(serviceAddress, Constant.PORT);//向远程服务端建立连接
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());//获得输出流
            objectOutputStream.writeObject(requestDO);//发送序列化结果
            objectOutputStream.flush();
            socket.shutdownOutput();
            //等待响应
            objectInputStream = new ObjectInputStream(socket.getInputStream());//获得输入流
            Object result = objectInputStream.readObject();//序列化为Object对象
            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (objectInputStream != null)
                    objectInputStream.close();
                if (objectOutputStream != null)
                    objectOutputStream.close();
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        }

    }

    public Object sendDataByNIO(String serviceAddress, RZRPCRequest requestDO) throws Exception {
        NIOClient nioClient = new NIOClient(requestDO, serviceAddress);
        return nioClient.run();
    }

    public Object sendDataByNetty(String serviceAddress, RZRPCRequest requestDO) throws Exception {
        NettyClient nettyClient = new NettyClient(requestDO, serviceAddress);
        return nettyClient.connect();
    }
}
