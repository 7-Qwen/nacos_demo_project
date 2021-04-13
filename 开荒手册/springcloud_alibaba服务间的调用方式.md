### 1、首先服务都在nacos中被注册

#### 2、一端调用另一端有以下几个方式:

```java

1.restTemplate 调用getobject...
	  例子:
		String product = restTemplate.getForObject("http://localhost:9098/product/find/{id}", String.class, id);
	  
2.	DiscoveryClient 其中可以调用service
      例子:
		List<ServiceInstance> productServer = discoveryClient.getInstances("product-server");
      
3. loadBalancerClient 根据serviceId进行调用 返回的ServiceInstance与上面的discoveryClient的返回参数一致
      例子:
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-server");
        log.info(serviceInstance.getUri().toString());
        
4.  restTemplate + ribbon 负载均衡客户端 (拼接服务地址的方式)
      例子:
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-server");
        log.info("第三种服务地址:"+serviceInstance.getUri().toString());
        //第四种调用方式 restTemplate+负载均衡
        String product = restTemplate.getForObject(serviceInstance.getUri() + "/product/find/{id}", String.class, id);
        log.info("第四种服务:与第三种服务地址配合[{}]",product);
        
5. restTemplate与负载均衡合二为一 在restTemplate上面加入@LoadBalanced
      例子:
     	(在加入了@Configuration的配置类中或者Application的启动类中配置bean加入spring工厂中)

            @Bean
        	@LoadBalanced
        	public RestTemplate restTemplate() {
        	    return new RestTemplate();
       		 }

        String product = restTemplate.getForObject("http://product-server/product/find/{id}", String.class, id);
        log.info("第五种调用方式:[{}]",product);

```

以上方式都有一个缺陷，那就是要输入请求的uri地址，当请求的地址发生了变动，所有用到请求的地方都要改，这样维护成本就非常大，所以springcloud提供了一种解决方案，那就是openFeign。

### 3、openFeign **最接近项目使用的远程调用服务的方式**

- 导入 spring-cloud-starter-openfeign以依赖
- 在客户端建一个clients包，准备调用服务用
- 建立一个**接口**，一定是一个接口供客户端内部使用，Feign再通过代理模式找到注册中心的服务从而调用服务（不多说，FeignClient内的接口要和提供服务者接口的Mapping请求要一致，可以说要**一模一样**） ，然后加上@FeignClient的注解（表明是服务的调用方）

```java
例子:用户调用商品服务
    
//商品服务提供的接口：
 @GetMapping("/product/find/{id}")
    public Map<String, Object> find(@PathVariable("id") String id) {
        Map<String, Object> map = new HashMap<>();
        log.info("进入商品服务,当前接受的商品id为:[{}]", id);
        map.put("status", "true");
        map.put("msg", "当前商品服务调用成功,查询的商品id为:" + id + ",当前处理服务的调用端口为:" + port);
        return map;
    }
    

//客户端要代理的接口:(product-server为商品服务注册进nacos的服务名称)

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

```

