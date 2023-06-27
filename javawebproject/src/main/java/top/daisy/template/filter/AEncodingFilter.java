package top.daisy.template.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//filter的加载次序是由filter的名称决定的

//过滤器：专门处理编码的
//这里的urlPatterns表示拦截那个对象，/*表示所有页面都是同一个编码
////urlPatterns = "/*"拦截所有的地址请求
@WebFilter(filterName = "AEncodingFilter", urlPatterns = "/*")
public class AEncodingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AEncodingFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("编码过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("编码过滤处理中...");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
        logger.debug("编码过滤器完成应答");
    }

    @Override
    public void destroy() {
        logger.debug("编码过滤器销毁");
    }
}
