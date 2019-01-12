package com.rz.rpc.thread.Netty;

import com.rz.rpc.utils.Constant;
import com.rz.rpc.utils.ObjectAndByteUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;

/**
 * Created by as on 2018/1/21.
 */
public class NettyClient {
    private Object reqObj;//封装的请求
    private String ip;

    public NettyClient(Object reqObj, String ip) {
        this.reqObj = reqObj;
        this.ip = ip;
    }

    public Object connect() throws InterruptedException, UnsupportedEncodingException {
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            byte[] req = ObjectAndByteUtil.toByteArray(reqObj);
            final NettyClientHandler nettyClientHandler = new NettyClientHandler(req);
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(nettyClientHandler);
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(ip, Constant.PORT).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
            //拿到异步请求结果,返回
            Object responseObj = ObjectAndByteUtil.toObject(nettyClientHandler.response);
            return responseObj;

        } finally {
            //优雅退出,释放NIO线程组
            group.shutdownGracefully();
        }
    }

}
