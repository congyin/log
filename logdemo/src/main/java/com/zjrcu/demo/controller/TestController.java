package com.zjrcu.demo.controller;

import com.zjrcu.demo.common.aop.ControllerWebLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * log controller
 */
@Controller
@RequestMapping("/log")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     *  测试 log 日志记录
     *  使用自己定义的注解，将该方法 标注为 查询
     * @return
     */
    @GetMapping("/test")
    @ControllerWebLog(name = "查询",intoDb = true)
    @ResponseBody
    public String test(){
        logger.debug("this is debug info.");
        logger.info("this is info info.");
        logger.error("this is error info.");
        return "Hello world!";
    }
}
