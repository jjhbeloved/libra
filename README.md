# Libra
Libra-配置中心，简化接入成本，自动选择所在环境，配置在线变更，实时生效

## Cli SDK
- 使用 spring **@Value**的方式提供SDK, 支持动态自更新, 只支持 **String.class** 配置
- 使用 先加载服务端配置数据, 后使用本地配置信息进行覆盖, 本地配置优先策略
    - 简化版本可以忽略本地配置像, 不需要 实现xsd,handler,parse
    - xsd,handler,parse是通过配置文件,进行解析动态传入数据
- ```PropertyPlaceholderConfigurer.class``` Properties只是基于location的配置文件, 如果想获取全部的信息, 继承 Enviorment 从Binder以获取

## Server
1. 将本地缓存(guava)开关保存至全局数据库system_setting表中
2. 将环境信息保存在environment表中
3. 将配置信息保存在zookeeper中, 根据本地缓存开关决定数据是否在本地缓存保存一份
4. 每个environment都会有一个注册管理器(每个管理器对应一个zookeeper), 一个环境对应一套zookeeper
5. 对注册器的变更通过修改环境信息来自动触发, 不对外暴露额外服务接口

## issue/tunning
1. 缓存存在穿透问题, 应该采用查询时效内缓存不更新(缓存时效到期更新), 
查询缓存不存在不穿透到持久化存储介质查询, 数据变更时主动更新缓存
    1. springCache 解决了缓存穿透问题
    2. springCache 注解的方式, 不支持类内方法调用, 由于使用 proxy 的方式会丢失注解的功能