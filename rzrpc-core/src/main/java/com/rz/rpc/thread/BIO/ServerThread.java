package com.rz.rpc.thread.BIO;

import com.rz.rpc.thread.BIO.ServerProcessThread;
import com.rz.rpc.utils.Constant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by as on 2018/1/17.
 */
public class ServerThread implements Runnable {
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Constant.PORT);

            System.out.println("已经开始监听,可以注册服务了");
            while (true) {
                Socket socket = serverSocket.accept();//阻塞到这里
                new Thread(new ServerProcessThread(socket)).start();//开启新的线程进行连接请求的处理
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
