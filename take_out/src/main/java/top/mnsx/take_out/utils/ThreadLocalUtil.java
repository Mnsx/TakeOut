package top.mnsx.take_out.utils;

import top.mnsx.take_out.entity.Employee;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/7 21:30
 * @Description: ThreadLocal工具类
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Object> threadLocal = new ThreadLocal<>();

    public static void add(Object temp) {
        threadLocal.set(temp);
    }

    public static Object get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
