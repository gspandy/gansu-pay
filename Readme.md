##1.一个简单的支付项目微服务架构
    gansu-pay-web:web项目
    gansu-pay-api:提供dubboRPC服务
    gansu-pay-server:服务实现
    gansu-pay-dao:dao层
    gansu-pay-util:提供工具类
    gansu-pay-model:提供实体
    
##2.项目之间的依赖关系
```markdown
gansu-pay-web--依赖-->gansu-pay-server
gansu-pay-server--依赖-->gansu-pay-api    gansu-pay-dao   gansu-pay-util
gan-pay-api--依赖-->gansu-pay-model
gan-pay-dao--依赖-->gansu-pay-model 
```
##3.项目集成功能
#### &nbsp;&nbsp;&nbsp;&nbsp;3.1 mysql数据库
#### &nbsp;&nbsp;&nbsp;&nbsp;3.2 redis缓存分布式锁
#### &nbsp;&nbsp;&nbsp;&nbsp;3.3 dubbo RPC远程服务框架
#### &nbsp;&nbsp;&nbsp;&nbsp;3.4 schedual定时任务

##4.docker创建mysql、redis容器
```markdown
alias redis-gansu='docker run -d --name gansu-redis -p 6379:6379 daocloud.io/library/redis:3.2.10'
alias mysql-gansu='docker run -d --name=gansu-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=gansu-pay daocloud.io/library/mysql:5.6.17'

```

##5.telnet命令调试dubbo
```markdown
//ls
列出dubbo服务
//ls xxx服务
列出当前服务下的方法
//invoke xxx服务.xxx方法("参数")
方法调用
```
##6 netty4框架接入
```
<dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty-all</artifactId>
    <version>4.1.19.Final</version>
</dependency>
```