# Getting Started

- 这是一个分布式多模块电商应用
  - 主模块负责处理http请求, 静态资源的处理, 商品, 店铺, 购物车等接口
  - 订单模块负责订单处理
  - 每个模块使用的是单独的数据库, 各个模块使用Dubbo进行通信
  
- 其他环境依赖
  - Redis: 进行分布式登录状态维持
  - MySQL: 存储所有的数据
  - ZooKeeper: 作为Dubbo的注册中心
  - NGINX: 可选，如果希望实现多实例部署和负载均衡
  
- 部署步骤(以下基于Docker环境, java8+)
  - `docker run -d --name redis -p 6379:6379 redis`
  - `docker run -d --name zookeeper -p 2181:2181 zookeeper`
  - `docker volume create mysql-wshop` #只有下一步进行失败时此条才是必需的
  - `docker run --name mysql -v mysql-wshop:/var/lib/mysql -p 3306:3306 -e TZ=Asia/Shanghai -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=wshop -d mysql:latest`#最新版可能不是很稳定, 可选5.7
  - ```docker exec -it mysql mysql -uroot -proot -e 'create database if not exists `order` ```   #需要上一步的数据库完全运行起来时
  - `./mvnw install -DskipTests` 
  - `./mvnw flyway:migrate -pl wshop-main`
  - `./mvnw flyway:migrate -pl wshop-order`
  - **注意，如果你使用的是Windows，将所有的./mvnw换成./mvnw.cmd**
  
- 启动应用
  - 在第一个窗口中运行 `java -jar wxshop-order/target/wshop-order-0.0.1-SNAPSHOT.jar`
  - 在第二个窗口中运行 `java -jar wxshop-main/target/wshop-main-0.0.1-SNAPSHOT.jar`  

- open http://localhost:8080  