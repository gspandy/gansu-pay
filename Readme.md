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
##3.telnet命令调试dubbo
```markdown
//ls
列出dubbo服务
//ls xxx服务
列出当前服务下的方法
//invoke xxx服务.xxx方法("参数")
方法调用
```
