package com.zjrcu.demo.common.aop;

import java.lang.annotation.*;

@Documented //表明这个注解是由 javadoc记录的
@Retention(RetentionPolicy.RUNTIME) //在运行时保留VM，因此可以反读。
@Target(ElementType.METHOD) //作用在方法上
public @interface ControllerWebLog {
    String name();  //所调用接口的名称
    boolean intoDb() default false;  //标识该条操作日志是否需要持久化存储,默认为false
}
