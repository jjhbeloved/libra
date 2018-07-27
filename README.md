# Libra
Libra-配置中心 

应用程序根据所在环境自动选择配置，无需重新打包。配置在线
变更，实时生效，无需重新发布。中间件配置封装，对开发透明化，
降低开发成本，提升工作效率，规避重新发布风险。

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

## 产品需求
1. 配置管理
2. 用户管理
3. 用户权限管理
4. 团队管理
5. 产品管理
6. 项目管理
7. 角色管理