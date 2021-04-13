package com.wen.controller;

import com.wen.clients.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 7wen
 * @description: TODO
 * @date 2021/4/13 16:06
 */
@RestController
@Slf4j
public class NacosUser9099 {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private ProductClient productClient;

    @GetMapping("/user/find")
    public String getProductService(@RequestParam("id") String id) {

        //第一种调用方式 restTemplate
//        String product = restTemplate.getForObject("http://localhost:9098/product/find/{id}", String.class, id);
//        log.info(product);
        //第二种调用方式 restTemplate + ribbon 负载均衡客户端 DiscoveryClient LoadBalanceClient 注解形式
//        List<ServiceInstance> productServer = discoveryClient.getInstances("product-server");
//        productServer.forEach(p -> {
//            log.info("第二种服务地址:[{}}",p.getUri());
//        });
        //第三种调用方式 loadBalancerClient
//        ServiceInstance serviceInstance = loadBalancerClient.choose("product-server");
//        log.info("第三种服务地址:"+serviceInstance.getUri().toString());
//      //第四种调用方式 restTemplate+负载均衡
//        String product = restTemplate.getForObject(serviceInstance.getUri() + "/product/find/{id}", String.class, id);
//        log.info("第四种服务:与第三种服务地址配合[{}]",product);
        //第五种方式 与负载均衡合二为一 在restTemplate上面加入@LoadBalanced
//        String product = restTemplate.getForObject("http://product-server/product/find/{id}", String.class, id);
//        log.info("第五种调用方式:[{}]",product);
        //第六种调用方式 接口调用 openFeign
        Map<String, Object> product = productClient.find(id);
        log.info("第六种调用方式:OpenFeign[{}]",product);
        return product.toString();
    }
}
