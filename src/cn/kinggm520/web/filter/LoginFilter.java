package cn.kinggm520.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 作者: kinggm Email:731586355@qq.com
 * 时间:  2020-03-01 20:16
 * 过滤器快速入门
 */

//浏览器直接访问请求资源时  该过滤器会被执行
@WebFilter(value="/*") //拦截路径 全部资源
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//  强转
        HttpServletRequest request = (HttpServletRequest)req;
//       获取资源请求路径
        String uri = request.getRequestURI();
//        判断是否包含登录相关资源
        if(uri.contains("/login.jsp")||uri.contains("/loginServlet") ||uri.contains("/css/")||uri.contains("/js/")||uri.contains("/fonts/")||uri.contains("/checkCodeServlet")){
//            包含 放行
            chain.doFilter(req, resp);
        }else {
//            不包含  验证用户是否登录 获取session
            Object manager = request.getSession().getAttribute("manager");

            if(manager!=null){
//                已登录 放行
                chain.doFilter(req, resp);
            }else {
//                未登录
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(req, resp);
            }

        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
