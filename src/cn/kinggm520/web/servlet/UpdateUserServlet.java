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
 * 时间:  2020-02-26 22:51
 * 更新用户
 */
@WebServlet("/updateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

//        获取用户输入的表单数据 Map集合
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);  //使用工具类封装
        } catch (IllegalAccessException e) {
//            写日志
        } catch (InvocationTargetException e) {
//            写日志
        }

        userService.updateUserById(user);

//        更新过后 重新查询并展示
        response.sendRedirect(request.getContextPath()+"/findUserByPageServlet");



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
