# 相关网站资料连接
https://www.springcloud.cc/spring-cloud-netflix.html

中文API文档  https://www.springcloud.cc/spring-cloud-dalston.html

SpringCloud中国社区  http://springcloud.cn/

SpringCloud中文网  https://www.springcloud.cc/

## Hystrix

官网资料：  https://github.com/Netflix/Hystrix/wiki

可以做到那些：
1. 服务降级  发生在客户端，可以在feign接口类做处理，使用一个没有进行熔断处理的8081服务器,
    
    主要用于服务端由于某些原因如负载过大，所以需要关闭服务器时，使用，这样为客户端做出一定的缺省值

2. 熔断   发生在服务端接口  示例对应8080的生产端

   主要用于在调用服务的链路里面，由于服务异常或者网络原因引起的的服务中断，导致了服务长时间占用资源做的熔断处理，就像保险丝一样，同时给调用方返回一定的缺省值。
3. 限流

4. 类似于服务监控 等 集成在consumer模块里面。。新建的hashdabord没有用

   1. 开启dashboard监控页面，则需要在对应要开启的服务端进行配置hystrixMetricsStreamServlet servletbean；
   2. spring-boot-starter-actuator 再要进行监控的服务端要添加对应的springboot监控信息依赖