package cn.kinggm520.web.servlet;

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
 * 时间:  2020-02-26 20:52
 * 添加用户
 */
@WebServlet("/addUserServlet")
public class AddUserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");  //解决中文断码

        Map<String, String[]> map = request.getParameterMap();  //获取表单数据到Map集合
        User user = new User();
        try {
            BeanUtils.populate(user, map);    //使用BeanUtils工具类封装JavaBean

        } catch (IllegalAccessException e) {
//            写日志
        } catch (InvocationTargetException e) {
//             写日志
        }

//        调用service保存
        userService.addUser(user);

//        跳转到userListServlet
        response.sendRedirect(request.getContextPath() + "/findUserByPageServlet");


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);

    }
}
