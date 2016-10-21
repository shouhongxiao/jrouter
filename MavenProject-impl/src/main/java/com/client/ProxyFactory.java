package com.client;

import com.annotation.PathParam;
import com.annotation.RegisterAddrAnnotation;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by shouh on 2016/10/10.
 */
public class ProxyFactory {
    public static <T> T getProxy(Class<T> classType,
                                 final String className
    ) {
        return Reflection.newProxy(classType, (proxy, method, args) -> {
            RegisterAddrAnnotation registerAddr = method.getAnnotation(RegisterAddrAnnotation.class);

            System.out.println("--- before " + method.getName() + "---");
            String url = buildUrl(method, args);
            String json = "";
            String key = registerAddr.value();
            String addrs = getBaseAddrs(key);
            if (!Strings.isNullOrEmpty(url)) {
                addrs = addrs + key + url;
            }
            if (!Strings.isNullOrEmpty(addrs)) {
                try {
                    HttpGet getDeviceInfo = new HttpGet(addrs);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse deviceResponse = httpclient.execute(getDeviceInfo);
                    int statusCode = deviceResponse.getStatusLine().getStatusCode();
                    if (statusCode != 200) {
                        throw new RuntimeException("Oh dear no device information, status code " + statusCode);
                    }
                    json = EntityUtils.toString(deviceResponse.getEntity());
                } catch (Exception oe) {
                    json = oe.getMessage();
                }
            }
            return json;
        });
    }

    private static String getBaseAddrs(String keyName) {
        try {
            return ServerRouter.getServer(keyName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String buildUrl(Method method, Object[] args) {
        StringBuffer stringBuffer = new StringBuffer();
        String param = "?";
        int i = 0;
        Map<String, String> maps = Maps.newHashMap();
        final Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            for (Parameter parameter : parameters) {
                PathParam pathParam = parameter.getAnnotation(PathParam.class);
                if (pathParam != null && !Strings.isNullOrEmpty(pathParam.value())) {
                    String name = pathParam.value();
                    maps.put(name, args[i++].toString());
                }
            }
        }
        param += Joiner.on("&").withKeyValueSeparator("=").join(maps);
        return param;
    }
}
