package com.rz.rpc.thread.NIO.Server;

import com.rz.rpc.domain.RZRPCRequest;
import com.rz.rpc.domain.ServiceInfo;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.HttpClientUtil;
import com.rz.rpc.utils.JsonUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Created by as on 2018/1/20.
 * <p>
 * 真正调用服务端函数获取结果返回
 */
public class NIOServerService {
    public Object getFuncCalldata(RZRPCRequest rzrpcRequest) throws Exception {
        //求注册中心获取对应的注册了服务的信息
        String serviceId = rzrpcRequest.getInterfaceName() + "_" + rzrpcRequest.getVersion();
        String url = "http://" + Constant.SERVICEACCESSCENTER_IP + ":" + Constant.SERVICEACCESSCENTER_PORT + "/"
                + Constant.QUERYSERVICEINFOBYID + "?serviceID=" + serviceId;
        String result = HttpClientUtil.doGet(url);
        ServiceInfo serviceInfo = JsonUtils.jsonToPojo(result, ServiceInfo.class);
        //step3.利用反射创建对象,调用方法,得到结果
        Class clz = Class.forName(serviceInfo.getImplClassName());
        Method methodethod = null;
        Object res = null;
        if (rzrpcRequest.getParams() != null && rzrpcRequest.getParams().size() > 0) {
            Class[] classes = new Class[rzrpcRequest.getParams().size()];
            Object[] obj = rzrpcRequest.getParams().toArray();
            int i = 0;
            for (Object object : rzrpcRequest.getParams()) {
                if (object instanceof Integer) {
                    classes[i] = Integer.TYPE;
                } else if (object instanceof Byte) {
                    classes[i] = Byte.TYPE;
                } else if (object instanceof Short) {
                    classes[i] = Short.TYPE;
                } else if (object instanceof Float) {
                    classes[i] = Float.TYPE;
                } else if (object instanceof Double) {
                    classes[i] = Double.TYPE;
                } else if (object instanceof Character) {
                    classes[i] = Character.TYPE;
                } else if (object instanceof Long) {
                    classes[i] = Long.TYPE;
                } else if (object instanceof Boolean) {
                    classes[i] = Boolean.TYPE;
                } else {
                    classes[i] = object.getClass();
                }

                i++;
            }
            methodethod = clz.getDeclaredMethod(rzrpcRequest.getMethodName(), classes);
            res = methodethod.invoke(clz.newInstance(), obj);
        } else {
            methodethod = clz.getDeclaredMethod(rzrpcRequest.getMethodName());
            res = methodethod.invoke(clz.newInstance());
        }
        return res;
    }

}
