package com.zkwatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import rx.Observable;
import rx.functions.Action1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by shouh on 2016/10/11.
 */
public class ConfigWatcher {

    private ActiveKeyValueStore store;

    public ConfigWatcher() throws IOException, InterruptedException, KeeperException {
        store = new ActiveKeyValueStore();
        store.connect();
    }

    public ConfigWatcher(String hosts) throws IOException, InterruptedException, KeeperException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public List<String> displayConfig(final String path, final Action1<List<String>> act) throws KeeperException, InterruptedException {
        List<String> serverlist = store.getChildren(path,
                event -> {
                    Watcher.Event.EventType changedeventType = Watcher.Event.EventType.NodeChildrenChanged;
                    Watcher.Event.EventType createeventType = Watcher.Event.EventType.NodeCreated;
                    if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {//当前节点数据发生变化
                        System.out.println("子节点发生了变化1," + event.getPath());
                    } else if (event.getType() == changedeventType || event.getType() == createeventType) {
                        try {
                            List<String> list1 = store.zk.getChildren(path, watchedEvent -> {
                                try {
                                    List<String> ll = displayConfig(path, act);
                                    if (act != null)
                                        Observable.just(ll).subscribe(act);
                                } catch (KeeperException e) {
                                    System.out.println("error");
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    System.out.println("error");
                                    e.printStackTrace();
                                }
                            });
                            if (act != null)
                                Observable.just(list1).subscribe(act);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("子节点发生了变化2," + event.getPath());
                    }
                }
        );
        return serverlist;
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        displayConfig(ConfigUpdater.PATH, null);
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CountDownLatch connectedSignal = new CountDownLatch(1);
        ConfigWatcher configWatcher = new ConfigWatcher();
        configWatcher.displayConfig();
        connectedSignal.await();
    }
}
