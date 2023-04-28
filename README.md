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

  1.3.5 设计系统登录界面

element-plus ui使用 按需导入

```bash
npm install -D unplugin-vue-components unplugin-auto-import
```

vite.config.ts 配置文件

```ts
import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

//npm install -D unplugin-vue-components unplugin-auto-import
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        //npm install -D unplugin-vue-components unplugin-auto-import
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        //npm install -D unplugin-vue-components unplugin-auto-import
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ],
    // base: 'admin',
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

element-plus main.ts入口文件

```ts
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
app.use(ElementPlus)
```



使用Elmessage组件

 ```ts
import {ElMessage} from "element-plus";

//["success", "info", "warning", "error"];
ElMessage(form.username)
ElMessage(form.password)
//ElMessage({
//    message:form.username,
//    type:"success"
//})
ElMessage.success(form.username);
 ```

Login.vue

```html
<script lang="ts" setup>
import {reactive} from "vue";
import {Lock, User} from "@element-plus/icons-vue";
const form = reactive({
    username: 'admin', password: '123456'
})
const onSubmit = () => {
}
</script>
<template>
    <div class="login">
        <div class="login-box">
            <img src="../assets/imgs/login-l.png" alt="">
            <div class="login-form">
                <el-form :model="form">
                    <el-form-item><img src="../assets/imgs/logo.png" alt=""></el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="User" placeholder="请输入账号" v-model="form.username"/>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="Lock" placeholder="请输入密码" show-password type="password" v-model="form.password"/>
                    </el-form-item>
                         <el-button type="primary" @click="onSubmit">登录</el-button>
                </el-form>
            </div>
        </div>
    </div>
</template>

<style>
.login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #333;
}

.login-box {
    width: 1000px;
    height: 474px;
    border-radius: 8px;
    display: flex;
}

.login-box img {
    width: 80%;
    height: auto;
}

.login-form {
    background: #ffffff;
    width: 40%;
    border-radius: 0 8px 8px 0;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>

```

![image-20230428111956805](E:\Java\ssms-project\assets\image-20230428111956805.png)

##### 1.3.6 登录按钮业务代码

点击登录按钮流程，使用ajax将账号密码发给api接口，进行验证，接口验证成功，返回验证结果，结果如果是成功的，并返回令牌，以后每次操作服务器后端，都需要携带令牌。

###### 前端部分

```html
<script lang="ts" setup>
import {reactive} from "vue";
import {Lock, User} from "@element-plus/icons-vue";
import {useRouter} from 'vue-router'

const router = useRouter()
const form = reactive({
    username: 'admin', password: '123456'
})
import axios from 'axios'
import {ElMessage} from "element-plus";
const onSubmit = () => {
    axios({
        url:'http://localhost:8080/api/admin/login',
        data:form,
        method:'post'
    }).then(res=>{
        let result:any = res.data
        if(result.code === 0){
            ElMessage.error(result.msg)
        }else{
            //ElMessage.success(result.msg)
            //成功后，保存令牌 cookie
            localStorage.setItem('token',result.token)
            //router.push({path:'/home'})
            ElMessage({
                message: `${result.msg}！即将跳转到系统首页！`,//提示的信息
                type:'success',　　//类型是成功
                offset:50,　　//距离窗口顶部的偏移量，建议不设置
                duration:1200,　　//显示时间, 毫秒。设为 0 则不会自动关闭，建议1200
                onClose:()=>{
                    //跳转页面或执行方法
                    router.push({path:'/home'})
                }
            })

        }
    }).catch(e=>{

        ElMessage.error('网络错误，请检查后端接口.')
    })
}
</script>
<template>
    <div class="login">
        <div class="login-box">
            <img src="../assets/imgs/login-l.png" alt="">
            <div class="login-form">
                <el-form :model="form">
                    <el-form-item><img src="../assets/imgs/logo.png" alt=""></el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="User" placeholder="请输入账号" v-model="form.username"/>
                    </el-form-item>
                    <el-form-item>
                        <el-input :prefix-icon="Lock" placeholder="请输入密码" show-password type="password" v-model="form.password"/>
                    </el-form-item>
                         <el-button type="primary" @click="onSubmit">登录</el-button>
                </el-form>
            </div>
        </div>
    </div>
</template>

<style>
.login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #333;
}

