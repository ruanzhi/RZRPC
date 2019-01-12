package com.rz.rpc.thread.Netty;

import com.rz.rpc.domain.RZRPCRequest;
import com.rz.rpc.utils.ObjectAndByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by as on 2018/1/21.
 */
public class NettyServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        if (req == null) return;
        RZRPCRequest request = (RZRPCRequest) ObjectAndByteUtil.toObject(req);
        NettyServerService nettyServerService = new NettyServerService();
        ByteBuf resp = Unpooled.copiedBuffer(ObjectAndByteUtil.toByteArray(nettyServerService.getFuncCalldata(request)));
        ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
