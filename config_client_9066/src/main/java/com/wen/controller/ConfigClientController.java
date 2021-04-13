package com.wen.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/14 0:46
 */
@RestController
@RefreshScope
@Slf4j
public class ConfigClientController {
    @Value("${user.name}")
    private String userName;
    @GetMapping("/user/name")
    public String getUserName() {
        log.info("获取到了远端的配置了,内容为:[{}]",userName);
        return userName;
    }
}
