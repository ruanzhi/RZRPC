package com.rz.rpc.framework.serialization.serializer;


/**
 * @author as
 * @create 2018-10-31 20:56
 * @desc 序列化接口
 */
public interface ISerializer {


    /**
     * 序列化一个类
     *
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化一个类
     *
     * @param data  需要反序列化的数据
     * @param clazz  反序列化对应的class类型
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);
}
