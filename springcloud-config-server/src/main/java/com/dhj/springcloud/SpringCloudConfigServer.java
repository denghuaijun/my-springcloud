package com.dhj.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
//访问远程git仓库分支中配置文件地址示例：http://localhost:1001/master/config-client-dev.yml
@SpringBootApplication
@EnableConfigServer //开启springcloud配置服务
public class SpringCloudConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServer.class,args);
    }
}
