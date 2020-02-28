package cn.kinggm520.web.servlet;

import cn.kinggm520.domain.User;
import cn.kinggm520.service.UserService;
import cn.kinggm520.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-25 22:25
 * 用户信息显示
 */
@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        1、调用UserService完成查询
        UserService service = new UserServiceImpl();
        List<User> users = service.findAll();

//        2、将list存入request域
        request.setAttribute("users",users);
//        3、转发到list.jsp页面
        request.getRequestDispatcher("list.jsp").forward(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
