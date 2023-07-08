package top.daisy.template.filter;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.daisy.template.dao.TbTokenDAO;
import top.daisy.template.entity.TbToken;
import top.daisy.template.entity.TokenInfo;
import top.daisy.template.util.IpUtil;
import top.daisy.template.util.JsonUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@WebFilter(filterName = "DTokenFilter", urlPatterns = "*.token")
public class DTokenFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DTokenFilter.class);
    private TbTokenDAO tbTokenDAO = new TbTokenDAO();
    public static final String REQUEST_TOKEN_NAME = "server_token";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //第一步，获取请求中token信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getParameter(REQUEST_TOKEN_NAME);
        token = token == null ? "" : token.trim();
        //默认是null
        TbToken tbToken = null;

        try {
            //去数据库校验token是否存在
            TbToken check = tbTokenDAO.queryByToken(token);
            if (check == null) {
                //不存在就新增加一个
                token = UUID.randomUUID().toString();
                tbToken = new TbToken();
                TokenInfo tokenInfo = new TokenInfo();
                tokenInfo.setIp(IpUtil.getIpAddr(request));
                tbToken.setToken(token);
                tbToken.setTokenInfo(JsonUtil.stringify(tokenInfo));
                tbTokenDAO.Insert(tbToken);
            } else {
                //存在就更新ip信息
                tbToken = check;
                TokenInfo tokenInfo = check.content();
                tokenInfo.setIp(IpUtil.getIpAddr(request));
                tbToken.setTokenInfo(JsonUtil.stringify(tokenInfo));
                tbTokenDAO.update(tbToken);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }


        logger.debug("token信息：{}", tbToken);
        request.setAttribute(REQUEST_TOKEN_NAME, tbToken);
        filterChain.doFilter(servletRequest, servletResponse);

    }


    @Override
    public void destroy() {

    }
}
