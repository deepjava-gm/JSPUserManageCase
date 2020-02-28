<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- HTML5文档-->
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加用户</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
<%--导入弹框组件--%>
    <link rel="stylesheet" type="text/css" href="blackbox/css/blackbox.css" media="screen"/>
    <script type="text/javascript" src="blackbox/js/jquery.blackbox.min.js"></script>

    <script>
        //实例化弹框
        var box = new BlackBox();

        window.onload = function () {

            var back = document.getElementById("back");
            back.onclick = function () {
                history.go(-1);
            }

            document.getElementById("form").onsubmit = function () {
                //验证用户名
                //验证密码
                //...
                //都成功则返回true
                //
                return checkName() && checkAge() && checkEmail();

            }

        };


        function checkName() {
            var flag = false;
            var name = document.getElementById("name").value;
            var reg_username = /^\S{1,6}$/;
            flag = reg_username.test(name);
            if (!flag) {
                // 验证失败
                box.alert("用户名格式不正确,正确格式为1-6位字符", {
                    title: '格式不正确',
                    value: '重新输入'
                })

            } else {
            }
            return flag;
        }

        function checkAge() {
            var age = document.getElementById("age").value;
            var reg_age = /^[0-9]{1,3}$/;
            var flag = reg_age.test(age);
            if (!flag||age<=0) {
                // 验证失败
                box.alert("用户年龄格式不正确,正确格式为大于0的1-3位数字", {
                    title: '格式不正确',
                    value: '重新输入'
                })

            } else {
            }
            return flag;
        }

        function checkEmail() {
            var email = document.getElementById("email").value;
            var reg_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            var flag = reg_email.test(email);
            if (!flag) {
                // 验证失败
                box.alert("邮箱地址格式不正确！", {
                    title: '格式不正确',
                    value: '重新输入'
                })

            } else {
            }
            return flag;
        }


    </script>
</head>
<body>
<div class="container">
    <center><h3>添加联系人页面</h3></center>
    <form id="form" action="${pageContext.request.contextPath}/addUserServlet" method="post">
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" onblur="checkName()" id="name" name="name" placeholder="请输入姓名">
        </div>

        <div class="form-group">
            <label>性别：</label>
            <input type="radio" name="gender" value="男" checked="checked"/>男
            <input type="radio" name="gender" value="女"/>女
        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" onblur="checkAge()" id="age" name="age" placeholder="请输入年龄">
        </div>

        <div class="form-group">
            <label for="address">地址：</label>
            <select name="address" class="form-control" id="address">
                <option value="陕西">陕西</option>
                <option value="北京">北京</option>
                <option value="上海">上海</option>
            </select>
        </div>


        <div class="form-group">
            <label for="email">Email：</label>
            <input type="text" class="form-control" onblur="checkEmail()" id="email" name="email"
                   placeholder="请输入邮箱地址"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交"/>
            <input class="btn btn-default" type="reset" value="重置"/>
            <input id="back" class="btn btn-default" type="button" value="返回"/>
        </div>
    </form>
</div>
</body>
</html>