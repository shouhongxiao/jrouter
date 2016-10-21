package com.client;

import java.util.List;
import java.util.Map;

/**
 * Created by shouh on 2016/10/19.
 */
public class RoundRouter implements IRouterStrategy {

    private Map<String, List<String>> servereMap = null;
    private static Integer pos = 0;

    @Override
    public String getServerAddr(String key) {
        List<String> servers = servereMap.get(key);
        String server = null;
        if (servers != null && servers.size() > 0) {
            synchronized (pos) {
                if (pos > servers.size())
                    pos = 0;
                server = servers.get(pos);
                pos++;
            }
        }
        return server;
    }

    @Override
    public void setServereMap(Map<String, List<String>> servereMap) {
        this.servereMap = servereMap;
    }
}
