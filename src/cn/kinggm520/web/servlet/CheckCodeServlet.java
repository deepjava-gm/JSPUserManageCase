package cn.kinggm520.web.servlet;

import cn.kinggm520.service.CheckCodeService;
import cn.kinggm520.service.impl.CheckCodeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-02-26 17:24
 * 验证码
 */

@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    CheckCodeService checkCodeService = new CheckCodeServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//获取验证码
        String code = checkCodeService.checkCodeService(response.getOutputStream());
//        获取session
        HttpSession session = request.getSession();
//        将验证码存入session
        session.setAttribute("code",code);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
