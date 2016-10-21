package com.demo;

import com.client.IService;
import com.client.ProxyFactory;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by shouh on 2016/10/10.
 */
public class SimpleClient {
    public void invoke() {
        IService hello = ProxyFactory.getProxy(
                IService.class, "com.client.ServiceImpl");
        String result = hello.sayHello("sayHello");
        System.out.println("result=" + result);
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {

        CountDownLatch connectedSignal = new CountDownLatch(1);
        SimpleClient client = new SimpleClient();
        client.invoke();
        connectedSignal.await();
    }
}
