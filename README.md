# 外卖点餐项目开发

## 技术

* 前后端分端
* mysql
* redis
* springboot
* mybatis plus
* vue 
* element plus

## 第一部分 建立后台系统项目

* springboot
* mybatis-plus
* redis
* mysql

### 1.1 安装redis

  #### 更新ubuntu系统

```bash
zxy@us:~$ sudo apt update
zxy@us:~$ sudo apt list --upgradable
zxy@us:~$ sudo apt upgrade -y
```

#### 直接安装

redis.io 有安装说明

```bash
curl -fsSL https://packages.redis.io/gpg | sudo gpg --dearmor -o /usr/share/keyrings/redis-archive-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/redis-archive-keyring.gpg] https://packages.redis.io/deb $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/redis.list

sudo apt update
sudo apt install redis
```

```bash
#查看系统的服务状态
sudo systemctl status redis-server 

#安装完成后，查看版本信息
zxy@us:~$ redis-cli --version
redis-cli 7.0.11
zxy@us:~$ redis-server --version
Redis server v=7.0.11 sha=00000000:0 malloc=jemalloc-5.2.1 bits=64 build=3af367a78d5e21e9

```

![image-20230427161127449](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427161127449.png)

  #### 配置redis服务器外网访问

```bash
zxy@us:~$ sudo find / -name redis.conf
/etc/redis/redis.conf

sudo vim /etc/redis/redis.conf #打开配置文件，输入i 进入编辑，按esc输入:wq保存退出

#bind 127.0.0.1 -::1  0.0.0.0 
bind 0.0.0.0

#此行取消注解，设置密码为123456
requirepass 123456


sudo systemctl restart redis-server
```

![image-20230427162446678](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427162446678.png)

#### idea开发工具测试连接redis

![image-20230427162725038](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427162725038.png)

  ### 1.2 建立springbootweb项目

https://start.spring.io/

![image-20230428020650217](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230428020650217.png)

![image-20230428021222515](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230428021222515.png)











##### 1.2.1 建立基本项目

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.11</version>
        <relativePath/>
    </parent>
    <groupId>cn.zxy</groupId>
    <artifactId>api</artifactId>
    <version>1.0</version>

    <dependencies>
        <!-- org.yaml/snakeyaml -->
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.0</version>
        </dependency>

        <!-- redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <!-- web tomcat  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

```

##### 1.2.2 配置项目热部署

![image-20230427164052511](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427164052511.png)

  ##### 1.2.3 日志配置

springboot项目默认使用了logback 和 log4j2 ，可以直接使用lombok组件，快速使用日志

```java
@SpringBootApplication //@Log4j2
public class App {
    public final static Logger log = LogManager.getLogger(App.class);
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
        log.info("hello world");
    }
}
```

`application.yml`

```yaml
server:
  port: 8080


logging:
  level:
    root: info
    cn.webrx: debug
  pattern:
    dateformat: HH:mm:ss.SSS

```

##### 1.2.4 添加mybatis-plus

（1）`pom.xml`文件添加修改

```xml
<!-- com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>
<!-- mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
<!-- druid-spring-boot-starter -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.17</version>
</dependency>

```

`application.yml 文件配置`

```yaml
spring:
  datasource:
    url: jdbc:mysql:/outdb
    type: com.alibaba.druid.pool.DruidDataSource
    username: root
    password: root
mybatis-plus:
  global-config:
    banner: on
  mapper-locations: classpath*:mapper/**/*.xml

```

DbMapper.java

```java
/*
 * Copyright (c) 2006, 2023, webrx.cn All rights reserved.
 *
 */
package cn.zxy.out.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <p>Powered by zxy On 2023-04-27 16:54:49</p>
 *
 * @author zxy 
 * @version 1.0
 * @since 17
 */
@Mapper
public interface DbMapper {
    //@Select("show tables")
    List<String> tbs();

    //@Select("show tables from #{db}")
    List<String> tbs(String db);

