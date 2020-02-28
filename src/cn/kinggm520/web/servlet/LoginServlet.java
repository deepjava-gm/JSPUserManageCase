package cn.kinggm520.web.servlet;

import cn.kinggm520.domain.Manager;
import cn.kinggm520.domain.User;
import cn.kinggm520.service.ManagerService;
import cn.kinggm520.service.UserService;
import cn.kinggm520.service.impl.ManagerServiceImpl;
import cn.kinggm520.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-26 19:34
 * 管理员登录
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");  //解决中文乱码

//        获取验证码
        String verifycode = request.getParameter("verifycode");
        HttpSession session = request.getSession();
        Object code1 = session.getAttribute("code");
        String code = (String) code1;
        session.removeAttribute("code"); //移除session 实现一次性验证码


        if (code.equals(verifycode)) { //先判断验证码是否正确
            Map<String, String[]> map = request.getParameterMap();
            Manager manager= new Manager();

            try {
                BeanUtils.populate(manager, map);
            } catch (IllegalAccessException e) {
                // 写日志
            } catch (InvocationTargetException e) {
                // 写日志
            }

            //            调用查询
            ManagerService managerService= new ManagerServiceImpl();
            Manager currentManager= managerService.login(manager);


            if (currentManager != null) {   // 登录成功
                request.getSession().setAttribute("manager", currentManager);   //将当前用户存入session
                response.sendRedirect(request.getContextPath() + "/index.jsp");


            } else {  //验证失败 显示提示信息
                request.setAttribute("login_msg", "用户名或密码错误！");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            }


        } else {  //验证码错误  显示提示信息
            request.setAttribute("login_msg", "验证码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
