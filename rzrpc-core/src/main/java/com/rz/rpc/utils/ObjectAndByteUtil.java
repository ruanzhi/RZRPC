package com.rz.rpc.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2018/1/20.
 */
public class ObjectAndByteUtil {
    public static Object toObject(byte[] result) {
        Object object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static byte[] toByteArray(Object obj) {

        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static byte[] getReadData(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        try {
            int len = socketChannel.read(byteBuffer);
            if (len == -1) {
                socketChannel.close();
                return null;//说明连接已经断开
            }
            int pos = 0;
            List<byte[]> list = new ArrayList<byte[]>();
            while (len > 0) {
                pos += len;
                byteBuffer.flip();
                byte[] arr = new byte[len];
                byteBuffer.get(arr, 0, len);
                list.add(arr);
                byteBuffer.clear();
                len = socketChannel.read(byteBuffer);
            }

            byte[] result = new byte[pos];
            int l = 0;
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).length; j++) {
                    result[l + j] = list.get(i)[j];
                }
                l += list.get(i).length;
            }
            return result;

        } catch (Exception e) {
            System.out.println("客户端异常关闭......");
            key.cancel();
            socketChannel.socket().close();
            socketChannel.close();
            return null;
        }
    }
}
