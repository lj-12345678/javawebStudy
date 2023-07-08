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

@WebServlet(name = "LoginServlet", urlPatterns = "/login.token")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private TbUserDAO tbUserDAO = new TbUserDAO();
    private TbTokenDAO tbTokenDAO = new TbTokenDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //http://127.0.0.1:8080/javawebproject/login.token?server_token=2e49f8a9-0abf-4fde-923e-370bc4bf0b88&username=zhangsan&password=123
        TbToken token = (TbToken) req.getAttribute(DTokenFilter.REQUEST_TOKEN_NAME);
        resp.setContentType("text/plain");
        try {
            //获取参数
            TbUser user = BeanTools.mapping(req.getParameterMap(), TbUser.class);
            TbUser check = tbUserDAO.queryByUsername(user);
            BaseResult<TbUser> result = new BaseResult<>();
            if (check == null || !check.getPassword().equalsIgnoreCase(user.getPassword()) || "y".equalsIgnoreCase(check.getEnable())) {
                result.setCode(500);
                result.setMessage("登录失败");
                result.setSuccess(false);
                result.setToken(token.getToken());
                resp.getWriter().println(JsonUtil.stringify(result));
            }
            //成功要何token进行绑定
            TokenInfo tokenInfo = token.content();
            tokenInfo.setUser(check);
            token.setTokenInfo(JsonUtil.stringify(tokenInfo));
            tbTokenDAO.update(token);
            //成功应答
            result.setCode(200);
            result.setMessage("登录成功");
            result.setSuccess(true);
            result.setToken(token.getToken());
            resp.getWriter().println(JsonUtil.stringify(result));

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
