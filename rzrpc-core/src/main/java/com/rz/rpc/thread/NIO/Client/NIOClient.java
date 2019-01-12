package com.rz.rpc.thread.NIO.Client;


import com.rz.rpc.domain.RZRPCRequest;
import com.rz.rpc.thread.NIO.Client.ClientNIOBase;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.ObjectAndByteUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2018/1/20.
 */
public class NIOClient extends ClientNIOBase {

    private RZRPCRequest requestDO;
    private String serviceAddress;

    public NIOClient(RZRPCRequest requestDO, String serviceAddress) {
        this.requestDO = requestDO;
        this.serviceAddress = serviceAddress;

    }

    public Object run() {
        try {
            initSelector();//初始化通道管理器
            initClient(serviceAddress, Constant.PORT);//初始化客户端连接scoketChannel
            return listen();//开始轮询处理事件
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object read(SelectionKey key) throws Exception {

        //step1. 得到事件发生的通道
        byte[] result = ObjectAndByteUtil.getReadData(key);
        if (result == null) return null;
        Object object = ObjectAndByteUtil.toObject(result);
        //关闭连接
        SocketChannel socketChannel = (SocketChannel) key.channel();
        key.channel();
        socketChannel.socket().close();
        socketChannel.close();
        this.selector.close();
        return object;
    }


    private void initClient(String ip, int port) throws IOException {
        //step1. 获得一个SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //step2. 初始化该channel
        socketChannel.configureBlocking(false);//设置通道为非阻塞
        //step3. 客户端连接服务器,其实方法执行并没有实现连接,需要再listen()方法中调用channel.finishConnect()方法才能完成连接
        socketChannel.connect(new InetSocketAddress(ip, port));
        //step4. 注册该channel到selector中,并为该通道注册SelectionKey.OP_CONNECT事件和SelectionKey.OP_READ事件
        socketChannel.register(this.selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
    }

    /**
     * 当监听到客户端连接事件后的处理函数
     *
     * @param key 事件key,可以从key中获取channel,完成事件的处理
     */
    @Override
    public void connect(SelectionKey key) throws IOException {
        //step1. 获取事件中的channel
        SocketChannel socketChannel = (SocketChannel) key.channel();
        //step2. 如果正在连接,则完成连接
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);//将连接设置为非阻塞
        //step3. 连接后,可以给服务端发送消息
        socketChannel.write(ByteBuffer.wrap(ObjectAndByteUtil.toByteArray(requestDO)));
    }
}
