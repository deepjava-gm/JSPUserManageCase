<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>管理员登录</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <%--弹出框--%>
    <link rel="stylesheet" type="text/css" href="blackbox/css/blackbox.css" media="screen"/>
    <script type="text/javascript" src="blackbox/js/jquery.blackbox.min.js"></script>

    <script type="text/javascript">
        //实例化弹框
        var box = new BlackBox();

        window.onload = function () {

            document.getElementById("form").onsubmit = function () {
                //验证用户名
                //验证密码
                //...
                //都成功则返回true
                //
                return checkUserName()&&checkPassword();

            }

        };

        function checkUserName() {
            var flag = false;
            var username = document.getElementById("username").value;
            var reg_username =/^[\s\S]*.*[^\s][\s\S]*$/;
            flag = reg_username.test(username);
            if (!flag) {
                // 验证失败
                box.alert("用户名不能为空", {
                    title: '格式不正确',
                    value: '重新输入'
                })

            } else {
            }
            return flag;
        }

        function checkPassword() {
            var flag = false;
            var password = document.getElementById("password").value;
            var reg_username = /^[\s\S]*.*[^\s][\s\S]*$/;
            flag = reg_username.test(password);
            if (!flag) {
                // 验证失败
                box.alert("密码不能为空", {
                    title: '格式不正确',
                    value: '重新输入'
                })

            } else {
            }
            return flag;
        }







        //切换验证码
        function refreshCode(){
            //1.获取验证码图片对象
            var vcode = document.getElementById("vcode");

            //2.设置其src属性，加时间戳
            vcode.src = "${pageContext.request.contextPath}/checkCodeServlet?time="+new Date().getTime();
        }


    </script>

</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">管理员登录</h3>
    <form id="form" action="${pageContext.request.contextPath}/loginServlet" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" name="username" onblur="checkUserName()" class="form-control" id="username" placeholder="请输入用户名"/>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" name="password" onblur="checkPassword()" class="form-control" id="password" placeholder="请输入密码"/>
        </div>

        <div class="form-inline">
            <label for="vcode">验证码：</label>
            <input type="text" name="verifycode" class="form-control" id="verifycode" placeholder="请输入验证码" style="width: 120px;"/>
            <a href="javascript:refreshCode();">
                <img src="${pageContext.request.contextPath}/checkCodeServlet" title="看不清点击刷新" id="vcode"/>
            </a>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" value="登录">
        </div>
    </form>

    <!-- 出错显示的信息框 -->
    <c:if test="${not empty login_msg}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" >
                <span style="margin-bottom: 10px">&times;</span>
            </button>
            <strong>${login_msg}</strong>
        </div>
    </c:if>



</div>
</body>
</html>