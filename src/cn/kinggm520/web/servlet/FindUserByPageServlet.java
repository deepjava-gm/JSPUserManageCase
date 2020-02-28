package cn.kinggm520.web.servlet;

import cn.kinggm520.domain.PageBean;
import cn.kinggm520.domain.User;
import cn.kinggm520.service.UserService;
import cn.kinggm520.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-28 0:06
 * 主页展示列表  分页+多条件查询
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {

    UserService service = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //  获取参数
        String currentPage = request.getParameter("currentPage"); // 当前页码
        String rows = request.getParameter("rows"); //每页显示条数

        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }

        if (rows == null || "".equals(rows)) {
            rows = "5";
        }

//       获取多条件查询字符串
        Map<String, String[]> condition = request.getParameterMap();

//        调用service查询
        PageBean<User> pb = service.findUserByPage(currentPage, rows, condition);

//        将数据设置到request域中
        request.setAttribute("condition", condition);
        request.setAttribute("pb", pb);
//        转发到list页面进行数据展示
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
