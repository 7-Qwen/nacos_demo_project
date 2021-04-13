package com.wen.nacos_products_9098.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/13 15:57
 */
@RestController
@Slf4j
public class NacosProduct9098Controller {
    @Value("${server.port}")
    private int port;

    @GetMapping("/product/find/{id}")
    public Map<String, Object> find(@PathVariable("id") String id) {
        Map<String, Object> map = new HashMap<>();
        log.info("进入商品服务,当前接受的商品id为:[{}]", id);
        map.put("status", "true");
        map.put("msg", "当前商品服务调用成功,查询的商品id为:" + id + ",当前处理服务的调用端口为:" + port);
        return map;
    }
}
