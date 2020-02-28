<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="blackbox/css/blackbox.css" media="screen"/>
    <script type="text/javascript" src="blackbox/js/jquery.blackbox.min.js"></script>

    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>

    <script>
        //实例化弹框
        var box = new BlackBox();
        /*删除单个*/
        function deleteUser(id) {
            box.confirm("确定删除?", function (data) {
                if (data) {
                    location.href = "${pageContext.request.contextPath}/delUserServlet?id=" + id;
                }
            }, {
                title: '删除用户',
                value: '确定'
            })
        }

        /*删除选中*/
        function delSelected() {
            var flag =false;
            var cbs = document.getElementsByName("cb");
            for(var i=0;i<cbs.length;i++){
                if(cbs[i].checked){
                    flag=true;
                }
            }

            //判断用户是否 至少勾选了一个
            if (flag===false) {
                // 一个都没选中
                box.alert("请选择您要删除的数据~~", {
                    title: '删除用户',
                    value: '确定'
                })
                return;
            }

            box.confirm("确定删除?", function (data) {
                if (data) {

                    document.getElementById("form").submit();
                }
            }, {
                title: '删除用户',
                value: '确定'
            })

            }






        window.onload = function () {
            var firstCb = document.getElementById("firstCb");
            firstCb.onclick = function () {
                var cbs = document.getElementsByName("cb");
                for (var i = 0; i < cbs.length; i++) {
                    cbs[i].checked = this.checked;
                }
            }
        };



    </script>
</head>
<body>

<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>

    <div style="float: left;">

        <form class="form-inline" action="${pageContext.request.contextPath}/findUserByPageServlet" method="post">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" name="name" value="${condition.name[0]}" class="form-control" id="exampleInputName2">
            </div>
            <div class="form-group">
                <label for="exampleInputName3">地址</label>
                <input type="text" name="address" value="${condition.address[0]}" class="form-control"
                       id="exampleInputName3">
            </div>

            <div class="form-group">
                <label for="exampleInputEmail2">邮箱</label>
                <input type="text" name="email" value="${condition.email[0]}" class="form-control"
                       id="exampleInputEmail2">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>

    </div>

    <div style="float: right;margin: 5px">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" onclick="delSelected()">删除选中</a>
    </div>

    <form id="form" action="${pageContext.request.contextPath}/delSelectedServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" name="firstCb" id="firstCb"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>地址</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>

            <c:forEach items="${pb.list}" var="user" varStatus="s">

                <tr>
                    <td><input type="checkbox" name="cb" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.email}</td>

                    <td>
                            <%--${pageContext.request.contextPath}/findUserServlet?id=${user.id}--%>
                        <a class="btn btn-default btn-sm"
                           href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>
                        &nbsp;
                        <a id="delete" class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a>
                    </td>

                </tr>

            </c:forEach>

        </table>
    </form>

    <%--分页--%>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">

                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=1&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}"
                       aria-label="Previous">
                        <span aria-hidden="true">首页</span>
                    </a>
                </li>


                <c:if test="${pb.currentPage == 1}">
                    <li class='disabled'><a href='#'>&laquo;</a></li>
                </c:if>

                <c:if test="${pb.currentPage != 1}">
                    <li>
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage - 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>


                <c:forEach begin="1" end="${pb.totalPage}" var="i">


                    <c:if test="${pb.currentPage == i}">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a>
                        </li>
                    </c:if>


                    <c:if test="${pb.currentPage != i}">
                        <li>
                            <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}">${i}</a>
                        </li>
                    </c:if>


                </c:forEach>


                <c:if test="${pb.currentPage == pb.totalPage}">
                    <li class='disabled'><a href='#'>&raquo;</a></li>
                </c:if>


                <c:if test="${pb.currentPage !=pb.totalPage}">
                    <li>
                        <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.currentPage + 1}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}"
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>

                <li>
                    <a href="${pageContext.request.contextPath}/findUserByPageServlet?currentPage=${pb.totalPage}&rows=5&name=${condition.name[0]}&address=${condition.address[0]}&email=${condition.email[0]}"
                       aria-label="Previous">
                        <span aria-hidden="true">尾页</span>
                    </a>
                </li>


                <span style="font-size: 25px;margin-left: 5px;">
       共${pb.totalCount}条记录，共${pb.totalPage}页
        </span>

            </ul>
        </nav>


    </div>


</div>
</body>
</html>
