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
 * 时间:  2020-02-27 20:35
 * 删除选中用户
 */
@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    UserService service = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        获取用户id
        String[] cbs = request.getParameterValues("cb");
        service.delSelectedUser(cbs);
//        重定向到首页 相当于重新查询所有
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
