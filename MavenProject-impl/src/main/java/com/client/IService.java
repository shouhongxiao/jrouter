package com.client;

import com.annotation.RegisterAddrAnnotation;

/**
 * Created by shouh on 2016/9/30.
 */
@RegisterAddrAnnotation(name = "base")
public interface IService {
     @RegisterAddrAnnotation(name = "say", value = "/say")
     String sayHello(@com.annotation.PathParam("say") String say);
}
