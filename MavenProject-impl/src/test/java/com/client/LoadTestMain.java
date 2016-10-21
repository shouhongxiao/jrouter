package com.client;

import com.clarkware.junitperf.LoadTest;
import com.clarkware.junitperf.TestMethodFactory;
import junit.framework.TestSuite;

/**
 * Created by shouh on 2016/9/30.
 */
public class LoadTestMain {

    public static void main(String[] args)
    {
        TestSuite suite = new TestSuite();
        //suite.addTest(new TimedTest(new TestMethodFactory(B.class,"c"),6));
        suite.addTest(new LoadTest(new TestMethodFactory(RoundRobinTest.class,"testgetServer"),9));
        junit.textui.TestRunner.run(suite);
    }
}
