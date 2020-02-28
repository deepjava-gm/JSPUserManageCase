<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改用户</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-2.1.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
<%--导入弹框组件--%>
    <link rel="stylesheet" type="text/css" href="blackbox/css/blackbox.css" media="screen"/>
    <script type="text/javascript" src="blackbox/js/jquery.blackbox.min.js"></script>

    <script>
        //实例化弹框
        var box = new BlackBox();

        window.onload = function () {


            document.getElementById("form").onsubmit = function () {
                //验证用户名
                //验证密码
                //...
                //都成功则返回true
                //
                return  checkAge() && checkEmail();
            }
        };



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

        function back() {
            history.go(-1);
        }


    </script>
</head>
<body>
<div class="container" style="width: 400px;">
    <h3 style="text-align: center;">修改联系人</h3>
    <form id="form" action="${pageContext.request.contextPath}/updateUserServlet" method="post">
        <!--  隐藏域 提交id-->
        <input type="hidden" name="id" value="${user.id}">

        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control"  id="name" name="name" value="${user.name}" readonly="readonly"
                   placeholder="请输入姓名"/>
        </div>

        <div class="form-group">
            <label>性别：</label>
            <c:if test="${user.gender == '男'}">
                <input type="radio" name="gender" value="男" checked/>男
                <input type="radio" name="gender" value="女"/>女
            </c:if>

            <c:if test="${user.gender == '女'}">
                <input type="radio" name="gender" value="男"/>男
                <input type="radio" name="gender" value="女" checked/>女
            </c:if>


        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" onblur="checkAge()" value="${user.age}" id="age" name="age" placeholder="请输入年龄"/>
        </div>

        <div class="form-group">
            <label for="address">地址：</label>
            <select name="address" id="address" class="form-control">
                <c:if test="${user.address == '陕西'}">
                    <option value="陕西" selected>陕西</option>
                    <option value="北京">北京</option>
                    <option value="上海">上海</option>
                </c:if>

                <c:if test="${user.address == '北京'}">
                    <option value="陕西">陕西</option>
                    <option value="北京" selected>北京</option>
                    <option value="上海">上海</option>
                </c:if>

                <c:if test="${user.address == '上海'}">
                    <option value="陕西">陕西</option>
                    <option value="北京">北京</option>
                    <option value="上海" selected>上海</option>
                </c:if>
            </select>
        </div>


        <div class="form-group">
            <label for="email">Email：</label>
            <input type="text" id="email" class="form-control" onblur="checkEmail()" value="${user.email}" name="email"
                   placeholder="请输入邮箱地址"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交"/>
            <input class="btn btn-default" type="reset" value="重置"/>
            <input onclick="back()" class="btn btn-default" type="button" value="返回"/>
        </div>
    </form>
</div>

</body>
</html>