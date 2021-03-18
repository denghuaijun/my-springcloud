# 相关网站资料连接
https://www.springcloud.cc/spring-cloud-netflix.html

中文API文档  https://www.springcloud.cc/spring-cloud-dalston.html

SpringCloud中国社区  http://springcloud.cn/

SpringCloud中文网  https://www.springcloud.cc/

https://www.cnblogs.com/haoxianrui/p/13740264.html

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
   
## springcloud security oauth2集成测试
### 授权码模式
这种模式是四种模式中安全性较高的

申请授权码地址：http://localhost:10011/oauth/authorize/?client_id=appid&response_type=code&scope=all&redirect_uri=http://localhost:10011   

申请令牌post请求：http://localhost:10011/oauth/token?client_id=appid&client_secret=app_secret&code=授权码&grant_type=authorization_code&redirect_uri=http://localhost:10011

### 简要模式
申请令牌地址：http://localhost:10011/oauth/authorize/?client_id=appid&response_type=token&scope=all&redirect_uri=http://localhost:10011  
认证之后同意授权之后，会将令牌回显到回传地址后面。

应用场景：主要应用于没有服务端的第三方应用，所以它通过页面回传地址的形式给出

### 密码模式
用户直接使用用户名和密码去访问认证服务获取令牌：
http://localhost:10011/oauth/token?client_id=appid&client_secret=app_secret&grant_type=password&username=用户名&password=密码
### 客户端模式
http://localhost:10011/oauth/token/?client_id=appid&client_secret=app_secret&grant_type=client_credentials

### jwt令牌生成方式
认证服务中心uaa处理方式：

1. 在认证服务中心，令牌配置服务中使用jwt方式存储令牌
2. 在认证服务配置类里面修改认证服务令牌配置服务添加令牌增强器，并使用jwt配置增强

资源服务resource处理方式：

1. 将uaa中的TokenConfig令牌配置类复制到资源服务模块，同时将资源服务配置类的使用远程调用认证服务校验token配置取消
2. 在资源服务中注入tokenStore，同时在资源服务配置拦截中直接设置对应的token存储方式，就可以自行校验。