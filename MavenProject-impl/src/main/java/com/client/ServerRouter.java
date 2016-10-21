package com.client;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zkwatch.ConfigWatcher;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by shouh on 2016/9/29.
 */
public class ServerRouter {
    private static Integer pos = 0;
    static Map<String, List<String>> serverMap = Maps.newConcurrentMap();
    static ConfigWatcher configWatcher = null;
    static IRouterStrategy roundRouter = new RoundRouter();
    static final String baseAddr = "/server/prov";

    static {
        try {
            configWatcher = new ConfigWatcher();
            List<String> list = configWatcher.displayConfig(baseAddr, null);
            for (String key1 : list) {
                loadlistBykey(baseAddr + "/" + key1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    private static void loadlistBykey(String key) throws KeeperException, InterruptedException {
        if (!Strings.isNullOrEmpty(key)) {
            List<String> list = configWatcher.displayConfig(key, s -> {
                act(key, s);
            });
            act(key, list);
        }
    }

    private static void act(String key, List<String> stringList) {
        if (stringList != null) {
            serverMap.put(key, stringList);
            System.out.println(key + stringList.size() + "个地址被加入");
            roundRouter.setServereMap(serverMap);
        }
    }

    public static String getServer(String key) throws InterruptedException, IOException, KeeperException {

        return roundRouter.getServerAddr(baseAddr + "/" + key);
    }
}
