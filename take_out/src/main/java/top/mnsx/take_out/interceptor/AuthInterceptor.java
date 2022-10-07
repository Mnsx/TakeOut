package top.mnsx.take_out.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.mnsx.take_out.annotation.UnAuth;
import top.mnsx.take_out.controller.BaseController;
import top.mnsx.take_out.entity.Employee;
import top.mnsx.take_out.service.ex.TokenErrorException;
import top.mnsx.take_out.utils.JSONUtil;
import top.mnsx.take_out.utils.JWTUtil;
import top.mnsx.take_out.utils.RedisUtil;
import top.mnsx.take_out.utils.ThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/9/23 22:21
 * @Description: 登录拦截器
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS方法类型直接放行
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }

        // 如果带有注解@UnAuth那么直接放行
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            if (method.getMethod().isAnnotationPresent(UnAuth.class)) {
                return true;
            }
        }

        // 获取请求头中的token
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        String token = request.getHeader("Authorization");

        // 判断token中用户登录权限是否过期
        try {
            DecodedJWT dToken = JWTUtil.getToken(token);
            String id = dToken.getClaim("id").asString();

            if (redisUtil.hHasKey("onlineEmployee", String.valueOf(id))) {
                String info = dToken.getClaim("info").asString();
                Employee employee = JSONUtil.jsonToObj(info, Employee.class);
                ThreadLocalUtil.add(employee);
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
