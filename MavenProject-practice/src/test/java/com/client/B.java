package com.client;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by shouh on 2016/9/30.
 */
public class B extends TestCase {

    public B(String name)
    {
        super(name);
    }
    @Test
    public void c() {
        assertEquals(3, 3);
    }

    @Test
    public void d() {
        assertEquals(3, 3);
    }
}
