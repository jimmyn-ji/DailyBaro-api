# DailyBaro-api 后端开发环境说明

## AI服务代理配置（解决SSLHandshakeException/握手失败）

### 背景

- Java后端（RestTemplate/HttpURLConnection）默认**只支持HTTP/HTTPS代理**，不支持SOCKS5。
- 你的clash/v2ray等代理软件默认1086端口为SOCKS5，**不能直接用在Java RestTemplate**。
- 你必须在代理软件中**开启HTTP代理端口**（如7890），并让Java进程走HTTP代理。

### 步骤

#### 1. 在clash/v2ray/surge等代理软件中开启HTTP代理端口
- 端口号如7890（可自定义）。
- 确认HTTP代理端口已开启，并能正常访问外网。
- 用curl测试：
  ```sh
  curl -x http://127.0.0.1:7890 https://api.deepseek.com/v1/chat/completions
  ```
  能返回401等响应即说明HTTP代理端口可用。

#### 2. 启动Spring Boot时加如下VM参数

- IDEA配置：Run/Debug Configurations → VM options：
  ```
  -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890
  ```
- 命令行启动：
  ```sh
  java -Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890 -jar target/你的jar包名.jar
  ```
- Maven插件：
  ```sh
  mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dhttps.proxyHost=127.0.0.1 -Dhttps.proxyPort=7890"
  ```

#### 3. 不要用-DsocksProxyHost参数
- RestTemplate默认不会走SOCKS5代理。
- 只用-Dhttps.proxyHost和-Dhttps.proxyPort即可。

#### 4. 重启Spring Boot服务
- 现在AI分析接口应能正常访问外部AI服务。

---

如仍有SSL握手失败，请检查：
- JDK版本建议用Adoptium OpenJDK 17最新版
- JDK cacerts证书链已补全（如有需要）
- 本机时间准确
- 代理软件已开启且端口无误

如有问题请联系开发支持。 