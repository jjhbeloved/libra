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

## issue/tunning
1. 缓存存在穿透问题, 应该采用查询时效内缓存不更新(缓存时效到期更新), 
查询缓存不存在不穿透到持久化存储介质查询, 数据变更时主动更新缓存
    1. springCache 解决了缓存穿透问题
    2. springCache 注解的方式, 不支持类内方法调用, 由于使用 proxy 的方式会丢失注解的功能
2. curator如何解决重复注册watcher的问题, 如何解决注销watcher重新注册期间丢失的问题
   ```
   Curator只是封装了原生Zookeeper的监听事件，使客户端程序员无序重复注册Watcher，
   但是Wathcer的一次性还是存在的，只是由curator完成。
   因此对于某些场景使用依然需要慎重。
   因为curator需要重复注册，因此，第一次触发Wathcer与再次注册Watcher即使是异常操作，
   但是中间还是存在时延，假使对于Zookeeper瞬时触发几个事件，
   则该监听器并不能保证监听到所有状态的改变，至于可以监听到多少取决于服务器的处理速度
   ```
   配置中心使用 listener.addListener添加针对 getData和existsData的监听(changed/created/deleted)事件, 
   获取数据并且缓存在本地
3. ClassLoader子类都对数据进行缓存, ConfigCache又做了一级缓存浪费一倍内存

## fixed
1. 使用 springCache解决缓存穿透问题, 使用 springCache composite 合并多种缓存
2. 使用curator监听watched
3. ClassLoader作为基础服务, 不进行数据缓存, 由ClassLoader的合集ConfigCache进行缓存, 减少内存

## 产品需求
1. 配置管理
2. 用户管理
3. 用户权限管理
4. 团队管理
5. 产品管理
6. 项目管理
7. 角色管理