package com.rz.rpc.framework.revoker;

import com.rz.rpc.framework.model.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author as
 * @create 2018-11-10 16:37
 * @desc
 */
public class NettyChannelPoolFactory {
    private static final Logger logger = LoggerFactory.getLogger(NettyChannelPoolFactory.class);

    private static final NettyChannelPoolFactory channelPoolFactory = new NettyChannelPoolFactory();

    public static NettyChannelPoolFactory channelPoolFactoryInstance() {
        return channelPoolFactory;
    }

    public void initChannelPoolFactory(Map<String, List<ProviderService>> providerMap) {

    }
}
