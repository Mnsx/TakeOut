package top.mnsx.take_out.utils;

import com.alibaba.fastjson.JSON;
import top.mnsx.take_out.component.ResultMap;

import java.util.Map;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/10/1 16:27
 * @Description: FASTJSON封装工具类，结合ResultMap使用，用来将响应类转换成json字符串
 */
public class JSONUtil {
    /**
     * 将json字符串转换成Map
     * @param json Json字符串
     * @return 返回Json字符串键值对组成的Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> jsonToMap(String json) {
        return JSON.parseObject(json, Map.class);
    }

    /**
     * 将ResultMap响应类转换成Json字符串
     * @param result 响应类
     * @return 返回Json字符串
     */
    public static String mapToJson(ResultMap result) {
        return JSON.toJSONString(result);
    }

    /**
     * 将json转换程对象
     * @param json 字符串
     * @param aClass 返回类型
     * @return 返回对象
     * @param <T> 对象类型
     */
    public static <T> T jsonToObj(String json, Class<T> aClass) {
        return JSON.parseObject(json, aClass);
    }
}
