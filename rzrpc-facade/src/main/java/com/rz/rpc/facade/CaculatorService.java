package com.rz.rpc.facade;

import com.rz.rpc.domain.Param;
import com.rz.rpc.domain.Result;

/**
 * Created by as on 2018/1/18.
 * 服务对应接口
 * 该服务实现一个简单的计算器服务,实现加减乘除四个基本功能
 */
public interface CaculatorService {

    public double add(double n1, double n2);

    public Result multiply(Param Param);
}
