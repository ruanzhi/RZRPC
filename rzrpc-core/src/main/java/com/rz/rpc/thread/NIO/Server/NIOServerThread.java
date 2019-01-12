package com.rz.rpc.thread.NIO.Server;

import com.rz.rpc.domain.RZRPCRequest;

import com.rz.rpc.thread.NIO.Server.NIOServerService;
import com.rz.rpc.thread.NIO.Server.ServerNIOBase;
import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.ObjectAndByteUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by as on 2018/1/20.
 */
public class NIOServerThread extends ServerNIOBase implements Runnable {
    public void run() {
        try {
            initSelector();//初始化通道管理器Selector
            initServer(Constant.IP, Constant.PORT);//初始化ServerSocketChannel,开启监听
            listen();//轮询处理Selector选中的事件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initServer(String ip, int port) throws IOException {
        //step1. 获得一个ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //step2. 初始化工作
        serverSocketChannel.configureBlocking(false);//设置通道为非阻塞
        serverSocketChannel.socket().bind(new InetSocketAddress(ip, port));

        //step3. 将该channel注册到Selector上,并为该通道注册SelectionKey.OP_ACCEPT事件
        //这样一来,当有"服务端接收客户端连接"事件到达时,selector.select()方法会返回,否则将一直阻塞
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 当监听到服务端接收客户端连接事件后的处理函数
     *
     * @param key 事件key,可以从key中获取channel,完成事件的处理
     */
    public void accept(SelectionKey key) throws IOException {

        //step1. 获取serverSocketChannel
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

        //step2. 获得和客户端连接的socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);//设置为非阻塞
        //step3. 注册该socketChannel
        socketChannel.register(selector, SelectionKey.OP_READ);//为了接收客户端的消息,注册读事件
    }

    public void read(SelectionKey key) throws Exception {
        byte[] result = ObjectAndByteUtil.getReadData(key);
        if (result == null) return;
        SocketChannel socketChannel = (SocketChannel) key.channel();

        RZRPCRequest request = (RZRPCRequest) ObjectAndByteUtil.toObject(result);
        NIOServerService nioServerService = new NIOServerService();
        //将结果写回
        socketChannel.write(ByteBuffer.wrap(ObjectAndByteUtil.toByteArray(nioServerService.getFuncCalldata(request))));

    }
}
