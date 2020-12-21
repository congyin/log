package com.zjrcu.demo.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * web 访问切面
 */
@Aspect
@Component
@Order(100)
public class WebLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    private ThreadLocal<Map<String, Object>> threadLocal  = new ThreadLocal<>();


    /**
     * 横切点
     */
    @Pointcut("execution(public * com.zjrcu.demo.controller..*.*(..))")
    public void webLog(){}

    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    public void doBefore(JoinPoint joinPoint,ControllerWebLog controllerWebLog) {

        logger.info("Order(100) doBefore 方法执行 ...");
        //获取到请求
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes rsa = (ServletRequestAttributes) ra;
        HttpServletRequest request = rsa.getRequest();

        //记录请求内容,threadInfo存储所有内容
        HashMap<String, Object> threadInfo = new HashMap<>();
        logger.info("URL : "  + request.getRequestURL());
        threadInfo.put("url",request.getRequestURL());
        logger.info("URI : " + request.getRequestURI());
        threadInfo.put("uri", request.getRequestURI());
        logger.info("HTTP_METHOD : " + request.getMethod());
        threadInfo.put("httpMethod", request.getMethod());
        logger.info("REMOTE_ADDR : " + request.getRemoteAddr());
        threadInfo.put("ip : ", request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
              + joinPoint.getSignature().getName());
        threadInfo.put("classMethod",joinPoint.getSignature().getDeclaringTypeName()+"."
                +joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        threadInfo.put("args", Arrays.toString(joinPoint.getArgs()));
        logger.info("USER_AGENT : " + request.getHeader("User-Agent"));
        threadInfo.put("userAgent", request.getHeader("User-Agent"));
        logger.info("执行方法 ：" + controllerWebLog.name());
        threadInfo.put("methodName", controllerWebLog.name());
        threadLocal.set(threadInfo);

    }

    /**
     * 执行成功后处理
     * @param controllerWebLog
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "ret")
    public void doAfterReturning(ControllerWebLog controllerWebLog, Object ret) throws Throwable {
        logger.info("Order(100) doBefore 方法执行 ...");
        Map<String, Object> threadInfo = threadLocal.get();
        threadInfo.put("result", ret);
        if (controllerWebLog.intoDb()) {
            //插入数据库操作
            //insertResult(threadInfo);
        }
        // 处理完请求，返回内容
        logger.info("RESPONSE : " + ret);
    }

    /**
     * 异常处理
     * @param throwable
     */
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        logger.info("Order(100) doBefore 方法执行 ...");
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        HttpServletRequest request = sra.getRequest();
        // 异常信息
        logger.error("{}接口调用异常，异常信息{}", request.getRequestURI(), throwable);
    }

}
