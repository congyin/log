package com.zjrcu.demo.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 测试 @Order 注解的执行顺序
 */
@Aspect
@Component
@Order(10)
public class TestOrderAnnotationAspect {

    private final static Logger logger = LoggerFactory.getLogger(TestOrderAnnotationAspect.class);

    /**
     * 横切点
     */
    @Pointcut("execution(public * com.zjrcu.demo.controller.*.*(..))")
    public void orderLog(){}

    /**
     * 横切点方法执行之前执行
     * @param joinPoint
     */
    @Before(value = "orderLog()")
    public void doBefore(JoinPoint joinPoint){
        logger.info("Order(10) doBefore 方法执行 ...");
    }

    /**
     * 横切点方法执行之后执行
     * @param joinPoint
     */
    @AfterReturning(value = "orderLog()")
    public void doAfterReturning(JoinPoint joinPoint){
        logger.info("Order(10)  doAfterReturning 方法执行 ...");
    }

    /**
     * 横切点方法回滚时执行
     * @param joinPoint
     */
    @AfterThrowing(value = "orderLog()")
    public void doAfterThrowing(JoinPoint joinPoint){
        logger.info("Order(10) doAfterThrowwing 方法执行 ...");
    }
}
