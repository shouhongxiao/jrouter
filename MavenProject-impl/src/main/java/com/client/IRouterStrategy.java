package com.client;

import java.util.List;
import java.util.Map;

/**
 * Created by shouh on 2016/10/19.
 */
public interface IRouterStrategy {

    abstract String getServerAddr(String key);
    abstract void setServereMap(Map<String,List<String>> servereMap);
}
