```txt
____|  |               |         \           _)
|      |   _` |   __|  __ \     _ \    __ \   |
__|    |  (   | \__ \  | | |   ___ \   |   |  |
_|     _| \__,_| ____/ _| |_| _/    _\  .__/  _|
_|
```

- 一款基于springboot搭建的，用于个人快速验证想法的“简易”项目结构,真正满足开箱即用。

## 需求
- 每次想快速写个demo验证一些想法或者学习一些新技术的时候都得手动创建一个springboot新项目，太麻烦。
- 索性还是创建一个基本的空项目，等待之后做实验时直接clone即可。

## 功能
### 20241223
- 标准化正确、异常相应返回的json格式
- 使用统一异常处理系统异常和自定义异常，使返回json格式统一
- 制定了统一业务异常返回枚举字典
- 增加了基本的身份认证（TokenFilter），能做到固定token校验，以及白名单功能
- 引入了swagger、logback以及一些第三方常用包


## 开发
- git clone xx
- mvn install 安装依赖及打包
- mvn package 打包

## 访问
```shell
curl -H Auth:oa http://localhost:8080/api/demo/test?name=santa
```


***
## 单实例部署
- nohup java -jar -Xms2048M -Xmx2048M flashApi-1.0-SNAPSHOT.jar > /dev/null 2>&1 &

***
## 高可用部署
### nginx
- 配置项：
```
#接口负载
upstream api-cluster-server{
    server 127.0.0.1:9225;
    server 127.0.0.1:9226;
}
server {
        listen          80;
        server_name     localhost;
        charset         utf-8;
        access_log      /usr/local/nginx-1.20.2/logs/access.log main;
        client_max_body_size 150m;
    	proxy_read_timeout 300;
    	proxy_send_timeout 300;
        location / {
            root   html;
            index  index.html index.htm;
        }
        location /convert-api/ {
            proxy_set_header Host $http_host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header REMOTE-HOST $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_pass http://api-cluster-server;
        }
}

```
- 常用命令：
```
启动： /usr/local/nginx-1.20.2/sbin/nginx
重新加载配置文件： /usr/local/nginx-1.20.2/sbin/nginx -s reload
停止：/usr/local/nginx-1.20.2/sbin/nginx -s quit
```
### java
- 常用命令
```text
# 启动
cd /app/flashApi
./startup.sh
```
- startup.sh内容：
```
#!/bin/bash
nohup java -jar -Xms2048M -Xmx2048M -Dserver.port=9225 -Dspring.application.name=aspose-convert-9225 flashapi-1.0-SNAPSHOT.jar > /dev/null 2>&1 &
nohup java -jar -Xms2048M -Xmx2048M -Dserver.port=9226 -Dspring.application.name=aspose-convert-9226 flashapi-1.0-SNAPSHOT.jar > /dev/null 2>&1 &
```
> 多服务启动时，指定不同端口及项目名称：--server.port=9226 --spring.application.name=aspose-convert-9226，项目名称会被用来当成日志命名文件夹


## 其他问题
### 复制windos字体到linux
- scp C:\Windows\Fonts\*  /usr/share/fonts/chinese/
- yum install mkfontscale
- cd /usr/share/fonts/chinese
- mkfontscale
- fc-list :lang=zh 查询所有中文字体
> yum -y install fontconfig
