package com.client;

import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TestMethodFactory;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by shouh on 2016/9/30.
 */
public class RoundRobinTest extends TestCase {

    public RoundRobinTest(String name)
    {
        super(name);
    }

    @Test
    public void testgetServer() throws InterruptedException, IOException, KeeperException {

       // System.out.println(addServer);

    }

    @Test
    public void loadTestMain()
    {
        TestSuite suite = new TestSuite();
        //suite.addTest(new TimedTest(new TestMethodFactory(B.class,"c"),6));
        suite.addTest(new LoadTest(new TestMethodFactory(RoundRobinTest.class,"testgetServer"),9));
        junit.textui.TestRunner.run(suite);
    }




}