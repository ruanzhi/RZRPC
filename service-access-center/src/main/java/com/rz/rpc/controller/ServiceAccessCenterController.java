package com.rz.rpc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rz.rpc.domain.ServiceInfo;
import com.rz.rpc.service.ServiceAccess;
import com.rz.rpc.utils.ServicesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by as on 2018/1/17.
 */
@Controller
@RequestMapping("/ServiceAccessCenter")
public class ServiceAccessCenterController {

    @Resource
    private ServiceAccess serviceAccess;

    @RequestMapping("/registryService")
    @ResponseBody
    public String registryService(ServiceInfo serviceInfo) {
        boolean result = serviceAccess.registryService(serviceInfo);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        return jsonObject.toString();
    }

    @RequestMapping("/queryServiceIPsByID")
    @ResponseBody
    public String queryServiceIPsByID(String serviceID) {

        Set<String> ips = serviceAccess.queryServiceIPsByID(serviceID);
        JSONArray jsonArray = new JSONArray();
        for (String ip : ips) {
            jsonArray.add(ip);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ips", jsonArray);
        return jsonObject.toString();
    }

    @RequestMapping("/queryServiceInfoByID")
    @ResponseBody
    public ServiceInfo queryServiceInfoByID(String serviceID) {
        return serviceAccess.queryServiceInfoByID(serviceID);
    }
}
