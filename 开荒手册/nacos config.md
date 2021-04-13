## 1.nacos config

(1)导入依赖

```xml
<!-- 引入nacos config依赖-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
```

(2)创建bootstrap.properties文件,配置好配置中心的内容,让服务一起就从nacos找对应的配置拉取下来

```properties
#config server addr

#nacos的服务地址
spring.cloud.nacos.server-addr=localhost:8848
#配置nacos配置中心的服务地址(就是nacos的服务地址)
spring.cloud.nacos.config.server-addr=${spring.cloud.nacos.server-addr}
#配置服务名称(寻找配置中心对应配置有用)
spring.application.name=config-server

#dataid 
#= config-server-prod.properties 
#= ${spring.application.name}-${spring.profiles.active}.${file-extension}
#1.spring.application.name为注册服务名称 2.spring.profiles.active 当前的环境 3.file-extension 配置文件的后缀

#配置的组名(nacos把命名空间+组+dataid定位唯一的一条配置)
spring.cloud.nacos.config.group=DEFAULT_GROUP
#dataid的第一个参数
spring.cloud.nacos.config.name=${spring.application.name}
#dataid的第二个参数(如果不写,则 config-server-prod.properties的-不存在 就变成 config-server.properties)
spring.profiles.active=prod
#dataid的第三个参数 只支持 properties&yaml
spring.cloud.nacos.config.file-extension=properties
```

(3)发布nacos配置

![image-20210414004130994](..\B2B-开荒手册\img\image-20210414004130994.png)

(4)启动服务

(5)自动刷新

```java
//在controller中加入注解@RefreshScope就可以实现了,配置一旦发布,controller立刻就会生效
//例子
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
```

