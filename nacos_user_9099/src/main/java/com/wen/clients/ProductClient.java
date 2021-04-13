package com.wen.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/13 20:52
 */
//调用商品服务feign的借口
@FeignClient("product-server")
public interface ProductClient {
    @GetMapping("/product/find/{id}")
    public Map<String, Object> find(@PathVariable("id") String id);
}
