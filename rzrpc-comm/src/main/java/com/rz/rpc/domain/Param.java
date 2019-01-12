package com.rz.rpc.domain;

import java.io.Serializable;

/**
 * Created by as on 2018/1/18.
 */
public class Param implements Serializable{
    private double n1;
    private double n2;

    public double getN1() {
        return n1;
    }

    public void setN1(double n1) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2(double n2) {
        this.n2 = n2;
    }
}
