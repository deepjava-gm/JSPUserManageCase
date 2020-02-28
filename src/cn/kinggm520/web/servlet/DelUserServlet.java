package cn.kinggm520.web.servlet;

import cn.kinggm520.service.UserService;
import cn.kinggm520.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-26 21:27
 * 删除单个用户
 */
@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    UserService service = new UserServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获取用户id
        String id = request.getParameter("id");

//      调用service删除
        service.deleteUser(Integer.parseInt(id));
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