    //@Select("show databases")
    List<String> dbs();

    @Select("select version()")
    String version();

    @Select("select user,host,authentication_string,plugin from mysql.user")
    List<Map<String, Object>> userInfo();

    @Select("select database(),user(),version(),@@datadir,@@basedir")
    Map<String, Object> dbInfo();

    @Select("show create table ${tb}")
    Map<String, Object> showCreateTable(String tb);
}

```

DbMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.webrx.out.mapper.DbMapper">
    <select id="dbs" resultType="string">
        show
        databases
    </select>

    <select id="tbs" resultType="string">
        show tables
        <if test="dbname!=null">from ${dbname}</if>
    </select>
</mapper>

```

控制器

```java
@RestController
public class IndexController {
    @Resource
    DbMapper dm;

    @GetMapping
    public String index(@RequestParam(name = "db", required = false, defaultValue = "outdb") String dbname) {
        return SpringBootVersion.getVersion() + String.format("[%s]", dm.tbs(dbname));
    }

    @GetMapping("/ver")
    public String ver(){
        return dm.version();
    }

}
```

`http://localhost:8080/ver`

  ![image-20230427170849903](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427170849903.png)

##### 1.2.5 测试redis

```xml
<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

```yaml
server:
  port: 8080

logging:
  level:
    root: info
    cn.webrx: debug
  pattern:
    dateformat: HH:mm:ss.SSS

spring:
  datasource:
    url: jdbc:mysql:/outdb
    type: com.alibaba.druid.pool.DruidDataSource
    username: root
    password: root

  redis:
    host: 192.168.14.4
    password: 123456
    database: 0
    port: 6379

mybatis-plus:
  global-config:
    banner: on
  mapper-locations: classpath*:mapper/**/*.xml

```

操作redis服务器，设置值，读取值

```java
@Resource
RedisTemplate redisTemplate;
@GetMapping("/ver")
public String ver() {
    //ValueOperations ops = redisTemplate.opsForValue();    // 首先redisTemplate.opsForValue的目的就是表明是以key，value形式储存到Redis数据库中数据的
    //ops.set("age",88);
    //ops.set("name", "李四");
    //System.out.println(ops.get("name"));

    //将生成的验证码缓存到Redis中，并且设置有效期为5分钟
    redisTemplate.opsForValue().set("13014577033","2323",5, TimeUnit.MINUTES);

    ValueOperations ops = redisTemplate.opsForValue();  // 表明取的是key，value型的数据
    Object o = ops.get("nn");  // 获取Redis数据库中key为address1对应的value数据
    return ops.get("name").toString() + ops.get("age");
}
```

![image-20230427172515044](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427172515044.png)

### 1.3 建立vue后台前端项目

##### 1.3.1 配置好node npm

`先配置nodejs path环境变量，进入命令控制台查看安装配置环境`

```cmd
node -v
npm -v
npm config set registry https://registry.npmmirror.com
npm ls -g 

npm config set registry https://registry.npmmirror.com

npm config get registry
https://registry.npmmirror.com/

npm i npm vue vite @vue/cli -g

npm ls -g 
```

##### 1.3.2 vite 建立vue3项目

`npm init vue admin`

![image-20230427174040717](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427174040717.png)

##### 1.3.3 配置 vite.config.ts文件

```ts
server: {
    host: '0.0.0.0',
    open: true,
    port: 8081
},
```

实现 `npm run dev 自动打开浏览器查看项目`

```ts
import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        host: '0.0.0.0',
        open: true,
        port: 8081
    },
})

```

##### 1.3.4 添加项目依赖

axios

element-plus

npm i element-plus axios vue-router@4 pina @element-plus/icons-vue  --save 

```cmd
npm i element-plus axios @element-plus/icons-vue  --save 
```

```cmd
npm ls 
```

![image-20230427174647181](D:\新建文件夹\java\ssms-mybatis\out\README\image-20230427174647181.png)

  

