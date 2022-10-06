package top.mnsx.take_out.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 16:45
 * @Description: JavaWebToken工具类——自定义生成Token工具，使用HMAC256加密
 */
public class JWTUtil {
    // 默认——Mnsx_x
    private static final String TOKEN = "7655d825";

    /**
     * 将提供的参数键值对加密生成生成token
     * @param map 参数键值对map
     * @return 返回生成的token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        // JWT过期时间7天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();
    }

    /**
     * 验证token
     * @param token token字符串
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取token中的参数
     * @param token token字符串
     * @return 返回JWT参数类 DecodedJWT
     */
    public static DecodedJWT getToken(String token) {
        if (token == null) {
            return null;
        }
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
