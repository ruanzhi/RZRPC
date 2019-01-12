package com.rz.rpc.framework.serialization.serializer.impl;

import com.rz.rpc.framework.serialization.serializer.ISerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author as
 * @create 2018-10-31 22:26
 * @desc Xml方式序列化
 */
public class XmlSerializer implements ISerializer {

    private static final XStream xStream = new XStream(new DomDriver());


    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }

    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String xml = new String(data);
        return (T) xStream.fromXML(xml);
    }


}
