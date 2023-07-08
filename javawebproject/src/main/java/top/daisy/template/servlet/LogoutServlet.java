package top.daisy.template.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.daisy.template.base.BaseResult;
import top.daisy.template.dao.TbTokenDAO;
import top.daisy.template.dao.TbUserDAO;
import top.daisy.template.entity.TbToken;
import top.daisy.template.entity.TbUser;
import top.daisy.template.entity.TokenInfo;
import top.daisy.template.filter.DTokenFilter;
import top.daisy.template.util.BeanTools;
import top.daisy.template.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout.token")
public class LogoutServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class);
    private TbUserDAO tbUserDAO = new TbUserDAO();
    private TbTokenDAO tbTokenDAO = new TbTokenDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://127.0.0.1:8080/javawebproject/logout.token?server_token=2e49f8a9-0abf-4fde-923e-370bc4bf0b88
        TbToken token = (TbToken) req.getAttribute(DTokenFilter.REQUEST_TOKEN_NAME);
        resp.setContentType("text/plain");
        try {

            BaseResult<String> result = new BaseResult<>();

            //删除token中的用户信息
            TokenInfo tokenInfo = token.content();
            tokenInfo.setUser(null);
            token.setTokenInfo(JsonUtil.stringify(tokenInfo));
            tbTokenDAO.update(token);
            //成功应答
            result.setCode(200);
            result.setMessage("登出成功");
            result.setSuccess(true);
            result.setToken(token.getToken());
            resp.getWriter().println(JsonUtil.stringify(result));

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