.login-box {
    width: 1000px;
    height: 474px;
    border-radius: 8px;
    display: flex;
}

.login-box img {
    width: 80%;
    height: auto;
}

.login-form {
    background: #ffffff;
    width: 40%;
    border-radius: 0 8px 8px 0;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>

```

###### 后端部分

token令牌概念?

JWT (全称：Json Web Token)是一个开放标准(RFC 7519)，它定义了一种紧凑的、自包含的方式，用于作为 JSON 对象在各方之间安全地传输信息。该信息可以被验证和信任，因为它是数字签名的。

上面说法比较文绉绉，简单点说就是一种认证机制，让后台知道该请求是来自于受信的客户端。

首先我们先看一个流程图：

![image-20230428142401889](E:\Java\ssms-project\assets\image-20230428142401889.png)



![image-20230428142606234](E:\Java\ssms-project\assets\image-20230428142606234.png)

 **JWT的优点**

- json格式的通用性，所以JWT可以跨语言支持，比如Java、JavaScript、PHP、Node等等。
- 可以利用Payload存储一些非敏感的信息。
- 便于传输，JWT结构简单，字节占用小。
- 不需要在服务端保存会话信息，易于应用的扩展。

![image-20230428143031049](E:\Java\ssms-project\assets\image-20230428143031049.png)

```xml
<!-- com.auth0/java-jwt -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.4.0</version>
</dependency>
```

```java
/*
 * Copyright (c) 2006, 2023, webrx.cn All rights reserved.
 *
 */
package cn.webrx.out;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 * <p>Powered by webrx On 2023-04-28 14:34:00</p>
 *
 * @author webrx [webrx@126.com]
 * @version 1.0
 * @since 17
 */

@Log4j2
public class JwtDemo {
    @Test
    void m() {
        //生成token
        //String token = JWT.create().sign(Algorithm.HMAC256("webrx"));
        //log.info(token);
        //String tk = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.03BNBX8FwXubNISFD2SJUetYEv5-S86sApLpuJN3cXE";

        //检验token
        //JWTVerifier jv = JWT.require(Algorithm.HMAC256("webrx")).build();
        //System.out.println(jv.verify(tk).getExpiresAt());

        //第一部分 header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String tk = JWT.create().withHeader(header)
                .withClaim("name","管理员")
                .withExpiresAt(Instant.now().plusSeconds(300))
                .sign(Algorithm.HMAC256("webrx"));

        log.debug(tk);

        String tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiZXhwIjoxNjgyNjY0NjIxfQ.4NFP4x8DtsiK5HuzRFXe3SjNw26aRoFJVBbiPgBmpJQ";
               tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiZXhwIjoxNjgyNjY1MTgwfQ.hcL_VBI1CLqQN4M6vfHiCwn33Hy6bP-zsqmAkZnymi0";

        JWTVerifier jv = JWT.require(Algorithm.HMAC256("webrx")).build();
        try{
            DecodedJWT verify = jv.verify(tt);
            System.out.println(verify.getHeader());
            System.out.println(verify.getAlgorithm());
            System.out.printf("%tF %<tT%n",verify.getExpiresAt());
        }catch(TokenExpiredException e){
            System.out.println("token过期了");
        }catch(SignatureVerificationException e){
            System.out.println("签名错误");
        }
    }
}

```









## 第二部分 建立前台系统项目





## 附录一 后端常用依赖

### 1.1 常用依赖

```xml
<!-- org.yaml/snakeyaml -->
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>2.0</version>
</dependency>

<!-- spring-boot-starter-data-redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- spring-boot-starter-test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- fastjson jackson是springboot内置的 -->
<!-- com.fasterxml.jackson.core/jackson-core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.15.0</version>
</dependency>
<!-- com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.0</version>
</dependency>
<!-- com.fasterxml.jackson.datatype/jackson-datatype-jsr310 -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.15.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>3.0.6</version>
</dependency>

<!-- spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>2.7.11</version>
</dependency>

<!-- mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.5.3.1</version>
</dependency>


<!-- com.auth0/java-jwt -->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>4.4.0</version>
</dependency>

```



### 1.2 常用配置文件





## 附录二 前端项目配置







