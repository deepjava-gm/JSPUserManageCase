## jsp小案例 用户信息的增删改查和列表展示
重点：其中的验证码功能    分页查询功能  和多条件查询功能为以后在企业开发中常用的必备技能 


## 一、案例需求分析
完成用户的增删改查操作 和管理员的登录操作

## 实际完成效果   用户名和密码都是 2
#### 登录：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200229002732190.gif)
#### 进入首页：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200229003041391.gif)
#### 添加用户  修改用户   多条件查询用户就不演示了  gif录屏 动图内存太大了 自己部署试一下吧  
#### 其中添加和修改加了表单验证
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200229004357522.gif)





## 二、技术选型
用刚学过的 Servlet+JSP+MySQL+Druid数据库连接池+JDBCTemplate+BeanUtils工具类+tomcat8

## 三、数据库设计
用户表 :  t_user    
字段:  编号  姓名  年龄  地址  邮箱  用户名 密码   
（这里偷懒就不写管理员表了  直接在用户表后面加两个字段 插入一个用户名和一个密码用来测试登录即可  ）  


-- 建库
CREATE DATABASE db_JSPUserManager;

-- 用库
USE db_JSPUserManager;

--建用户表
CREATE TABLE t_user(
id INT PRIMARY KEY  AUTO_INCREMENT,
NAME VARCHAR(20),
age INT,
address VARCHAR(30),
email VARCHAR(20)
);
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200229002453638.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3ODgzODY2,size_16,color_FFFFFF,t_70)

--建管理员表
CREATE TABLE t_manager(
id INT PRIMARY KEY  AUTO_INCREMENT,
username VARCHAR(20),
password VARCHAR(20)
);
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200229000223143.jpg)



## 四、搭建环境
- 1、创建数据库环境
- 2、创建项目，导入需要的jar包
- 3、编码
- 4、测试
- 5、部署，运维
上面是企业开发的一整套流程当然肯定步骤更详细  
我们只写到测试功能正常通过即可

代码结构：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020022819551023.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3ODgzODY2,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200225220605474.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3ODgzODY2,size_16,color_FFFFFF,t_70)

## 五、重点部分
#### 5.1、分页查询
用到一个分页对象PageBean 来处理 SQL语句 limit  ?,?    两个问号的取值问题
limit  开始索引,每页显示个数
开始索引 = (当前页 - 1 )* 每页显示个数
例如查询第一页  显示5条数据     
limit  0,5

#### 5.2、多条件查询
多条件查询 用到Map集合 来封装前台输入的条件值  key   values[] 
后台 将Map集合中的值通过循环遍历 拼接  存入List集合  最后转换成数组toArray()   传入JdbcTemplate 的 query方法中  进行模糊查询   
其中JdbcTemplate 的 query方法中 形参的可变参数的底层是数组实现 所有可以直接传入数组

**这里有个模板   select  *  from  xxx  where 1=1   xxxxx**
这样就解决了拼接SQL语句where的问题

#### 5.3分页与多条件查询 查询字段过滤问题
当获取Map集合中条件查询值的时候 要跳过分页相关的数据 再进行拼接
最后 再加上分页的数据


具体代码有点多  GitHub下载到本地看吧





