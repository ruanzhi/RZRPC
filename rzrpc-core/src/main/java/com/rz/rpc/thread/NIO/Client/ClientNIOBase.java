package com.rz.rpc.thread.NIO.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by as on 2018/1/20.
 */
public class ClientNIOBase {
    // 线程中的通道管理器
    public Selector selector;

    /**
     * 初始化 该线程中的通道管理器Selector
     */
    public void initSelector() throws IOException {
        this.selector = Selector.open();
    }

    /**
     * 采用轮询的方式监听selector上是否有需要处理的事件,如果有,则循环处理
     * 这里主要监听连接事件以及读事件
     */
    public Object listen() throws Exception {

        //轮询访问select
        boolean flag = true;
        Object result = null;
        while(flag){
            //当注册的事件到达时,方法返回;否则将一直阻塞
            selector.select();
            //获得selector中选中的项的迭代器,选中的项为注册的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            //循环处理注册事件
            /**
             * 一共有四种事件:
             * 1. 服务端接收客户端连接事件: SelectionKey.OP_ACCEPT
             * 2. 客户端连接服务端事件:    SelectionKey.OP_CONNECT
             * 3. 读事件:                SelectionKey.OP_READ
             * 4. 写事件:                SelectionKey.OP_WRITE
             */
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                //手动删除已选的key,以防重复处理
                iterator.remove();
                //判断事件性质
                if (key.isReadable()){//读事件
                    result = read(key);
                    flag = false;
                    break;
                }else if (key.isConnectable()) {//客户端连接事件
                    connect(key);
                }
            }
        }
        return result;
    }

    /**
     * 当监听到读事件后的处理函数
     *
     * @param key 事件key,可以从key中获取channel,完成事件的处理
     */
    public Object read(SelectionKey key) throws Exception {

        return null;
    }


    /**
     * 当监听到服务端接收客户端连接事件后的处理函数
     *
     * @param key 事件key,可以从key中获取channel,完成事件的处理
     */
    public void accept(SelectionKey key) throws IOException {
    }

    /**
     * 当监听到客户端连接事件后的处理函数
     *
     * @param key 事件key,可以从key中获取channel,完成事件的处理
     */
    public void connect(SelectionKey key) throws IOException {
    }
}
