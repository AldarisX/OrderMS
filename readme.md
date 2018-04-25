## 进销存管理

#### 运行环境

> 最低单核2G,推荐双核4G

> JDK(8,9,10),PostgreSQL9.6

JDK环境:[JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),[JDK10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html),[Zulu JDK(Windows)](https://www.azul.com/downloads/zulu/zulu-windows/),[Zulu JDK(Linux)](https://www.azul.com/downloads/zulu/zulu-linux/)

数据库:[PostgreSQL](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

#### 配置

 * 配置文件在config目录下
    
    | 名称 | 功能 | 修改 |
    |:--------|:---------|:-------|
    application-db.properties | 数据库连接 | 自定义
    application-server.properties | 服务器配置 | server.port
    application-dev.properties | 开发用的参数 | 自定义
    application-pro.properties | 生产用的参数 | 自定义
    application-system.properties | 管理系统的配置 | 不要修改
    
 * 修改连接PostgreSQL参数
    * spring.datasource.url:JDBC连接参数其中127.0.0.1:5432修改为自己的数据库地址与端口
    * spring.datasource.username:数据库用户名
    * spring.datasource.password:数据库密码
    
 * 生产环境建议将服务器配置中的server.port改为80,这样直接输入地址就能访问了
 
 * 日志
    * 日志存放在log目录,具体参数见开发/生产用的配置文件

#### 启动

执行目录下的 run脚本

* WIndows下执行run.cmd

* Linux下执行run.sh或者run-log.sh(需要安装screen)

*  初始账号 admin 密码123456

#### 详细API

> 启动后访问 /swagger-ui.html (需要用admin权限访问)