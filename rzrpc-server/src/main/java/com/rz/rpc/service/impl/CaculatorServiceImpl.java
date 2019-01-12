package com.rz.rpc.service.impl;

import com.rz.rpc.domain.Param;
import com.rz.rpc.domain.Result;
import com.rz.rpc.facade.CaculatorService;


/**
 * Created by as on 2018/1/18.
 */
public class CaculatorServiceImpl implements CaculatorService {
    public double add(double n1, double n2) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return n1 + n2;
    }

    public Result multiply(Param Param) {

        Result result = new Result();
        result.setResult(Param.getN1() * Param.getN2());
        return result;
    }
}
